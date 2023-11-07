package kr.movements.vertical.entity;

import kr.movements.vertical.entity.baseEntity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SLAB")
@Entity
public class SlabEntity extends BaseEntity {

    @Column(length = 45)
    private String slabName;
    @Column
    private int slabFloor;

    @ManyToOne
    @JoinColumn(name="vertical_hole_id", nullable = false)
    private VerticalHoleEntity verticalHole;
}
