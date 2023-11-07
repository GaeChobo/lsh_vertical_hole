package kr.movements.vertical.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.movements.vertical.dto.QAlertResponse is a Querydsl Projection type for AlertResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAlertResponse extends ConstructorExpression<AlertResponse> {

    private static final long serialVersionUID = -63333881L;

    public QAlertResponse(com.querydsl.core.types.Expression<Long> slabMaterialId, com.querydsl.core.types.Expression<String> alertContextCompletion, com.querydsl.core.types.Expression<String> alertContextReceiving, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDateCompletion, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDateReceiving) {
        super(AlertResponse.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, slabMaterialId, alertContextCompletion, alertContextReceiving, createdDateCompletion, createdDateReceiving);
    }

}

