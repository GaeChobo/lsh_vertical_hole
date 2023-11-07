package kr.movements.vertical.repo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.entity.*;
import kr.movements.vertical.entity.code.CommonCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.select;
import static kr.movements.vertical.entity.QAreaEntity.areaEntity;
import static kr.movements.vertical.entity.QSlabEntity.slabEntity;
import static kr.movements.vertical.entity.QSlabMaterialEntity.slabMaterialEntity;
import static kr.movements.vertical.entity.QVerticalHoleEntity.verticalHoleEntity;

@RequiredArgsConstructor
public class AreaRepositoryCustomImpl implements AreaRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AreaSlabMaterialResponse getAreaSlabMaterialSituation(Long areaId) {
        QAreaEntity areaEntity = QAreaEntity.areaEntity;
        QVerticalHoleEntity verticalHoleEntity = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity slabEntity = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterialEntity = QSlabMaterialEntity.slabMaterialEntity;

        BooleanExpression slabMaterialStatus1030 = slabMaterialEntity.slabMaterialStatus.eq(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode());

        AreaSlabMaterialDto subQueryResult = jpaQueryFactory
                .select(Projections.constructor(
                        AreaSlabMaterialDto.class,
                        slabMaterialEntity.id.countDistinct().as("totalSlabMaterial"),
                        ExpressionUtils
                                .as(
                                        Expressions.cases()
                                                .when(slabMaterialStatus1030)
                                                .then(slabMaterialEntity.id)
                                                .otherwise((NumberPath<Long>) null)
                                                .countDistinct(),
                                        "totalSlabMaterial1030"
                                )
                ))
                .from(areaEntity)
                .join(verticalHoleEntity).on(verticalHoleEntity.area.id.eq(areaEntity.id))
                .join(slabEntity).on(slabEntity.verticalHole.id.eq(verticalHoleEntity.id))
                .join(slabMaterialEntity).on(slabMaterialEntity.slab.id.eq(slabEntity.id))
                .where(areaEntity.id.eq(areaId))
                .fetchOne();

        // Calculate the percentage
        Long totalSlabMaterial = subQueryResult.getTotalSlabMaterial();
        Long totalSlabMaterial1030 = subQueryResult.getSlabMaterialCompletion();
        String percentageTotalSlabMaterial;
        if (totalSlabMaterial == 0) {
            percentageTotalSlabMaterial = "0%";
        } else {
            double percentage = (double) totalSlabMaterial1030 / totalSlabMaterial * 100;
            percentageTotalSlabMaterial = String.format("%.0f%%", percentage);
        }

        // Create the final response object
        return new AreaSlabMaterialResponse(
                totalSlabMaterial,
                totalSlabMaterial1030,
                percentageTotalSlabMaterial
        );
    }


    @Override
    public List<AreasResponse> findAllAreaList(Long memberId, String areaName) {

        QAreaEntity areaEntity = QAreaEntity.areaEntity;
        QMemberAreaEntity memberAreaEntity = QMemberAreaEntity.memberAreaEntity;

        return jpaQueryFactory.select(new QAreasResponse(
                areaEntity.id,
                areaEntity.areaName,
                areaEntity.clientName,
                areaEntity.companyName,
                areaEntity.companyEmail,
                areaEntity.companyAddress
        ))
                .from(areaEntity)
                .join(memberAreaEntity).on(areaEntity.id.eq(memberAreaEntity.area.id))
                .where(memberAreaEntity.member.id.eq(memberId), areaEntity.areaName.likeIgnoreCase("%" +areaName + "%"))
                .fetch();
    }

    @Override
    public List<VerticalHoleInfoResponse> getVerticalDetailsByAreaId(Long areaId) {

        QAreaEntity areaEntity = QAreaEntity.areaEntity;
        QVerticalHoleEntity verticalHoleEntity = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity slabEntity = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterialEntity = QSlabMaterialEntity.slabMaterialEntity;
        QAlertLogEntity alertLogEntity = QAlertLogEntity.alertLogEntity;

        // SQL을 사용하여 contextList 구하기
        List<Tuple> contextListTuples = jpaQueryFactory
                .select(
                        alertLogEntity.alertContext,
                        slabEntity.verticalHole.id
                )
                .from(alertLogEntity)
                .join(slabMaterialEntity).on(alertLogEntity.slabMaterial.id.eq(slabMaterialEntity.id))
                .join(slabEntity).on(slabMaterialEntity.slab.id.eq(slabEntity.id))
                .join(slabEntity.verticalHole, verticalHoleEntity) // verticalHoleEntity와 조인
                .where(verticalHoleEntity.area.id.eq(areaId)) // areaEntity 대신 verticalHoleEntity를 사용하여 조인된 테이블과 관련된 필드 조건을 설정
                .orderBy(alertLogEntity.createdDate.desc())
                .fetch();

        // Map을 사용하여 verticalHoleId를 기준으로 contextList를 그룹화
        Map<Long, List<String>> contextListMap = contextListTuples.stream()
                .collect(Collectors.groupingBy(tuple -> tuple.get(slabEntity.verticalHole.id), Collectors.mapping(tuple -> tuple.get(alertLogEntity.alertContext), Collectors.toList())));

        // 각 ID별로 최상위 4개의 contextList만 선택하여 새로운 Map을 생성
        Map<Long, List<String>> limitedContextListMap = new HashMap<>();
        for (Map.Entry<Long, List<String>> entry : contextListMap.entrySet()) {
            List<String> limitedContextList = entry.getValue().stream()
                    .limit(4)
                    .collect(Collectors.toList());
            limitedContextListMap.put(entry.getKey(), limitedContextList);
        }

        List<VerticalHoleInfoWithAlertDto> queryResult = jpaQueryFactory
                .select(Projections.constructor(
                        VerticalHoleInfoWithAlertDto.class,
                        verticalHoleEntity.id,
                        verticalHoleEntity.verticalHoleName,
                        verticalHoleEntity.verticalHoleStartDate,
                        verticalHoleEntity.verticalHoleEndDate,
                        slabMaterialEntity.countDistinct().as("verticalHoleSlabMaterialTotal"),
                        new CaseBuilder()
                                .when(slabMaterialEntity.slabMaterialStatus.eq(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode())).then(1L)
                                .otherwise(0L).sum().as("constructionCount")
                ))
                .from(areaEntity)
                .join(verticalHoleEntity).on(areaEntity.id.eq(verticalHoleEntity.area.id))
                .leftJoin(slabEntity).on(verticalHoleEntity.id.eq(slabEntity.verticalHole.id))
                .leftJoin(slabMaterialEntity).on(slabEntity.id.eq(slabMaterialEntity.slab.id))
                .where(areaEntity.id.eq(areaId))
                .groupBy(
                        verticalHoleEntity.id,
                        verticalHoleEntity.verticalHoleName,
                        verticalHoleEntity.verticalHoleStartDate,
                        verticalHoleEntity.verticalHoleEndDate
                )
                .fetch();

        List<VerticalHoleInfoResponse> responseList = queryResult.stream().map(result -> {
            Long total = result.getVerticalHoleSlabMaterialTotal();
            Long constructionCount = result.getConstructionCount();

            // 해당 verticalHoleId에 해당하는 contextList 가져오기
            List<String> contextList = limitedContextListMap.get(result.getId());

            String constructionPercentage = (total == 0) ? "0" : String.valueOf(constructionCount * 100 / total);

            return VerticalHoleInfoResponse.builder()
                    .verticalId(result.getId())
                    .verticalHoleName(result.getVerticalHoleName())
                    .verticalHoleStartDate(result.getVerticalHoleStartDate())
                    .verticalHoleEndDate(result.getVerticalHoleEndDate())
                    .verticalHoleSlabMaterialTotal(total)
                    .constructionCount(constructionCount)
                    .constructionPercentage(constructionPercentage)
                    .contextList(contextList) // 최상위 4개의 contextList
                    .build();
        }).collect(Collectors.toList());

        return responseList;
    }

    /*
    @Override
    public List<VerticalHoleInfoResponse> getVerticalDetailsByAreaId(Long areaId) {

        QAreaEntity areaEntity = QAreaEntity.areaEntity;
        QVerticalHoleEntity verticalHoleEntity = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity slabEntity = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterialEntity = QSlabMaterialEntity.slabMaterialEntity;
        QAlertLogEntity alertLogEntity = QAlertLogEntity.alertLogEntity;


        SubQueryExpression<String> alertContextSubQuery = JPAExpressions
                .select(alertLogEntity.alertContext)
                .from(alertLogEntity)
                .join(slabMaterialEntity).on(alertLogEntity.slabMaterial.id.eq(slabMaterialEntity.id))
                .join(slabEntity).on(slabMaterialEntity.slab.id.eq(slabEntity.id))
                .where(slabEntity.verticalHole.id.eq(verticalHoleEntity.id))
                .orderBy(alertLogEntity.createdDate.desc())
                .limit(4);





        List<VerticalHoleInfoWithAlertDto> queryResult = jpaQueryFactory
                .select(Projections.constructor(
                        VerticalHoleInfoWithAlertDto.class,
                        verticalHoleEntity.id,
                        verticalHoleEntity.verticalHoleName,
                        verticalHoleEntity.verticalHoleStartDate,
                        verticalHoleEntity.verticalHoleEndDate,
                        slabMaterialEntity.countDistinct().as("verticalHoleSlabMaterialTotal"),
                        new CaseBuilder()
                                .when(slabMaterialEntity.slabMaterialStatus.eq("1030")).then(1L)
                                .otherwise(0L).sum().as("constructionCount")
                        //alertContextSubQuery
                ))
                .from(areaEntity)
                .join(verticalHoleEntity).on(areaEntity.id.eq(verticalHoleEntity.area.id))
                .leftJoin(slabEntity).on(verticalHoleEntity.id.eq(slabEntity.verticalHole.id))
                .leftJoin(slabMaterialEntity).on(slabEntity.id.eq(slabMaterialEntity.slab.id))
                .where(areaEntity.id.eq(areaId))
                .groupBy(
                        verticalHoleEntity.id,
                        verticalHoleEntity.verticalHoleName,
                        verticalHoleEntity.verticalHoleStartDate,
                        verticalHoleEntity.verticalHoleEndDate
                )
                .fetch();

        return queryResult.stream().map(result -> {
            Long total = result.getVerticalHoleSlabMaterialTotal();
            Long constructionCount = result.getConstructionCount();
            String constructionPercentage = (total == 0) ? "0" : String.valueOf(constructionCount * 100 / total);

            return VerticalHoleInfoResponse.builder()
                    .verticalId(result.getId())
                    .verticalHoleName(result.getVerticalHoleName())
                    .verticalHoleStartDate(result.getVerticalHoleStartDate())
                    .verticalHoleEndDate(result.getVerticalHoleEndDate())
                    .verticalHoleSlabMaterialTotal(total)
                    .constructionCount(constructionCount)
                    .constructionPercentage(constructionPercentage)
                    //.contextList(contextList.get)
                    .build();
        }).collect(Collectors.toList());
    }

     */

    /*
    @Override
    public List<VerticalHoleInfoResponse> getVerticalDetailsByAreaId(Long areaId) {

        QAreaEntity areaEntity = QAreaEntity.areaEntity;
        QVerticalHoleEntity verticalHoleEntity = QVerticalHoleEntity.verticalHoleEntity;
        QSlabEntity slabEntity = QSlabEntity.slabEntity;
        QSlabMaterialEntity slabMaterialEntity = QSlabMaterialEntity.slabMaterialEntity;

        return jpaQueryFactory
                .select(Projections.constructor(
                        VerticalHoleInfoResponse.class,
                        verticalHoleEntity.id,
                        verticalHoleEntity.verticalHoleName,
                        verticalHoleEntity.verticalHoleStartDate,
                        verticalHoleEntity.verticalHoleEndDate,
                        slabMaterialEntity.countDistinct(),
                        new CaseBuilder()
                                .when(slabMaterialEntity.slabMaterialStatus.eq("1030")).then(1L)
                                .otherwise(0L)
                                .sum()
                ))
                .from(areaEntity)
                .join(verticalHoleEntity).on(areaEntity.id.eq(verticalHoleEntity.area.id))
                .leftJoin(slabEntity).on(verticalHoleEntity.id.eq(slabEntity.verticalHole.id))
                .leftJoin(slabMaterialEntity).on(slabEntity.id.eq(slabMaterialEntity.slab.id))
                .where(areaEntity.id.eq(areaId))
                .groupBy(verticalHoleEntity.id, verticalHoleEntity.verticalHoleName, verticalHoleEntity.verticalHoleStartDate, verticalHoleEntity.verticalHoleEndDate)
                .fetch();
    }

     */

}
