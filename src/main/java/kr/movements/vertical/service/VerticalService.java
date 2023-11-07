package kr.movements.vertical.service;

import kr.movements.vertical.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface VerticalService {

    void verticalDrawingFileUpdate(Long verticalId, Long fileId);

    Long getModelFileId(Long verticalId);

    Long getDrawingFileId(Long verticalId);

    void verticalModelFileUpdate(Long verticalId, Long fileId, Boolean riderType);

    VerticalHoleSlabInfoDto appVerticalDetail(Long verticalId);

    List<AppVerticalHoleInfoDto> appVerticalList(Long areaId);

    Long verticalCreate(VerticalCreateDto dto);

    VerticalDetailResponse verticalPercentage(Long verticalHoleId);

    //List<VerticalHoleInfoResponse> verticalSituationList(Long areaId);

    List<VerticalHoleInfoResponse> verticalInfoList(Long areaId);

    void verticalScheduleUpdate(Long verticalId, LocalDate startDate, LocalDate endDate);

    List<AlertLogResponse> verticalAlertList(Long verticalId);

    VerticalHoleTimeResponse getVerticalHoleInfo(Long verticalHoleId);
}
