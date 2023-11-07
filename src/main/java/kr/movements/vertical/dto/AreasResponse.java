package kr.movements.vertical.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@ApiModel("현장 목록 조회")
@Getter
@Builder
public class AreasResponse {

    private Long areaId;
    private String areaName;

    private String clientName;

    private String companyName;

    private String companyEmail;

    private String companyAddress;

    @QueryProjection
    public AreasResponse(Long areaId, String areaName, String clientName, String companyName, String companyEmail, String companyAddress) {

        this.areaId = areaId;
        this.areaName = areaName;
        this.clientName = clientName;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyAddress = companyAddress;
    }
}
