package w58984.carrental.model.DTO.Rent;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Date;
/**
 * Klasa typu Data Transfer Object do responsów dla wypożyczeń
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentCreateDTO {



    @NotNull
    @DateTimeFormat
    @ApiModelProperty("Rent finish date")
    private OffsetDateTime rentEndDate;




}
