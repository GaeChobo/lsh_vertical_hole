package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginDto {

    @NotBlank
    @ApiModelProperty(value = "이메일", example = "lsh.mv@movements.kr")
    private String email;
    @NotBlank
    @ApiModelProperty(value = "비밀번호", example = "123456")
    private String password;
}
