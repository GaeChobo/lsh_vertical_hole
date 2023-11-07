package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("슬라브 등록 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlabCreateDto {

    @ApiModelProperty(value = "수직구Id", example = "1689751644382809")
    private Long verticalHoleId;

    @ApiModelProperty(value = "슬라브명", example = "슬라브A")
    private String slabName;

    @ApiModelProperty(value = "슬라브순서", example = "1")
    private int slabFloor;
}
