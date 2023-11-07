package kr.movements.vertical.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@ApiModel("수직구 리스트 정보 response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerticalHoleInfoResponse {

    @ApiModelProperty(value = "수직구Id")
    private Long verticalId;
    @ApiModelProperty(value = "수직구명")
    private String verticalHoleName;
    @ApiModelProperty(value = "시공 시작 일정")
    private LocalDate verticalHoleStartDate;
    @ApiModelProperty(value = "시공 종료 일정")
    private LocalDate verticalHoleEndDate;
    @ApiModelProperty(value = "전체 수량")
    private Long verticalHoleSlabMaterialTotal;
    @ApiModelProperty(value = "시공 수량")
    private Long constructionCount;
    @ApiModelProperty(value = "알림 리스트")
    private List<String> contextList;
    @ApiModelProperty(value = "시공 현황 퍼센트")
    private String constructionPercentage;

}
