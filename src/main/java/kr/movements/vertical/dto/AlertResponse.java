package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class AlertResponse {

    @ApiModelProperty(value = "슬라브 자재 Id")
    private Long slabMaterialId;
    @ApiModelProperty(value = "슬라브 자재 시공완료 메시지")
    private String alertContextCompletion;
    @ApiModelProperty(value = "슬라브 자재 입고완료 메시지")
    private String alertContextReceiving;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "시공 완료일시")
    private LocalDateTime createdDateCompletion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "입고 완료일시")
    private LocalDateTime createdDateReceiving;

    @QueryProjection
    public AlertResponse(Long slabMaterialId, String alertContextCompletion, String alertContextReceiving,
                         LocalDateTime createdDateCompletion, LocalDateTime createdDateReceiving) {
        this.slabMaterialId = slabMaterialId;
        this.alertContextCompletion = alertContextCompletion;
        this.alertContextReceiving = alertContextReceiving;
        this.createdDateCompletion = createdDateCompletion;
        this.createdDateReceiving = createdDateReceiving;
    }

}