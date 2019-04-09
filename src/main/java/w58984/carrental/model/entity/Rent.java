package w58984.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(schema = "carrental", name = "rent")
@SequenceGenerator(schema = "carrental", name = "id_generator", sequenceName = "rent_seq_id", allocationSize = 1)
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_generator")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Builder.Default
    @CreatedDate
    @Column(name = "rent_start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected OffsetDateTime rentStartDate = OffsetDateTime.now();


    @DateTimeFormat
    @Column(name = "rent_end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime rentEndDate;


}
