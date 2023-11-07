package kr.movements.vertical.entity.baseEntity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseEntity extends BaseTimeEntity {

    @Column(nullable = false)
    private boolean hasDeleted;

    public void setHasDeleted(boolean hasDeleted) {
        this.hasDeleted = hasDeleted;
    }
}