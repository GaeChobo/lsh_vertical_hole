package kr.movements.vertical.repo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.movements.vertical.dto.AlertResponse;
import kr.movements.vertical.entity.QAlertLogEntity;
import kr.movements.vertical.entity.QSlabEntity;
import kr.movements.vertical.entity.QSlabMaterialEntity;
import kr.movements.vertical.entity.QVerticalHoleEntity;
import kr.movements.vertical.entity.code.CommonCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AlertLogRepositoryCustomImpl implements AlertLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AlertResponse> findAlertResponsesByVerticalHoleId(Long verticalHoleId) {
        QAlertLogEntity alertLog = QAlertLogEntity.alertLogEntity;
        QSlabMaterialEntity slabMaterial = QSlabMaterialEntity.slabMaterialEntity;
        QSlabEntity slab = QSlabEntity.slabEntity;
        QVerticalHoleEntity verticalHole = QVerticalHoleEntity.verticalHoleEntity;


        List<Tuple> tuples = jpaQueryFactory
                .select(
                        alertLog.slabMaterial.id,
                        alertLog.alertType,
                        alertLog.alertContext,
                        alertLog.createdDate,
                        alertLog.hasDeleted
                )
                .from(alertLog)
                .join(slabMaterial).on(alertLog.slabMaterial.id.eq(slabMaterial.id))
                .join(slab).on(slabMaterial.slab.id.eq(slab.id))
                .join(verticalHole).on(slab.verticalHole.id.eq(verticalHole.id))
                .where(
                        verticalHole.id.eq(verticalHoleId),
                        alertLog.alertType.in(CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode(), CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode()),
                        alertLog.hasDeleted.eq(false)
                )
                .fetch();

        Map<Long, List<Tuple>> groupedTuples = tuples.stream()
                .collect(Collectors.groupingBy(tuple -> tuple.get(alertLog.slabMaterial.id)));

        List<AlertResponse> result = groupedTuples.entrySet().stream()
                .map(entry -> {
                    Long slabMaterialId = entry.getKey();
                    List<Tuple> group = entry.getValue();

                    String alertContextCompletion = "";
                    String alertContextReceiving = "";
                    LocalDateTime createdDateCompletion = LocalDateTime.parse("2000-01-01T00:00:00");
                    LocalDateTime createdDateReceiving = LocalDateTime.parse("2000-01-01T00:00:00");

                    for (Tuple tuple : group) {
                        String alertType = tuple.get(alertLog.alertType);
                        String alertContext = tuple.get(alertLog.alertContext);
                        LocalDateTime createdDate = tuple.get(alertLog.createdDate);

                        if (CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode().equals(alertType)) {
                            alertContextCompletion = alertContext;
                            createdDateCompletion = createdDate;
                        } else if (CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode().equals(alertType)) {
                            alertContextReceiving = alertContext;
                            createdDateReceiving = createdDate;
                        }
                    }

                    return new AlertResponse(
                            slabMaterialId,
                            alertContextCompletion,
                            alertContextReceiving,
                            createdDateCompletion,
                            createdDateReceiving
                    );
                })
                .collect(Collectors.toList());

        return result;
    }
}
