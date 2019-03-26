package w58984.carrental.model.DTO.Car;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarCreateDTO {

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Mark", required = true)
    private String mark;

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Model", required = true)
    private String model;

    @NotNull
    @ApiModelProperty(value = "Engine capacity", required = true)
    private Double engine;

    @NotNull
    @ApiModelProperty(value = "Power (HP)", required = true)
    private int power;
}
