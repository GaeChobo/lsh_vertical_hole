package kr.movements.vertical.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSlabMaterialEntity is a Querydsl query type for SlabMaterialEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSlabMaterialEntity extends EntityPathBase<SlabMaterialEntity> {

    private static final long serialVersionUID = -654798840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSlabMaterialEntity slabMaterialEntity = new QSlabMaterialEntity("slabMaterialEntity");

    public final kr.movements.vertical.entity.baseEntity.QBaseEntity _super = new kr.movements.vertical.entity.baseEntity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath hasDeleted = _super.hasDeleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QSlabEntity slab;

    public final NumberPath<Double> slabMaterialHorizontal = createNumber("slabMaterialHorizontal", Double.class);

    public final StringPath slabMaterialName = createString("slabMaterialName");

    public final NumberPath<Integer> slabMaterialProcess = createNumber("slabMaterialProcess", Integer.class);

    public final StringPath slabMaterialStatus = createString("slabMaterialStatus");

    public final NumberPath<Double> slabMaterialVertical = createNumber("slabMaterialVertical", Double.class);

    public QSlabMaterialEntity(String variable) {
        this(SlabMaterialEntity.class, forVariable(variable), INITS);
    }

    public QSlabMaterialEntity(Path<? extends SlabMaterialEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSlabMaterialEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSlabMaterialEntity(PathMetadata metadata, PathInits inits) {
        this(SlabMaterialEntity.class, metadata, inits);
    }

    public QSlabMaterialEntity(Class<? extends SlabMaterialEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.slab = inits.isInitialized("slab") ? new QSlabEntity(forProperty("slab"), inits.get("slab")) : null;
    }

}

