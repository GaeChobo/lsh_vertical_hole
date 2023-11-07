package kr.movements.vertical.repo;

import kr.movements.vertical.dto.AlertResponse;

import java.util.List;

public interface AlertLogRepositoryCustom {

    List<AlertResponse> findAlertResponsesByVerticalHoleId(Long verticalHoleId);
}
