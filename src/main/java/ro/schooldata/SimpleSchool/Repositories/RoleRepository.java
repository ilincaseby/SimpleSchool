package ro.schooldata.SimpleSchool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.schooldata.SimpleSchool.Classes.ERole;
import ro.schooldata.SimpleSchool.Classes.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
    long count();
}
