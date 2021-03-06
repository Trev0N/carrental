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
public class CarRentDTO {

    private Long id;
}
