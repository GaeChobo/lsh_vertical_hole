package kr.movements.vertical.entity;

import kr.movements.vertical.entity.baseEntity.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ALERT_LOG")
@Entity
public class AlertLogEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="slab_material_id", nullable = false)
    private SlabMaterialEntity slabMaterial;

    @Column(columnDefinition = "Text")
    private String alertContext;

    @Column
    private String alertType;

}
