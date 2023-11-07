package kr.movements.vertical.repo;

import kr.movements.vertical.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByIdAndHasDeleted(Long id, Boolean hasDeleted);

}
