package w58984.carrental.model.entity;

import lombok.*;
import w58984.carrental.model.entity.enums.StatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Klasa z encjami dla detali samochodów
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(schema = "carrental", name = "car_detail")
@SequenceGenerator(schema = "carrental", name = "id_generator", sequenceName = "car_detail_seq_id", allocationSize = 1)
public class CarDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "id_generator")
    private Long id;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    private BigDecimal price;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    @NotNull
    private Long mileage;


}
