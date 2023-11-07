package kr.movements.vertical.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.movements.vertical.dto.QCompletionFixDto is a Querydsl Projection type for CompletionFixDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCompletionFixDto extends ConstructorExpression<CompletionFixDto> {

    private static final long serialVersionUID = 692746236L;

    public QCompletionFixDto(com.querydsl.core.types.Expression<java.time.LocalDate> minCreateDate, com.querydsl.core.types.Expression<java.time.LocalDate> maxCreateDate) {
        super(CompletionFixDto.class, new Class<?>[]{java.time.LocalDate.class, java.time.LocalDate.class}, minCreateDate, maxCreateDate);
    }

}

