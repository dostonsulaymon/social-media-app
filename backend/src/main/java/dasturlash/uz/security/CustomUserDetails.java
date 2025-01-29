package dasturlash.uz.security;

import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.enums.ProfileRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {
    private final Long id;
    private final String name;
    private final GeneralStatus status;
    private final String email;
    private final String password;
    private final List<ProfileRole> roleList;

    // shuyerda qara
    public CustomUserDetails(ProfileEntity profile) {
        this.id = profile.getId();
        this.name = profile.getFirstName();
        this.email = profile.getLastName();

        this.password = profile.getPassword();
        this.status = profile.getStatus();
        // Extract ProfileRole from ProfileRoleEntity
        this.roleList = profile.getRoleList().stream()
                .map(roleEntity -> roleEntity.getRole())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert ProfileRole to GrantedAuthority only when needed for Spring Security
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != GeneralStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == GeneralStatus.ACTIVE;
    }
}