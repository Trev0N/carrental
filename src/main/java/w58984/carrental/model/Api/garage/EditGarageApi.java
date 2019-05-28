package w58984.carrental.model.Api.garage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditGarageApi {

    @ApiModelProperty(value = "Name")
    private String name;


    @ApiModelProperty(value = "Address")
    private String address;


}
