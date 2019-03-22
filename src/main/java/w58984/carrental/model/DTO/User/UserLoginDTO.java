package w58984.carrental.model.DTO.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserLoginDTO {

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "login", required = true)
    private String login;

    @NotEmpty
    @Length(min = 8, max = 255)
    @ApiModelProperty(value = "password", required = true)
    private String password;
}
