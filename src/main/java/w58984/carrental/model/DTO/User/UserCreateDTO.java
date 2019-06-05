package w58984.carrental.model.DTO.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
/**
 * Klasa typu Data Transfer Object do responsów dla użytkowników
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "First name", required = true)
    private String firstName;

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Last name", required = true)
    private String lastName;

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "login", required = true)
    private String login;

    @NotEmpty
    @Length(min = 8, max = 255)
    @ApiModelProperty(value = "password", required = true)
    private String password;

    @NotEmpty
    @ApiModelProperty(value = "Re-captcha", required = true)
    private String recaptcha;

    @NotEmpty
    @Length(max = 255)
    @Email
    @ApiModelProperty(value = "E-mail", required = true)
    private String mail;

}
