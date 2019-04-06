package w58984.carrental.model.DTO.Car;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.hibernate.validator.constraints.Length;
import w58984.carrental.model.entity.Garage;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CarCreateDTO {


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
