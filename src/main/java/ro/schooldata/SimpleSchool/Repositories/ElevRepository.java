package ro.schooldata.SimpleSchool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.schooldata.SimpleSchool.Classes.Elev;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElevRepository extends JpaRepository<Elev, Long> {
    public List<Elev> findAllByCnp(String cnp);
    public Optional<Elev> findByCod(String cod);
    public Optional<Elev> findByTel(String tel);
    public Optional<Elev> findByUserName(String userName);
    public boolean existsByUserName(String username);
}
