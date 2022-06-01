package net.auth.spring.login.repository;

import java.util.Optional;
import net.auth.spring.login.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.auth.spring.login.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);

  Role findById(Integer id);
}
