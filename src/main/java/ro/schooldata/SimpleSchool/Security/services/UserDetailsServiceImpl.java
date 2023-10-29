package ro.schooldata.SimpleSchool.Security.services;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.schooldata.SimpleSchool.Classes.Profesor;
import ro.schooldata.SimpleSchool.Repositories.ProfesorRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    ProfesorRepository profesorRepository;

    public UserDetailsServiceImpl(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Profesor profesor = profesorRepository.findByUserName(userName).
                            orElseThrow(() -> new UsernameNotFoundException("Profesor with username: " + userName + " not found!"));
        return UserDetailsImpl.build(profesor);
    }
}
