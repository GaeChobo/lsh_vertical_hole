package kr.movements.vertical.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerticalHoleInfoWithAlertDto {

    private Long id;
    private String verticalHoleName;
    private LocalDate verticalHoleStartDate;
    private LocalDate verticalHoleEndDate;
    private Long verticalHoleSlabMaterialTotal;
    private Long constructionCount;
}