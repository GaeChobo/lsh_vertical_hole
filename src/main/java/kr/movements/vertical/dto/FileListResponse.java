package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("파일 리스트")
@Getter
@Builder
public class FileListResponse {

    @ApiModelProperty(value = "파일Id")
    private Long fileId;

    @ApiModelProperty(value = "파일Type")
    private String fileType;

    @ApiModelProperty(value = "파일 원본명")
    private String originalName;

    public FileListResponse(Long fileId, String fileType, String originalName) {
        this.fileId = fileId;
        this.fileType = fileType;
        this.originalName = originalName;
    }
}
