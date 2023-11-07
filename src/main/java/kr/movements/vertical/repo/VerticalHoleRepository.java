package kr.movements.vertical.repo;

import kr.movements.vertical.entity.AreaEntity;
import kr.movements.vertical.entity.SlabMaterialEntity;
import kr.movements.vertical.entity.VerticalHoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerticalHoleRepository extends JpaRepository<VerticalHoleEntity, Long>, VerticalHoleRepositoryCustom {

    Optional<VerticalHoleEntity> findByIdAndHasDeleted(Long verticalId, Boolean hasDeleted);

    List<VerticalHoleEntity> findAllByAreaAndHasDeleted(AreaEntity areaEntity, Boolean hasDeleted);
}
