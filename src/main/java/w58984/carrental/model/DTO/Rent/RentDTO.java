package w58984.carrental.model.DTO.Rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.entity.Car;

import java.time.OffsetDateTime;
/**
 * Klasa typu Data Transfer Object do responsów dla wypożyczeń
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentDTO {

    private Long idRent;

    private OffsetDateTime rentStartDate;

    private OffsetDateTime rentEndDate;

    private Long idCar;

    private String registerName;

    private String mark;

    private String model;

    private int engine;

    private int power;

    private String garageName;

    private String garageAddress;




}
