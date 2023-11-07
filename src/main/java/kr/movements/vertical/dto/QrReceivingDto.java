package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("QR 입고처리 Dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrReceivingDto {

    @NotNull
    @ApiModelProperty(value = "슬라브 자재 Id", example = "1689920883308311")
    private Long slabMaterialId;

}
