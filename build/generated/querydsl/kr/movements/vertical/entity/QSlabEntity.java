package kr.movements.vertical.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSlabEntity is a Querydsl query type for SlabEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSlabEntity extends EntityPathBase<SlabEntity> {

    private static final long serialVersionUID = -1346767359L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSlabEntity slabEntity = new QSlabEntity("slabEntity");

    public final kr.movements.vertical.entity.baseEntity.QBaseEntity _super = new kr.movements.vertical.entity.baseEntity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath hasDeleted = _super.hasDeleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> slabFloor = createNumber("slabFloor", Integer.class);

    public final StringPath slabName = createString("slabName");

    public final QVerticalHoleEntity verticalHole;

    public QSlabEntity(String variable) {
        this(SlabEntity.class, forVariable(variable), INITS);
    }

    public QSlabEntity(Path<? extends SlabEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSlabEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSlabEntity(PathMetadata metadata, PathInits inits) {
        this(SlabEntity.class, metadata, inits);
    }

    public QSlabEntity(Class<? extends SlabEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.verticalHole = inits.isInitialized("verticalHole") ? new QVerticalHoleEntity(forProperty("verticalHole"), inits.get("verticalHole")) : null;
    }

}

