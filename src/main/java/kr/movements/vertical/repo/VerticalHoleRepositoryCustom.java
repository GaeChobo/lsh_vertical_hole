package kr.movements.vertical.repo;

import kr.movements.vertical.dto.*;

import java.util.List;
import java.util.Optional;

public interface VerticalHoleRepositoryCustom {

    List<ConstructionVerticalResponse> findConstructionVerticalsByAreaId(Long areaId);

    VerticalHoleSlabInfoDto getSlabAlertContextByVerticalHoleId(Long verticalHoleId);

    Optional<CompletionFixDto> getMinMaxCreatedDateByVerticalId(Long verticalId);

    List<AlertLogResponse> getLatestAlertLogsByVerticalId(Long verticalId);

    List<AppVerticalHoleInfoDto> getVerticalDetailsByAreaId(Long areaId);

    VerticalHoleGraphResponse findVerticalHoleInfo(Long verticalHoleId);
}