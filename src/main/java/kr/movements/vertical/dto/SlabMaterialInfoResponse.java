package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@ApiModel("슬라브 자재 상세조회")
@Getter
@Builder
public class SlabMaterialInfoResponse {
    @ApiModelProperty(value = "슬라브 자재명")
    private String slabMaterialName;
    @ApiModelProperty(value = "시공된 날짜")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime completionDate;

    @ApiModelProperty(value = "슬라브 자재 수평")
    private Double slabMaterialHorizontal;

    @ApiModelProperty(value = "슬라브 자재 수직")
    private Double slabMaterialVertical;
}
