package kr.movements.vertical.service;

import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.SlabCreateDto;
import kr.movements.vertical.entity.SlabEntity;
import kr.movements.vertical.entity.VerticalHoleEntity;
import kr.movements.vertical.repo.SlabRepository;
import kr.movements.vertical.repo.VerticalHoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlabServiceImpl implements SlabService{

    private final VerticalHoleRepository verticalHoleRepository;
    private final SlabRepository slabRepository;

    @Override
    public Long slabCreate(SlabCreateDto dto) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(dto.getVerticalHoleId(), Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구 정보를 확인할 수 없습니다."));

        SlabEntity slabEntity = slabRepository.save(
                SlabEntity.builder()
                        .verticalHole(verticalHoleEntity)
                        .slabName(dto.getSlabName())
                        .slabFloor(dto.getSlabFloor())
                        .build());

        return slabEntity.getId();
    }
}
