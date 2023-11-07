package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel("QR 직접 입고처리 Dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrSelfReceivingDto {

    @NotNull
    @ApiModelProperty(value = "슬라브 자재 Id", example = "1689920883308311")
    private Long slabMaterialId;

    @ApiModelProperty(value = "모바일 db 시간값", example = "2023-09-20 13:15")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime cellPhoneTime;
}
