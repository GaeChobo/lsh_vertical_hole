package kr.movements.vertical.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.movements.vertical.dto.QAlertInfoTupleDto is a Querydsl Projection type for AlertInfoTupleDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAlertInfoTupleDto extends ConstructorExpression<AlertInfoTupleDto> {

    private static final long serialVersionUID = 638221963L;

    public QAlertInfoTupleDto(com.querydsl.core.types.Expression<Long> slabMaterialId, com.querydsl.core.types.Expression<String> alertContextCompletion, com.querydsl.core.types.Expression<String> alertContextReceiving, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDateCompletion, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDateReceiving, com.querydsl.core.types.Expression<String> slabMaterialName) {
        super(AlertInfoTupleDto.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class}, slabMaterialId, alertContextCompletion, alertContextReceiving, createdDateCompletion, createdDateReceiving, slabMaterialName);
    }

}

