package w58984.carrental.model.Api.garage;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Klasa modelowa do requestów dla edycji garazu
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditGarageApi {

    @ApiModelProperty(value = "Name")
    private String name;


    @ApiModelProperty(value = "Address")
    private String address;


}
