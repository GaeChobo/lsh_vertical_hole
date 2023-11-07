package kr.movements.vertical.repo;

import kr.movements.vertical.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUserEmailAndHasDeleted(String userEmail, Boolean hasDeleted);
    Optional<MemberEntity> findByIdAndHasDeleted(Long memberId, Boolean hasDeleted);
    boolean existsByUserEmailAndHasDeleted(String userEmail, boolean hasDeleted);

    boolean existsByUserPhoneAndHasDeleted(String userPhone, boolean hasDeleted);
}
