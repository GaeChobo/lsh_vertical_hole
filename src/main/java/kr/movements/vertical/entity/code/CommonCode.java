package kr.movements.vertical.entity.code;

import kr.movements.vertical.common.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CommonCode {

    SLAB_MATERIAL_STATUS("1", "슬라브 자재 상태", "10"),
    SLAB_MATERIAL_STATUS_DEFAULT("10", "슬라브 자재 초기값", "1010"),
    SLAB_MATERIAL_STATUS_RECEIVING("10", "입고 완료", "1020"),
    SLAB_MATERIAL_STATUS_COMPLETION("10", "시공 완료", "1030"),
    ALERT_LOG_TYPE("2", "알림 메시지 타입", "20"),
    ALERT_LOG_TYPE_RECEIVING("20", "입고 완료 알림", "2010"),
    ALERT_LOG_TYPE_COMPLETION("20", "시공 완료 알림", "2020");

    private String parentCode;
    private String value;
    private String code;
}