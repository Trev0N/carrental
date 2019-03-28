package w58984.carrental.model.DTO.Car;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CarUpdateDTO {

    @NotEmpty
    @ApiModelProperty(value = "Register", required = true)
    private String registerName;

    @NotEmpty
    @ApiModelProperty(value = "Mark", required = true)
    private String mark;

    @NotEmpty
    @ApiModelProperty(value = "Model", required = true)
    private String model;

    @NotNull
    @ApiModelProperty(value = "Engine capacity cm3", required = true)
    private int engine;

    @NotNull
    @ApiModelProperty(value = "Engine power(HP)", required = true)
    private int power;

}
