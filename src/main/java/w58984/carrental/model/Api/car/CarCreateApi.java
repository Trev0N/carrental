package w58984.carrental.model.Api.car;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarCreateApi {


    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Register")
    private String registerName;

    @NotNull
    @ApiModelProperty(value = "Garage id")
    private Long garageId;

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Mark", required = true)
    private String mark;

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Model", required = true)
    private String model;

    @NotNull
    @ApiModelProperty(value = "Engine capacity cm3", required = true)
    private int engine;

    @NotNull
    @ApiModelProperty(value = "Engine Power (HP)", required = true)
    private int power;


}
