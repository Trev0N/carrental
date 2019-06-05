package w58984.carrental.model.DTO.CarDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import w58984.carrental.model.entity.enums.StatusEnum;

import java.math.BigDecimal;
/**
 * Klasa typu Data Transfer Object do responsów dla detali samochodów
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDetailUpdateDTO {

    private Long carId;

    private BigDecimal price;

    private StatusEnum statusEnum;

    private Long mileage;
}
