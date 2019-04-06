package w58984.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(schema = "carrental", name = "user")
@SequenceGenerator(schema = "carrental", name = "id_generator", sequenceName = "user_seq_id", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_generator")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    private String mail;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String salt;

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

    public enum Status {
        /**
         * {@code active}
         */
        A,

        /**
         * {@code inActive}
         */
        I,
    }

    public enum Role {
        /**
         * {@code administrator}
         */
        A,

        /**
         * {@code user}
         */
        U,
    }
}