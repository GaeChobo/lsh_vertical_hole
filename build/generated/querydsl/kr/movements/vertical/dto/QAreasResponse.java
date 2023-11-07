package kr.movements.vertical.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.movements.vertical.dto.QAreasResponse is a Querydsl Projection type for AreasResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAreasResponse extends ConstructorExpression<AreasResponse> {

    private static final long serialVersionUID = 1564379185L;

    public QAreasResponse(com.querydsl.core.types.Expression<Long> areaId, com.querydsl.core.types.Expression<String> areaName, com.querydsl.core.types.Expression<String> clientName, com.querydsl.core.types.Expression<String> companyName, com.querydsl.core.types.Expression<String> companyEmail, com.querydsl.core.types.Expression<String> companyAddress) {
        super(AreasResponse.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class}, areaId, areaName, clientName, companyName, companyEmail, companyAddress);
    }

}

