package ro.schooldata.SimpleSchool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Classes.ElevMaterieT;
import ro.schooldata.SimpleSchool.Classes.Materie;

import java.util.Optional;

@Repository
public interface ElevMaterieTRepository extends JpaRepository<ElevMaterieT, Long> {
    public Optional<ElevMaterieT> findByElevAndMaterie(Elev elev, Materie materie);
}
