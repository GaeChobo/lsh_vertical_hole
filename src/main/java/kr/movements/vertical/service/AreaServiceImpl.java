package kr.movements.vertical.service;

import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.entity.AreaEntity;
import kr.movements.vertical.entity.MemberEntity;
import kr.movements.vertical.entity.VerticalHoleEntity;
import kr.movements.vertical.repo.AreaRepository;
import kr.movements.vertical.repo.FileRepository;
import kr.movements.vertical.repo.MemberRepository;
import kr.movements.vertical.repo.VerticalHoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Area;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final MemberRepository memberRepository;
    private final VerticalHoleRepository verticalHoleRepository;


    @Override
    public List<ConstructionVerticalResponse> verticalFileExistList(Long areaId) {

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(areaId,Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));

        return verticalHoleRepository.findConstructionVerticalsByAreaId(areaEntity.getId());
    }


    @Override
    public AreaSlabMaterialResponse areaCompletionSituation(Long areaId) {

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(areaId,Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));

        return areaRepository.getAreaSlabMaterialSituation(areaEntity.getId());
    }


    @Override
    public Long areaCreate(AreaCreateDto dto) {

        AreaEntity areaEntity = areaRepository.save(
                AreaEntity.builder()
                        .areaName(dto.getAreaName())
                        .clientName(dto.getClientName())
                        .companyAddress(dto.getCompanyAddress())
                        .companyEmail(dto.getCompanyEmail())
                        .companyName(dto.getCompanyName())
                        .build());

        return areaEntity.getId();
    }


    @Override
    public List<AreasResponse> memberAllAreaList(Long memberId, String areaName) {

        MemberEntity memberEntity = memberRepository.findByIdAndHasDeleted(memberId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("유저정보를 확인할 수 없습니다."));

        return areaRepository.findAllAreaList(memberEntity.getId(), areaName);
    }


    @Override
    public AreaInfoResponse areaInfo(Long areaId) {

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(areaId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));

        return AreaInfoResponse.builder()
                .companyAddress(areaEntity.getCompanyAddress())
                .clientName(areaEntity.getClientName())
                .companyEmail(areaEntity.getCompanyEmail())
                .areaName(areaEntity.getAreaName())
                .companyName(areaEntity.getCompanyName())
                .build();

    }

}
