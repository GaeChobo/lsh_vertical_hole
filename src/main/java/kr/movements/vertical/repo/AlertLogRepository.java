package kr.movements.vertical.repo;

import kr.movements.vertical.entity.AlertLogEntity;
import kr.movements.vertical.entity.SlabMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertLogRepository extends JpaRepository<AlertLogEntity, Long>, AlertLogRepositoryCustom {

    boolean existsBySlabMaterialAndAlertTypeAndHasDeleted(SlabMaterialEntity slabMaterialEntity, String AlertTypeCode, Boolean hasDeleted);

    List<AlertLogEntity> findAllBySlabMaterialAndHasDeleted(SlabMaterialEntity slabMaterialEntity, Boolean hasDeleted);

}
