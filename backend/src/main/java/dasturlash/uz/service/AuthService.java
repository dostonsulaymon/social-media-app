package dasturlash.uz.service;

import dasturlash.uz.dto.JwtResponseDTO;
import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.exceptions.SomethingWentWrongException;
import dasturlash.uz.exceptions.auth_related.UnauthorizedException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.security.CustomUserDetails;
import dasturlash.uz.util.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String registration(RegistrationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByLoginAndVisibleTrue(dto.login());
        if (optional.isPresent()) {
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.delete(profile);
                //2 sen sms/ email for verificate
            }
            throw new SomethingWentWrongException("User already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setPassword(passwordEncoder.encode(dto.password()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        return null;
    }

    public JwtResponseDTO login(String login, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password)
            );

            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                List<ProfileRole> roles = userDetails.getRoleList();
                List<String> rolesForToken = roles.stream().map(Enum::name).toList();

                return new JwtResponseDTO(
                        jwtUtil.encode(login, rolesForToken),  // token
                        jwtUtil.refreshToken(login, rolesForToken), // refreshToken
                        login,                                 // login
                        rolesForToken                          // roles
                );
            }
            throw new UnauthorizedException("Login or password is wrong");
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Login or password is wrong");
        }
    }
}
