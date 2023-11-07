package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("현장 - 멤버 맵핑 등록 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAreaCreateDto {
    @ApiModelProperty(value = "유저Id", example = "1689667637966711")
    private Long memberId;
    @ApiModelProperty(value = "현장Id", example = "1689668743551852")
    private Long areaId;
}
