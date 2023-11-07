package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("슬라브 자재 센서 등록 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlabMaterialSensorDto {

    @ApiModelProperty(value = "슬라브 자재 수평값", example = "1.45")
    private Double slabMaterialHorizontal;

    @ApiModelProperty(value = "슬라브 자재 수직값", example = "1.45")
    private Double slabMaterialVertical;
}
