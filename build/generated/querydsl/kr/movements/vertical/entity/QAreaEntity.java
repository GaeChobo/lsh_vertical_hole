package kr.movements.vertical.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAreaEntity is a Querydsl query type for AreaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAreaEntity extends EntityPathBase<AreaEntity> {

    private static final long serialVersionUID = 1724453908L;

    public static final QAreaEntity areaEntity = new QAreaEntity("areaEntity");

    public final kr.movements.vertical.entity.baseEntity.QBaseEntity _super = new kr.movements.vertical.entity.baseEntity.QBaseEntity(this);

    public final StringPath areaName = createString("areaName");

    public final StringPath clientName = createString("clientName");

    public final StringPath companyAddress = createString("companyAddress");

    public final StringPath companyEmail = createString("companyEmail");

    public final StringPath companyName = createString("companyName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath hasDeleted = _super.hasDeleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QAreaEntity(String variable) {
        super(AreaEntity.class, forVariable(variable));
    }

    public QAreaEntity(Path<? extends AreaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAreaEntity(PathMetadata metadata) {
        super(AreaEntity.class, metadata);
    }

}

