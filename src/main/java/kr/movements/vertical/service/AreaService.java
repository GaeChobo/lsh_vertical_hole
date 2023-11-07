package kr.movements.vertical.service;

import kr.movements.vertical.dto.*;

import java.util.List;

public interface AreaService {

    List<ConstructionVerticalResponse> verticalFileExistList(Long areaId);

    Long areaCreate(AreaCreateDto dto);

    List<AreasResponse> memberAllAreaList(Long memberId, String areaName);

    AreaInfoResponse areaInfo(Long areaId);

    AreaSlabMaterialResponse areaCompletionSituation(Long areaId);

}
