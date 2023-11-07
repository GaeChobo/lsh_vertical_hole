package kr.movements.vertical.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVerticalHoleEntity is a Querydsl query type for VerticalHoleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVerticalHoleEntity extends EntityPathBase<VerticalHoleEntity> {

    private static final long serialVersionUID = -1183880675L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVerticalHoleEntity verticalHoleEntity = new QVerticalHoleEntity("verticalHoleEntity");

    public final kr.movements.vertical.entity.baseEntity.QBaseEntity _super = new kr.movements.vertical.entity.baseEntity.QBaseEntity(this);

    public final QAreaEntity area;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> drawingFileId = createNumber("drawingFileId", Long.class);

    //inherited
    public final BooleanPath hasDeleted = _super.hasDeleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> modelFileId = createNumber("modelFileId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final DatePath<java.time.LocalDate> verticalHoleEndDate = createDate("verticalHoleEndDate", java.time.LocalDate.class);

    public final StringPath verticalHoleName = createString("verticalHoleName");

    public final BooleanPath verticalHoleRider = createBoolean("verticalHoleRider");

    public final DatePath<java.time.LocalDate> verticalHoleStartDate = createDate("verticalHoleStartDate", java.time.LocalDate.class);

    public QVerticalHoleEntity(String variable) {
        this(VerticalHoleEntity.class, forVariable(variable), INITS);
    }

    public QVerticalHoleEntity(Path<? extends VerticalHoleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVerticalHoleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVerticalHoleEntity(PathMetadata metadata, PathInits inits) {
        this(VerticalHoleEntity.class, metadata, inits);
    }

    public QVerticalHoleEntity(Class<? extends VerticalHoleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.area = inits.isInitialized("area") ? new QAreaEntity(forProperty("area")) : null;
    }

}

