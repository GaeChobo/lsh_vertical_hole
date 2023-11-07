package kr.movements.vertical.entity;

import kr.movements.vertical.entity.baseEntity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "AREA")
@Entity
public class AreaEntity extends BaseEntity {

    @Column(length = 45)
    private String areaName;

    @Column(length = 45)
    private String clientName;

    @Column(length = 45)
    private String companyName;

    @Column(length = 100)
    private String companyAddress;

    @Column(length = 45)
    private String companyEmail;
}
