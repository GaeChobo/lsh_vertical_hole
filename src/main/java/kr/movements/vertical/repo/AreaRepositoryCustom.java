package kr.movements.vertical.repo;

import kr.movements.vertical.dto.AreaSlabMaterialResponse;
import kr.movements.vertical.dto.AreasResponse;
import kr.movements.vertical.dto.VerticalDetailResponse;
import kr.movements.vertical.dto.VerticalHoleInfoResponse;

import java.util.List;

public interface AreaRepositoryCustom {

    List<AreasResponse> findAllAreaList(Long memberId, String areaName);

    List<VerticalHoleInfoResponse> getVerticalDetailsByAreaId(Long areaId);

    AreaSlabMaterialResponse getAreaSlabMaterialSituation(Long areaId);
}
