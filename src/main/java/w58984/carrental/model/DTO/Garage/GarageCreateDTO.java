package w58984.carrental.model.DTO.Garage;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageCreateDTO {

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Name")
    private String name;

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Address")
    private String address;

}
