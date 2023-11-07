package kr.movements.vertical.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("현장 목록 조회")
@Getter
@Builder
public class AlertInfoTupleDto {

    private Long slabMaterialId;
    private String alertContextCompletion;
    private String alertContextReceiving;
    private LocalDateTime createdDateCompletion;
    private LocalDateTime createdDateReceiving;
    private String slabMaterialName;

    @QueryProjection
    public AlertInfoTupleDto(Long slabMaterialId, String alertContextCompletion, String alertContextReceiving,
                        LocalDateTime createdDateCompletion, LocalDateTime createdDateReceiving, String slabMaterialName) {
        this.slabMaterialId = slabMaterialId;
        this.alertContextCompletion = alertContextCompletion;
        this.alertContextReceiving = alertContextReceiving;
        this.createdDateCompletion = createdDateCompletion;
        this.createdDateReceiving = createdDateReceiving;
        this.slabMaterialName = slabMaterialName;
    }
}
