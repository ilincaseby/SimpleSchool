package ro.schooldata.SimpleSchool.Services;

import ro.schooldata.SimpleSchool.Classes.Elev;

import java.util.List;

public interface IElevService {
    public Elev getADefaultElev();
    public List<Elev> returnAll();

    public void createElev(Elev elev);

    public void updateElev(Long id, Elev elev);


    public void deleteElev(Long id);

    public Elev getElevById(Long id);
}
