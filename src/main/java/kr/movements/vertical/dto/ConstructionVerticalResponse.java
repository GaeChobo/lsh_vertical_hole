package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel("설계 수직구 목록 리스트")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructionVerticalResponse {

    private Long verticalId;

    private String verticalName;

    private Long fileId;

    private Boolean verticalRiderExist;
}
