package kr.movements.vertical.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlertLogEntity is a Querydsl query type for AlertLogEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlertLogEntity extends EntityPathBase<AlertLogEntity> {

    private static final long serialVersionUID = -1477386865L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlertLogEntity alertLogEntity = new QAlertLogEntity("alertLogEntity");

    public final kr.movements.vertical.entity.baseEntity.QBaseEntity _super = new kr.movements.vertical.entity.baseEntity.QBaseEntity(this);

    public final StringPath alertContext = createString("alertContext");

    public final StringPath alertType = createString("alertType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath hasDeleted = _super.hasDeleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QSlabMaterialEntity slabMaterial;

    public QAlertLogEntity(String variable) {
        this(AlertLogEntity.class, forVariable(variable), INITS);
    }

    public QAlertLogEntity(Path<? extends AlertLogEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlertLogEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlertLogEntity(PathMetadata metadata, PathInits inits) {
        this(AlertLogEntity.class, metadata, inits);
    }

    public QAlertLogEntity(Class<? extends AlertLogEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.slabMaterial = inits.isInitialized("slabMaterial") ? new QSlabMaterialEntity(forProperty("slabMaterial"), inits.get("slabMaterial")) : null;
    }

}

