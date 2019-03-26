package w58984.carrental.model.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(schema = "carrental", name = "garage")
@SequenceGenerator(schema = "carrental", name = "id_generator", sequenceName = "garage_seq_id", allocationSize = 1)
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_generator")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Car> carList;


}
