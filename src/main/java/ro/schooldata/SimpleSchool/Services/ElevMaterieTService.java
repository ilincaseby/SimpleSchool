package ro.schooldata.SimpleSchool.Services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Classes.ElevMaterieT;
import ro.schooldata.SimpleSchool.Classes.Materie;
import ro.schooldata.SimpleSchool.Repositories.ElevMaterieTRepository;

import java.util.Optional;

@Service
public class ElevMaterieTService implements  IElevMaterieTService{
    private ElevMaterieTRepository elevMaterieTRepository;
    public ElevMaterieTService(ElevMaterieTRepository elevMaterieTRepository) {
        this.elevMaterieTRepository = elevMaterieTRepository;
    }
    @Override
    public void assignGrade(int grade, Elev elev, Materie materie) {
        Optional<ElevMaterieT> elevMaterieT = elevMaterieTRepository.findByElevAndMaterie(elev, materie);
        if (elevMaterieT.isPresent()) {
            elevMaterieT.get().getNote().add(grade);
            elevMaterieTRepository.save(elevMaterieT.get());
        } else {
            throw new RuntimeException("Does not exist in database a table to assign this grade!");
        }
    }

    @Override
    public void saveInDB(ElevMaterieT elevMaterieT) {
        elevMaterieTRepository.save(elevMaterieT);
    }
}
