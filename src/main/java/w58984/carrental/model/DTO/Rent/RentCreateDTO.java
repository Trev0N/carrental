package w58984.carrental.model.DTO.Rent;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentCreateDTO {

    @NotNull
    private Long idCar;

    @NotNull
    @DateTimeFormat
    @ApiModelProperty("Rent finish date")
    private Date rentEndDate;




}
