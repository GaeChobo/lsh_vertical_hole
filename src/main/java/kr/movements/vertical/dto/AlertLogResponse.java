package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("현장 목록 조회")
@Getter
@Builder
public class AlertLogResponse {

    private String alertContext;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @QueryProjection
    public AlertLogResponse(String alertContext, LocalDateTime createdDate) {
        this.alertContext = alertContext;
        this.createdDate = createdDate;
    }

}
