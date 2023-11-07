package kr.movements.vertical.repo;

import kr.movements.vertical.entity.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<AreaEntity, Long>, AreaRepositoryCustom {

    Optional<AreaEntity> findByIdAndHasDeleted(Long areaId,Boolean hasDeleted);
}
