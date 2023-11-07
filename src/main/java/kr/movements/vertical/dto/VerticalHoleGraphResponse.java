package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerticalHoleGraphResponse {

    @ApiModelProperty(value = "시공 예정 종료일시")
    private LocalDate verticalHoleEndDate;
    @ApiModelProperty(value = "시공 예정 시작일시")
    private LocalDate verticalHoleStartDate;
    @ApiModelProperty(value = "시공 슬라츠 자재 총갯수")
    private Long slabMaterialCount;
}