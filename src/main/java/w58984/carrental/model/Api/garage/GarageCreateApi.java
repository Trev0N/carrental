package w58984.carrental.model.Api.garage;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
/**
 * Klasa modelowa do requestów dla utworzenia garażu
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageCreateApi {

    @ApiModelProperty(value = "Name")
    private String name;

    @ApiModelProperty(value = "Address")
    private String address;

}
