package ro.schooldata.SimpleSchool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.schooldata.SimpleSchool.Classes.Materie;

@Repository
public interface MaterieRepository extends JpaRepository<Materie, Long>{
    public void deleteById(Long id);

}
