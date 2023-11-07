package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel("유저등록 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberCreateDto {

    @ApiModelProperty(value = "회원명", example = "이승희")
    private String userName;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$", message = "핸드폰 번호 형식으로 입력해야 합니다.")
    @ApiModelProperty(value = "연락처", example = "010-0000-0000")
    private String userPhone;

    @ApiModelProperty(value = "비밀번호", example = "1234")
    private String userPw;

    @Email
    @NotNull
    @ApiModelProperty(value = "oauth 이메일", example = "etet@mo.kr")
    private String userEmail;
}
