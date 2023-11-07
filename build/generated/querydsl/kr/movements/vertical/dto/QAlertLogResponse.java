package kr.movements.vertical.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.movements.vertical.dto.QAlertLogResponse is a Querydsl Projection type for AlertLogResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAlertLogResponse extends ConstructorExpression<AlertLogResponse> {

    private static final long serialVersionUID = -1736772577L;

    public QAlertLogResponse(com.querydsl.core.types.Expression<String> alertContext, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate) {
        super(AlertLogResponse.class, new Class<?>[]{String.class, java.time.LocalDateTime.class}, alertContext, createdDate);
    }

}

