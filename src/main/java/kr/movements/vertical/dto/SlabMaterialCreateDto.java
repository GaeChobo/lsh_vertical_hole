package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("슬라브 자재 등록 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlabMaterialCreateDto {

    @ApiModelProperty(value = "슬라브Id", example = "1689751644382809")
    private Long slabId;

    @ApiModelProperty(value = "슬라브 자재순서", example = "1")
    private int slabMaterialProcess;

    @ApiModelProperty(value = "슬라브 자재명", example = "1층 1번 SLAB PC")
    private String slabMaterialName;

}
