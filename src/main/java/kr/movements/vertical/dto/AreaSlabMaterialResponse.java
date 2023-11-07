package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AreaSlabMaterialResponse {

    @ApiModelProperty(value = "현장 총 자재량")
    private Long totalSlabMaterial;
    @ApiModelProperty(value = "현장 총 시공완료 수량")
    private Long SlabMaterialCompletion;
    @ApiModelProperty(value = "현장 시공률")
    private String percentageTotalSlabMaterial;
}