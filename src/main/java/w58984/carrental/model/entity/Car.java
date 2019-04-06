package w58984.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(name = "register_name")
    private String registerName;

    @NotBlank
    private String mark;

    @NotBlank
    private String model;

    @NotNull
    private int engine;

    @NotNull
    private int power;

    @ManyToOne(optional = false)
    @JoinColumn(name = "garage_id")
    private Garage garage;

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
