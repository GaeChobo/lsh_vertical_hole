package kr.movements.vertical.service;

import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.MemberAreaCreateDto;
import kr.movements.vertical.entity.AreaEntity;
import kr.movements.vertical.entity.MemberAreaEntity;
import kr.movements.vertical.entity.MemberEntity;
import kr.movements.vertical.repo.AreaRepository;
import kr.movements.vertical.repo.MemberAreaRepository;
import kr.movements.vertical.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAreaServiceImpl implements MemberAreaService {

    private final MemberAreaRepository memberAreaRepository;
    private final MemberRepository memberRepository;
    private final AreaRepository areaRepository;

    @Override
    public void memberAreaCreate(MemberAreaCreateDto dto) {

        MemberEntity memberEntity = memberRepository.findByIdAndHasDeleted(dto.getMemberId(), Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("유저정보를 확인할 수 없습니다."));

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(dto.getAreaId(), Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));

         memberAreaRepository.save(
                MemberAreaEntity.builder()
                        .member(memberEntity)
                        .area(areaEntity)
                        .build());
    }
}
