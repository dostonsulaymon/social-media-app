package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRoleRepository extends JpaRepository<ProfileRoleEntity, Long> {
}
