package ro.schooldata.SimpleSchool.Services;

import org.springframework.stereotype.Service;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Repositories.ElevRepository;
import ro.schooldata.SimpleSchool.Classes.LoggedInAccount;

import java.util.List;

@Service
public class ElevService implements IElevService {

    private final ElevRepository elevRepository;
    private LoggedInAccount acc = LoggedInAccount.getInstance();
    public ElevService(ElevRepository elevRepository) {
        this.elevRepository = elevRepository;
    }

    @Override
    public Elev getADefaultElev() {
        return new Elev("Popescu", "Andrei", "0788888888", "email@example.com", "0000000000", "000000");
    }

    @Override
    public List<Elev> returnAll() {
        return elevRepository.findAll();
    }

    @Override
    public void createElev(Elev elev) {
        List<Elev> elevP = elevRepository.findAllByCnp(elev.getCNP());
        if (elevP.size() != 0) {
            throw new IllegalStateException(String.format("Elevul deja exista in sistem pe baza CNP-ului: %s", elev.getCNP()));
        }
        elevRepository.save(elev);
    }

    @Override
    public void updateElev(Long id, Elev elev) {
        Elev elev1 = elevRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("Nu exista in baza de date elevul cu id-ul %ld", id)));
        elev1.setCNP(elev.getCNP());
        elev1.setCod(elev.getCod());
        elev1.setEmail_p(elev.getEmail_p());
        elev1.setTel(elev.getTel());
        elev1.setFirstName(elev.getFirstName());
        elev1.setLastName(elev.getLastName());
        elev1.setClasa(elev.getClasa());
        elevRepository.save(elev1);
    }

    @Override
    public void deleteElev(Long id) {
        elevRepository.deleteById(id);
    }

    @Override
    public Elev getElevById(Long id) {
        Elev elev = elevRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("Nu exista elevul cu id-ul %ld", id)));
        return elev;
    }
}
