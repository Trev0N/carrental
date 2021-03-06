package w58984.carrental.model.DTO.Car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Klasa typu Data Transfer Object do responsów dla samochodów
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {

    private Long id;

    private String registerName;

    private String mark;

    private String model;

    private int engine;

    private int power;

    private Long garageId;


}
