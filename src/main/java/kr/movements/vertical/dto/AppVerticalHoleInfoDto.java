package kr.movements.vertical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppVerticalHoleInfoDto {

    private Long verticalHoleId;
    private String verticalHoleName;
    private Long verticalHoleSlabMaterialTotal;
    private Long slabMaterialReceiving;
    private Long slabMaterialCompletion;
}
