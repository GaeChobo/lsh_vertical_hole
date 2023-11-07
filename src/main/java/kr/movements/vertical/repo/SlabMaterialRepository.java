package kr.movements.vertical.repo;

import kr.movements.vertical.entity.SlabEntity;
import kr.movements.vertical.entity.SlabMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlabMaterialRepository extends JpaRepository<SlabMaterialEntity, Long> {

    Optional<SlabMaterialEntity> findByIdAndHasDeleted(Long slabMaterialId, Boolean hasDeleted);

    List<SlabMaterialEntity> findAllBySlabAndHasDeleted(SlabEntity slabEntity, Boolean hasDeleted);

    Long countBySlabAndSlabMaterialStatusAndHasDeleted(SlabEntity slab, String statusCode, Boolean hasDeleted);

    Long countBySlabAndHasDeleted(SlabEntity slab, Boolean hasDeleted);
}
