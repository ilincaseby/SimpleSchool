package ro.schooldata.SimpleSchool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.schooldata.SimpleSchool.Classes.Profesor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    public Optional<Profesor> findByEmailAndFirstNameAndLastName(String email, String firstName, String lastName);
    public Optional<Profesor> findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
    List<Profesor> findAll();
}
