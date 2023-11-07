package kr.movements.vertical.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.ApiOperation;
import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.entity.*;
import kr.movements.vertical.entity.code.CommonCode;
import kr.movements.vertical.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerticalServiceImpl implements VerticalService {

    private final VerticalHoleRepository verticalHoleRepository;
    private final AreaRepository areaRepository;
    private final SlabMaterialRepository slabMaterialRepository;
    private final SlabRepository slabRepository;
    private final AlertLogRepository alertLogRepository;

    @Override
    public Long getModelFileId(Long verticalId) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        return verticalHoleEntity.getModelFileId();
    }

    @Override
    public Long getDrawingFileId(Long verticalId) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        return verticalHoleEntity.getDrawingFileId();
    }

    @Override
    public void verticalDrawingFileUpdate(Long verticalId, Long fileId) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        verticalHoleEntity.verticalHoleDrawingFileUpdate(fileId);
    }

    @Override
    public void verticalModelFileUpdate(Long verticalId, Long fileId, Boolean riderType) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        verticalHoleEntity.verticalHoleModelFileUpdate(fileId, riderType);

    }

    @Override
    public VerticalHoleSlabInfoDto appVerticalDetail(Long verticalId) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        return verticalHoleRepository.getSlabAlertContextByVerticalHoleId(verticalHoleEntity.getId());
    }

    @Override
    public List<AppVerticalHoleInfoDto> appVerticalList(Long areaId) {

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(areaId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));


        return verticalHoleRepository.getVerticalDetailsByAreaId(areaEntity.getId());
    }

    @Override
    public VerticalHoleTimeResponse getVerticalHoleInfo(Long verticalHoleId) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalHoleId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        VerticalHoleGraphResponse graphResponse = verticalHoleRepository.findVerticalHoleInfo(verticalHoleEntity.getId());
        List<AlertResponse> alertResponses = alertLogRepository.findAlertResponsesByVerticalHoleId(verticalHoleEntity.getId());

        return new VerticalHoleTimeResponse(graphResponse, alertResponses);

    }

    @Override
    public List<AlertLogResponse> verticalAlertList(Long verticalId) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        return verticalHoleRepository.getLatestAlertLogsByVerticalId(verticalHoleEntity.getId());

    }

    @Override
    public List<VerticalHoleInfoResponse> verticalInfoList(Long areaId) {

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(areaId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));

        return areaRepository.getVerticalDetailsByAreaId(areaEntity.getId());
    }

    @Override
    public void verticalScheduleUpdate(Long verticalId, LocalDate startDate, LocalDate endDate) {
        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구정보를 확인할 수 없습니다."));

        Optional<CompletionFixDto> completionFixDtoOptional = verticalHoleRepository.getMinMaxCreatedDateByVerticalId(verticalHoleEntity.getId());

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("시작 날짜는 종료 날짜보다 빠를 수 없습니다.");
        }

        if (completionFixDtoOptional.isPresent()) {
            CompletionFixDto completionFixDto = completionFixDtoOptional.get();
            LocalDate existingMinCreateDate = completionFixDto.getMinCreateDate();
            LocalDate existingMaxCreateDate = completionFixDto.getMaxCreateDate();

            if ((startDate.isBefore(existingMinCreateDate) || startDate.isEqual(existingMinCreateDate)) &&
                    (endDate.isAfter(existingMaxCreateDate) || endDate.isEqual(existingMaxCreateDate))) {
                // 업데이트 허용

                verticalHoleEntity.verticalScheduleUpdate(startDate, endDate);


            } else {
                throw new BadRequestException("수정하려는 날짜가 기존 시공일정을 벗어납니다.");
            }
        }

        verticalHoleEntity.verticalScheduleUpdate(startDate, endDate);
    }









    /*
    @Override
    public List<VerticalHoleInfoResponse> verticalSituationList(Long areaId) {

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(areaId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));

        List<VerticalHoleEntity> verticalHoleEntities = verticalHoleRepository.findAllByAreaAndHasDeleted(areaEntity, Boolean.FALSE);

        if (verticalHoleEntities.isEmpty()) {
            throw new BadRequestException("해당 수직구에 대한 정보가 없습니다.");
        }

        List<SlabEntity> slabEntities = new ArrayList<>();

        for (VerticalHoleEntity verticalHoleEntity : verticalHoleEntities) {
            List<SlabEntity> slabs = slabRepository.findAllByVerticalHoleAndHasDeleted(verticalHoleEntity, Boolean.FALSE);
            slabEntities.addAll(slabs);

            List<SlabMaterialEntity> slabMaterialEntities = slabMaterialRepository.findAllBySlabAndHasDeleted()

            slabEntities.get().getId()
        }

        List<VerticalHoleInfoResponse> result = new ArrayList<>();

        for (VerticalHoleEntity verticalHoleEntity : verticalHoleEntities) {
            VerticalHoleInfoResponse dto = VerticalHoleInfoResponse.builder()
                    .verticalId((verticalHoleEntity.getId()))
                    .verticalHoleName(verticalHoleEntity.getVerticalHoleName())
                    .verticalHoleStartDate(verticalHoleEntity.getVerticalHoleStartDate())
                    .verticalHoleEndDate(verticalHoleEntity.getVerticalHoleEndDate())
                    .build();
            result.add(dto);
        }


        return result;
    }

     */

    @Override
    public Long verticalCreate(VerticalCreateDto dto) {

        AreaEntity areaEntity = areaRepository.findByIdAndHasDeleted(dto.getAreaId(), Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("현장정보를 확인할 수 없습니다."));

        if (dto.getVerticalStartDate().isAfter(dto.getVerticalEndDate())) {
            throw new BadRequestException("시작 날짜는 종료 날짜보다 빠를 수 없습니다.");
        }

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.save(
                VerticalHoleEntity.builder()
                        .verticalHoleRider(false)
                        .verticalHoleName(dto.getVerticalHoleName())
                        .area(areaEntity)
                        .verticalHoleStartDate(dto.getVerticalStartDate())
                        .verticalHoleEndDate(dto.getVerticalEndDate())
                        .build());

        return verticalHoleEntity.getId();
    }

    @Override
    public VerticalDetailResponse verticalPercentage(Long verticalHoleId) {

        VerticalHoleEntity verticalHoleEntity = verticalHoleRepository.findByIdAndHasDeleted(verticalHoleId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("수직구 정보를 확인할 수 없습니다."));

        List<SlabEntity> slabEntities  = slabRepository.findAllByVerticalHoleAndHasDeleted(verticalHoleEntity, Boolean.FALSE);

        if (slabEntities.isEmpty()) {
            throw new BadRequestException("해당 수직구에 대한 슬라브정보가 없습니다.");
        }

        Long totalSlabReceiving = 0L;
        Long totalSlabCompletion = 0L;
        Long totalSlabTotal = 0L;

        LocalDate earliestDate = null;
        LocalDate latestDate = null;

        for (SlabEntity slabEntity : slabEntities) {

            List<SlabMaterialEntity> slabMaterialEntities = slabMaterialRepository.findAllBySlabAndHasDeleted(slabEntity, Boolean.FALSE);

            for(SlabMaterialEntity slabMaterialEntity : slabMaterialEntities) {

                List<AlertLogEntity> alertLogEntities = alertLogRepository.findAllBySlabMaterialAndHasDeleted(slabMaterialEntity, Boolean.FALSE);

                for (AlertLogEntity alertLogEntity : alertLogEntities) {

                    if (alertLogEntity.getAlertType().equals(CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode())) { // alertStatus가 2020인 경우에만 고려
                        LocalDate createDate = alertLogEntity.getCreatedDate().toLocalDate();

                        // 초기 변수값이 없다면 현재 alertLogEntity의 createDate를 가장 낮은 값과 높은 값으로 설정
                        if (earliestDate == null || createDate.isBefore(earliestDate)) {
                            earliestDate = createDate;
                        }

                        if (latestDate == null || createDate.isAfter(latestDate)) {
                            latestDate = createDate;
                        }
                    }
                }

                // "2010"과 "2020"의 개수 세기
                long count2010 = alertLogEntities.stream().filter(alertLog -> alertLog.getAlertType().equals(CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode())).count();
                long count2020 = alertLogEntities.stream().filter(alertLog -> alertLog.getAlertType().equals(CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode())).count();

                // 개수 더하기
                totalSlabReceiving += count2010;
                totalSlabCompletion += count2020;
            }

            Long slabTotal = slabMaterialRepository.countBySlabAndHasDeleted(slabEntity, Boolean.FALSE);
            totalSlabTotal += slabTotal;
        }

        // 각각의 총합 구하기 (위에서 이미 구한 totalSlabReceiving, totalSlabCompletion, totalSlabTotal이라 가정)
        double completionPercent = totalSlabTotal > 0 ? (double) totalSlabCompletion / totalSlabTotal * 100 : 0.0;

        // 퍼센트를 정수형으로 변환하여 소수점 제거
        int roundedCompletionPercent = (int) Math.floor(completionPercent);

        return VerticalDetailResponse.builder()
                .verticalHoleName(verticalHoleEntity.getVerticalHoleName())
                .verticalHoleStartDate(verticalHoleEntity.getVerticalHoleStartDate())
                .verticalHoleEndDate(verticalHoleEntity.getVerticalHoleEndDate())
                .slabMaterialCompletion(totalSlabCompletion)
                .slabMaterialReceiving(totalSlabReceiving)
                .slabMaterialTotal(totalSlabTotal)
                .constructionPercent(roundedCompletionPercent)
                .earliestDate(earliestDate)
                .latestDate(latestDate)
                .build();
    }

}
