package kr.movements.vertical.repo;

import kr.movements.vertical.entity.SlabEntity;
import kr.movements.vertical.entity.VerticalHoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlabRepository extends JpaRepository<SlabEntity, Long> {

    List<SlabEntity> findAllByVerticalHoleAndHasDeleted(VerticalHoleEntity vertical, Boolean hasDeleted);

    Optional<SlabEntity> findByIdAndHasDeleted(Long slabId, Boolean hasDeleted);

    Optional<SlabEntity> findByVerticalHoleIdAndHasDeleted(Long verticalId, Boolean hasDeleted);


}
