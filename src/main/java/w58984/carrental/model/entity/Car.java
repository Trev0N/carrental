package w58984.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(schema = "carrental", name = "car")
@SequenceGenerator(schema = "carrental", name = "id_generator", sequenceName = "car_seq_id", allocationSize = 1)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_generator")
    private Long id;

    @NotBlank
    private String mark;

    @NotBlank
    private String model;

    @NotNull
    private Double engine;

    @NotNull
    private int power;

    @Builder.Default
    @CreatedDate
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected OffsetDateTime createdAt = OffsetDateTime.now();

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @UpdateTimestamp
    @Column(name = "modified_at")
    protected OffsetDateTime modifiedAt = OffsetDateTime.now();

}
