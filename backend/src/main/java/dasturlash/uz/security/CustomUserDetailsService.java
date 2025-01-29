package dasturlash.uz.security;

import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.LanguageEnum;
import dasturlash.uz.exceptions.auth_related.UnauthorizedException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.service.ResourceBundleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final ResourceBundleService resourceBundleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ProfileEntity> optional = profileRepository.findByLoginAndVisibleTrue(username);

        if (optional.isEmpty()) {
            log.info("I am throwing an unauthorized exception in Uzbek language");
            throw new UnauthorizedException(resourceBundleService.getMessage("invalid.login.format", LanguageEnum.uz));
        }

        ProfileEntity profile = optional.get();


        return new CustomUserDetails(profile);
    }

}
