package ro.schooldata.SimpleSchool.Services;

import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.stereotype.Service;
import ro.schooldata.SimpleSchool.Classes.ERole;
import ro.schooldata.SimpleSchool.Classes.Role;
import ro.schooldata.SimpleSchool.Repositories.RoleRepository;

@Service
public class RoleService {
    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    void verifyAndAdapt() {
        long howMany = roleRepository.count();
        if (howMany == 0) {
            Role roleElev = new Role(ERole.ROLE_ELEV);
            roleRepository.save(roleElev);
            Role roleAdmin = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(roleAdmin);
            Role roleTeacher = new Role(ERole.ROLE_TEACHER);
            roleRepository.save(roleTeacher);
        }
    }
}
