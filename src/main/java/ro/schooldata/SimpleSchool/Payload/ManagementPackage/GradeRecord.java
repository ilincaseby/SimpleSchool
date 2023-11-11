package ro.schooldata.SimpleSchool.Payload.ManagementPackage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class GradeRecord {
    @NotBlank
    private int grade;
}
