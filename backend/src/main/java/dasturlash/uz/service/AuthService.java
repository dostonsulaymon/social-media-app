package dasturlash.uz.service;

import dasturlash.uz.dto.JwtResponseDTO;
import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.enums.LanguageEnum;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.exceptions.SomethingWentWrongException;
import dasturlash.uz.exceptions.auth_related.ProfileExistException;
import dasturlash.uz.exceptions.auth_related.UnauthorizedException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRoleRepository;
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
    private final ResourceBundleService resourceBundleService;
    private final JwtUtil jwtUtil;
    private final ProfileRoleRepository profileRoleRepository;

    public JwtResponseDTO registration(RegistrationDTO dto) {

        existsByLogin(dto.login());

        ProfileEntity profile = new ProfileEntity();
        profile.setFirstName(dto.firstName());
        profile.setLastName(dto.lastName());
        profile.setLogin(dto.login());
        profile.setStatus(GeneralStatus.IN_REGISTRATION);
        profile.setCreatedDate(LocalDateTime.now());
        profile.setPassword(passwordEncoder.encode(dto.password()));


        ProfileEntity saved = profileRepository.save(profile);

        // phonemi yoki email bilish kerak
        // email sender service ga request ketadi.
        // send random code
        // wait for confirmation
           // agar user in registration da turib yana register qilishg harakat qilsa, ruhsat bermaymiz.
           //


        return null;
    }

    public JwtResponseDTO login(String login, String password) {
        ProfileEntity entity = profileRepository.findByLoginAndVisibleTrue(login)
                .orElseThrow(() -> new UnauthorizedException(resourceBundleService.getMessage("login.password.wrong", LanguageEnum.uz)));

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login, password));

            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                List<ProfileRole> roles = userDetails.getRoleList();
                List<String> rolesForToken = roles.stream().map(Enum::name).toList();

                return new JwtResponseDTO(jwtUtil.encode(login, rolesForToken),  // token
                        jwtUtil.refreshToken(login, rolesForToken), // refreshToken
                        login,                                 // login
                        rolesForToken                          // roles
                );
            }

            throw new UnauthorizedException(resourceBundleService.getMessage("login.password.wrong", LanguageEnum.uz));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(resourceBundleService.getMessage("login.password.wrong", LanguageEnum.uz));
        }
    }

    private void existsByLogin(String login) {
        boolean result = profileRepository.existsByLogin(login);
        if (!result) {
            throw new ProfileExistException(resourceBundleService.getMessage("login.exists", LanguageEnum.uz));
        }

        System.out.println("heloo");

    }
}
