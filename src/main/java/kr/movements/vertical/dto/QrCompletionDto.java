package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@ApiModel("QR 시공완료 Dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrCompletionDto {

    @NotNull
    @ApiModelProperty(value = "슬라브 자재 Id", example = "1689920883308311")
    private Long slabMaterialId;

    @ApiModelProperty(value = "슬라브 자재 수평값", example = "1.45")
    private Double slabMaterialHorizontal;

    @ApiModelProperty(value = "슬라브 자재 수직값", example = "1.45")
    private Double slabMaterialVertical;
}
