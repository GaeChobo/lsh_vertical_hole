package kr.movements.vertical.repo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.entity.*;
import kr.movements.vertical.entity.code.CommonCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerticalHoleRepositoryCustomImpl implements VerticalHoleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ConstructionVerticalResponse> findConstructionVerticalsByAreaId(Long areaId) {

        QVerticalHoleEntity qVerticalHole = QVerticalHoleEntity.verticalHoleEntity;

        return jpaQueryFactory.select(Projections.constructor(ConstructionVerticalResponse.class,
                        qVerticalHole.id,
                        qVerticalHole.verticalHoleName,
                        qVerticalHole.drawingFileId,
                        qVerticalHole.verticalHoleRider))
                .from(qVerticalHole)
                .where(qVerticalHole.area.id.eq(areaId))
                .fetch();
    }

    @Override
    public VerticalHoleSlabInfoDto getSlabAlertContextByVerticalHoleId(Long verticalHoleId) {
        QSlabEntity slab = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterial = QSlabMaterialEntity.slabMaterialEntity;
        QAlertLogEntity alertLog = QAlertLogEntity.alertLogEntity;

        List<Tuple> tuples = jpaQueryFactory
                .select(
                        slab.slabFloor,
                        alertLog.alertContext
                )
                .from(slab)
                .leftJoin(slabMaterial).on(slab.id.eq(slabMaterial.slab.id))
                .leftJoin(alertLog).on(slabMaterial.id.eq(alertLog.slabMaterial.id))
                .where(slab.verticalHole.id.eq(verticalHoleId))
                .fetch();

        Map<String, List<String>> slabAlertContextMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 숫자 부분을 뽑아내고 정수로 변환하여 정렬
                int floor1 = Integer.parseInt(o1.replaceAll("\\D", ""));
                int floor2 = Integer.parseInt(o2.replaceAll("\\D", ""));
                return Integer.compare(floor1, floor2);
            }
        });

        for (Tuple tuple : tuples) {
            String slabFloor = tuple.get(slab.slabFloor) + "B";
            String alertContext = tuple.get(alertLog.alertContext);
            slabAlertContextMap.computeIfAbsent(slabFloor, k -> new ArrayList<>()).add(alertContext);
        }

        return VerticalHoleSlabInfoDto.builder()
                .slabAlertContextMap(slabAlertContextMap)
                .build();
    }

    @Override
    public List<AppVerticalHoleInfoDto> getVerticalDetailsByAreaId(Long areaId) {
        QVerticalHoleEntity vh = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity s = QSlabEntity.slabEntity;
        QSlabMaterialEntity sm = QSlabMaterialEntity.slabMaterialEntity;
        QAlertLogEntity al = QAlertLogEntity.alertLogEntity;

        return jpaQueryFactory
                .select(
                        vh.id,
                        vh.verticalHoleName,
                        sm.countDistinct().as("verticalHoleSlabMaterialTotal"),
                        new CaseBuilder()
                                .when(al.alertType.eq(CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode())).then(1L)
                                .otherwise(0L).sum().as("slabMaterialReceiving"),
                        new CaseBuilder()
                                .when(al.alertType.eq(CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode())).then(1L)
                                .otherwise(0L).sum().as("slabMaterialCompletion")
                )
                .from(vh)
                .join(s).on(vh.id.eq(s.verticalHole.id))
                .join(sm).on(s.id.eq(sm.slab.id))
                .leftJoin(al).on(sm.id.eq(al.slabMaterial.id))
                .where(vh.area.id.eq(areaId))
                .groupBy(vh.id, vh.verticalHoleName)
                .fetch()
                .stream()
                .map(tuple -> new AppVerticalHoleInfoDto(
                        tuple.get(vh.id),
                        tuple.get(vh.verticalHoleName),
                        tuple.get(2, Long.class),
                        tuple.get(3, Long.class),
                        tuple.get(4, Long.class)
                ))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public VerticalHoleGraphResponse findVerticalHoleInfo(Long verticalHoleId) {
        QVerticalHoleEntity verticalHole = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity slab = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterial = QSlabMaterialEntity.slabMaterialEntity;

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                VerticalHoleGraphResponse.class,
                                verticalHole.verticalHoleEndDate,
                                verticalHole.verticalHoleStartDate,
                                slabMaterial.id.count()
                        )
                )
                .from(verticalHole)
                .leftJoin(slab).on(verticalHole.id.eq(slab.verticalHole.id))
                .leftJoin(slabMaterial).on(slab.id.eq(slabMaterial.slab.id))
                .where(
                        verticalHole.id.eq(verticalHoleId)
                )
                .groupBy(verticalHole.verticalHoleEndDate, verticalHole.verticalHoleStartDate)
                .fetchOne();  // 단일 객체 반환
    }

    @Override
    public List<AlertLogResponse> getLatestAlertLogsByVerticalId(Long verticalId) {
        QVerticalHoleEntity verticalHoleEntity = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity slabEntity = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterialEntity = QSlabMaterialEntity.slabMaterialEntity;
        QAlertLogEntity alertLogEntity = QAlertLogEntity.alertLogEntity;

        List<AlertLogResponse> latestAlertLogs = jpaQueryFactory.select(new QAlertLogResponse(
                alertLogEntity.alertContext,
                alertLogEntity.createdDate
        ))
                .from(verticalHoleEntity)
                .join(slabEntity).on(verticalHoleEntity.id.eq(slabEntity.verticalHole.id))
                .join(slabMaterialEntity).on(slabEntity.id.eq(slabMaterialEntity.slab.id))
                .join(alertLogEntity).on(slabMaterialEntity.id.eq(alertLogEntity.slabMaterial.id))
                .where(verticalHoleEntity.id.eq(verticalId))
                .orderBy(alertLogEntity.createdDate.desc())
                .limit(6)
                .fetch();

        return latestAlertLogs;
    }

    @Override
    public Optional<CompletionFixDto> getMinMaxCreatedDateByVerticalId(Long verticalId) {
        QVerticalHoleEntity verticalHoleEntity = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity slabEntity = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterialEntity = QSlabMaterialEntity.slabMaterialEntity;
        QAlertLogEntity alertLogEntity = QAlertLogEntity.alertLogEntity;

        Tuple tuple = jpaQueryFactory
                .select(
                        alertLogEntity.createdDate.min().as("minCreateDate"),
                        alertLogEntity.createdDate.max().as("maxCreateDate")
                )
                .from(verticalHoleEntity)
                .join(slabEntity).on(verticalHoleEntity.id.eq(slabEntity.verticalHole.id))
                .join(slabMaterialEntity).on(slabEntity.id.eq(slabMaterialEntity.slab.id))
                .join(alertLogEntity).on(slabMaterialEntity.id.eq(alertLogEntity.slabMaterial.id))
                .where(verticalHoleEntity.id.eq(verticalId))
                .where(alertLogEntity.alertType.eq(CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode()))
                .fetchOne();

        // Tuple에서 값을 꺼내 CompletionFixDto로 매핑
        LocalDateTime minCreateDate = tuple.get(alertLogEntity.createdDate.min().as("minCreateDate"));
        LocalDateTime maxCreateDate = tuple.get(alertLogEntity.createdDate.max().as("maxCreateDate"));

        if (minCreateDate != null && maxCreateDate != null) {
            LocalDate minLocalDate = minCreateDate.toLocalDate();
            LocalDate maxLocalDate = maxCreateDate.toLocalDate();
            return Optional.of(new CompletionFixDto(minLocalDate, maxLocalDate));
        }

        return Optional.empty();
    }
}
