package kr.movements.vertical.entity;

import kr.movements.vertical.entity.baseEntity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SLAB_MATERIAL")
@Entity
public class SlabMaterialEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="slab_id", nullable = false)
    private SlabEntity slab;

    @Column
    private Double slabMaterialHorizontal;

    @Column
    private Double slabMaterialVertical;

    @Column(length = 45)
    private String slabMaterialStatus;

    @Column(length = 45)
    private String slabMaterialName;

    @Column
    private int slabMaterialProcess;

    public void sensorUpdate(Double slabMaterialHorizontal, Double slabMaterialVertical) {
        this.slabMaterialHorizontal = slabMaterialHorizontal;
        this.slabMaterialVertical = slabMaterialVertical;
    }

    public void slabStatusUpdate(String slabMaterialStatus) {
        this.slabMaterialStatus = slabMaterialStatus;
    }
}
