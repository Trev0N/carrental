package w58984.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    @JsonIgnore
    @NotBlank
    private String name;

    @JsonIgnore
    @NotBlank
    private String address;

}
