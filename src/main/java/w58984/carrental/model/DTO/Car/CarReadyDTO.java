package w58984.carrental.model.DTO.Car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarReadyDTO {
    private Long id;

    private String registerName;

    private String mark;

    private String model;

    private int engine;

    private int power;

    private Long garageId;

    private BigDecimal price;




}
