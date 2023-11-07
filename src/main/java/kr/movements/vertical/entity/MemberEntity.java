package kr.movements.vertical.entity;

import kr.movements.vertical.entity.baseEntity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
public class MemberEntity extends BaseEntity {

    @Column(length = 45)
    private String userEmail;

    @Column(length = 45)
    private String userName;

    @Column
    private String userPw;

    @Column(length = 13)
    private String userPhone;

}
