package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@ApiModel("현장 상세 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaInfoResponse {

    @NotNull
    @ApiModelProperty(value = "사업명 or 현장명", example = "사업명 혹은 현장명")
    private String areaName;
    @NotNull
    @ApiModelProperty(value = "발주처", example = "발주처명")
    private String clientName;
    @NotNull
    @ApiModelProperty(value = "시공사", example = "시공 회사")
    private String companyName;
    @NotNull
    @ApiModelProperty(value = "회사주소", example = "서울특별시 강남구 봉은사로  120 르메르디앙서울호텔")
    private String companyAddress;
    @Email
    @NotNull
    @ApiModelProperty(value = "이메일", example = "tets@mo.kr")
    private String companyEmail;

}
