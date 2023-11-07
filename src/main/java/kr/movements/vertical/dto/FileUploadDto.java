package kr.movements.vertical.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDto {

    @ApiModelProperty(value = "수직구 id")
    private Long verticalId;

    @Column(nullable = false)
    private Boolean riderType;
}
