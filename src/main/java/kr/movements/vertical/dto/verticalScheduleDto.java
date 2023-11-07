package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@ApiModel("시공일정 수정 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class verticalScheduleDto {

    @ApiModelProperty(value = "시공일정 시작일", example = "2023-07-25")
    private LocalDate startDate;
    @ApiModelProperty(value = "시공일정 종료일", example = "2023-07-28")
    private LocalDate endDate;
}
