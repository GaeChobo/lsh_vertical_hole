package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AreaSlabMaterialDto {
    @ApiModelProperty(value = "현장 총 자재량")
    private Long totalSlabMaterial;
    @ApiModelProperty(value = "현장 총 시공완료 수량")
    private Long SlabMaterialCompletion;
}
