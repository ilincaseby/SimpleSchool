package ro.schooldata.SimpleSchool.Classes;

import javax.validation.constraints.NotBlank;

public class ConnTeacherStudent {
    @NotBlank
    private Long idT;
    @NotBlank
    private Long idS;

    public ConnTeacherStudent(Long idT, Long idS) {
        this.idT = idT;
        this.idS = idS;
    }

    public ConnTeacherStudent() {
    }

    public Long getIdT() {
        return idT;
    }

    public void setIdT(Long idT) {
        this.idT = idT;
    }

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }
}
