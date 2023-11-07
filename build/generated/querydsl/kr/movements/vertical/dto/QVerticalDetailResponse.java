package kr.movements.vertical.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.movements.vertical.dto.QVerticalDetailResponse is a Querydsl Projection type for VerticalDetailResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QVerticalDetailResponse extends ConstructorExpression<VerticalDetailResponse> {

    private static final long serialVersionUID = 1836456894L;

    public QVerticalDetailResponse(com.querydsl.core.types.Expression<String> verticalHoleName, com.querydsl.core.types.Expression<Long> slabMaterialTotal, com.querydsl.core.types.Expression<Long> slabMaterialReceiving, com.querydsl.core.types.Expression<Long> slabMaterialCompletion, com.querydsl.core.types.Expression<Integer> constructionPercent, com.querydsl.core.types.Expression<java.time.LocalDate> verticalHoleStartDate, com.querydsl.core.types.Expression<java.time.LocalDate> verticalHoleEndDate, com.querydsl.core.types.Expression<java.time.LocalDate> earliestDate, com.querydsl.core.types.Expression<java.time.LocalDate> latestDate) {
        super(VerticalDetailResponse.class, new Class<?>[]{String.class, long.class, long.class, long.class, int.class, java.time.LocalDate.class, java.time.LocalDate.class, java.time.LocalDate.class, java.time.LocalDate.class}, verticalHoleName, slabMaterialTotal, slabMaterialReceiving, slabMaterialCompletion, constructionPercent, verticalHoleStartDate, verticalHoleEndDate, earliestDate, latestDate);
    }

}

