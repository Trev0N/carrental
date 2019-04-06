package w58984.carrental.model.entity;

import com.fasterxml.jackson.annotation.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

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
