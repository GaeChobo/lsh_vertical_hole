package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("수직구 등록 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerticalCreateDto {

    @ApiModelProperty(value = "현장Id", example = "1689748748252141")
    private Long areaId;

    @ApiModelProperty(value = "수직구명", example = "수직구A")
    private String verticalHoleName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "수직구 시공시작일정", example = "2023-07-21")
    private LocalDate verticalStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "수직구 시공종료일정", example = "2023-08-18")
    private LocalDate verticalEndDate;
}
