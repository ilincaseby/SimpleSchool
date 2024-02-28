package ro.schooldata.SimpleSchool.Services;

import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Classes.ElevMaterieT;
import ro.schooldata.SimpleSchool.Classes.Materie;

public interface IElevMaterieTService {
    public void assignGrade(int grade, Elev elev, Materie materie);
    public void saveInDB(ElevMaterieT elevMaterieT);
}
