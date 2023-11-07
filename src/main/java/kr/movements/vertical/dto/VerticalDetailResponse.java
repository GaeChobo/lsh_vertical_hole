package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("수직구 현황 response")
@Getter
@Builder
public class VerticalDetailResponse {

    @ApiModelProperty(value = "수직구명")
    private String verticalHoleName;

    @ApiModelProperty(value = "전체수량")
    private Long slabMaterialTotal;

    @ApiModelProperty(value = "입고수량")
    private Long slabMaterialReceiving;

    @ApiModelProperty(value = "시공수량")
    private Long slabMaterialCompletion;

    @ApiModelProperty(value = "시공 현황 퍼센트")
    private int constructionPercent;

    @ApiModelProperty(value = "시공 시작일정")
    private LocalDate verticalHoleStartDate;

    @ApiModelProperty(value = "시공 시작일정")
    private LocalDate verticalHoleEndDate;

    @ApiModelProperty(value = "시공 실제 가장 먼저 시작한 날")
    private LocalDate earliestDate;

    @ApiModelProperty(value = "시공 실제 가장 먼저 시작한 날")
    private LocalDate latestDate;

    @QueryProjection
    public VerticalDetailResponse(String verticalHoleName,
                                  Long slabMaterialTotal,
                                  Long slabMaterialReceiving,
                                  Long slabMaterialCompletion,
                                  int constructionPercent,
                                  LocalDate verticalHoleStartDate,
                                  LocalDate verticalHoleEndDate,
                                  LocalDate earliestDate,
                                  LocalDate latestDate) {

        this.verticalHoleName = verticalHoleName;
        this.slabMaterialTotal = slabMaterialTotal;
        this.slabMaterialReceiving = slabMaterialReceiving;
        this.slabMaterialCompletion = slabMaterialCompletion;
        this.constructionPercent = constructionPercent;
        this.verticalHoleStartDate = verticalHoleStartDate;
        this.verticalHoleEndDate = verticalHoleEndDate;
        this.earliestDate = earliestDate;
        this.latestDate = latestDate;
    }

}
