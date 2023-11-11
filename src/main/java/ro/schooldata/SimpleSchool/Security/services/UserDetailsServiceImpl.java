package ro.schooldata.SimpleSchool.Security.services;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.schooldata.SimpleSchool.Classes.Profesor;
import ro.schooldata.SimpleSchool.Classes.User;
import ro.schooldata.SimpleSchool.Repositories.ElevRepository;
import ro.schooldata.SimpleSchool.Repositories.ProfesorRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    ProfesorRepository profesorRepository;
    ElevRepository elevRepository;

    public UserDetailsServiceImpl(ProfesorRepository profesorRepository, ElevRepository elevRepository) {
        this.profesorRepository = profesorRepository;
        this.elevRepository = elevRepository;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = profesorRepository.findByUserName(userName).orElse(null);
        if (user == null) {
            user = elevRepository.findByUserName(userName)
                    .orElseThrow(() -> new RuntimeException("User not found!"));
        }
        return UserDetailsImpl.build(user);
    }
}
