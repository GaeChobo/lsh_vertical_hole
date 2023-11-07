package kr.movements.vertical.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAreaEntity is a Querydsl query type for MemberAreaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAreaEntity extends EntityPathBase<MemberAreaEntity> {

    private static final long serialVersionUID = -795639986L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAreaEntity memberAreaEntity = new QMemberAreaEntity("memberAreaEntity");

    public final kr.movements.vertical.entity.baseEntity.QBaseEntity _super = new kr.movements.vertical.entity.baseEntity.QBaseEntity(this);

    public final QAreaEntity area;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath hasDeleted = _super.hasDeleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QMemberAreaEntity(String variable) {
        this(MemberAreaEntity.class, forVariable(variable), INITS);
    }

    public QMemberAreaEntity(Path<? extends MemberAreaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAreaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAreaEntity(PathMetadata metadata, PathInits inits) {
        this(MemberAreaEntity.class, metadata, inits);
    }

    public QMemberAreaEntity(Class<? extends MemberAreaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.area = inits.isInitialized("area") ? new QAreaEntity(forProperty("area")) : null;
        this.member = inits.isInitialized("member") ? new QMemberEntity(forProperty("member")) : null;
    }

}

