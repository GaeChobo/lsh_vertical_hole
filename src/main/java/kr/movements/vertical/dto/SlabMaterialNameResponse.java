package kr.movements.vertical.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("QR 찍었을 떄 슬라브 자재 명칭, ID 조회")
@Getter
@Builder
public class SlabMaterialNameResponse {

    @ApiModelProperty(value = "슬라브 Id")
    private Long slabMaterialId;

    @ApiModelProperty(value = "슬라브 자재명")
    private String slabMaterialName;

    @ApiModelProperty(value = "슬라브 자재 입고여부")
    private Boolean slabMaterialReceiving;

    @ApiModelProperty(value = "슬라브 자재 시공여부")
    private Boolean slabMaterialCompletion;
}
