package w58984.carrental.model.DTO.Garage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageDTO {

    private Long id;

    private String name;

    private String address;

}
