package kr.movements.vertical.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.entity.AlertLogEntity;
import kr.movements.vertical.entity.SlabEntity;
import kr.movements.vertical.entity.SlabMaterialEntity;
import kr.movements.vertical.entity.baseEntity.BaseEntity;
import kr.movements.vertical.entity.code.CommonCode;
import kr.movements.vertical.repo.AlertLogRepository;
import kr.movements.vertical.repo.SlabMaterialRepository;
import kr.movements.vertical.repo.SlabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlabMaterialServiceImpl implements SlabMaterialService {

    private final SlabMaterialRepository slabMaterialRepository;
    private final SlabRepository slabRepository;
    private final AlertLogRepository alertLogRepository;

    @Override
    public SlabMaterialNameResponse slabMaterialNameAndId(Long slabMaterialId) {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(slabMaterialId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        String status = slabMaterialEntity.getSlabMaterialStatus();
        boolean isReceiving = status.equals(CommonCode.SLAB_MATERIAL_STATUS_RECEIVING.getCode()) || status.equals(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode());
        boolean isCompletion = status.equals(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode());

        return SlabMaterialNameResponse.builder()
                .slabMaterialReceiving(isReceiving)
                .slabMaterialCompletion(isCompletion)
                .slabMaterialId(slabMaterialEntity.getId())
                .slabMaterialName(slabMaterialEntity.getSlabMaterialName())
                .build();
    }

    @Override
    public SlabMaterialInfoResponse slabMaterialInfo(Long slabMaterialId) {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(slabMaterialId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));


        List<AlertLogEntity> alertLogEntityList = alertLogRepository.findAllBySlabMaterialAndHasDeleted(slabMaterialEntity, Boolean.FALSE);

        LocalDateTime completionDate = null;
        for (AlertLogEntity alertLogEntity : alertLogEntityList) {
            if (alertLogEntity.getAlertType().equals(CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode())) {
                completionDate = alertLogEntity.getCreatedDate();
                break; // alert_type이 2020인 경우를 찾았으므로 반복문 종료
            }
        }

        return SlabMaterialInfoResponse.builder()
                .slabMaterialName(slabMaterialEntity.getSlabMaterialName())
                .slabMaterialHorizontal(slabMaterialEntity.getSlabMaterialHorizontal())
                .slabMaterialVertical(slabMaterialEntity.getSlabMaterialVertical())
                .completionDate(completionDate)
                .build();
    }


    @Override
    public Long slabMaterialCreate(SlabMaterialCreateDto dto) {

        SlabEntity slabEntity = slabRepository.findByIdAndHasDeleted(dto.getSlabId(), Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브정보를 확인할 수 없습니다."));

        SlabMaterialEntity slabMaterial = slabMaterialRepository.save(
                SlabMaterialEntity.builder()
                        .slabMaterialName(dto.getSlabMaterialName())
                        .slabMaterialStatus(CommonCode.SLAB_MATERIAL_STATUS_DEFAULT.getCode())
                        .slab(slabEntity)
                        .slabMaterialProcess(dto.getSlabMaterialProcess())
                        .build());

        return slabMaterial.getId();

    }
    @Override
    public void slabMaterialSensorUpdate(Long slabMaterialId, SlabMaterialSensorDto dto) {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(slabMaterialId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        if(slabMaterialEntity.getSlabMaterialHorizontal() == null && slabMaterialEntity.getSlabMaterialVertical() == null) {
            slabMaterialEntity.sensorUpdate(dto.getSlabMaterialHorizontal(), dto.getSlabMaterialHorizontal());
        }
    }

    @Override
    public ResponseEntity<byte[]> slabMaterialQrExport(Long slabMaterialId) throws IOException, WriterException {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(slabMaterialId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String codeUrl = new String(slabMaterialEntity.getId().toString().getBytes("UTF-8"), "ISO-8859-1");

        //QR Code의 Width, Height 값
        BitMatrix bitMatrix = qrCodeWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 200, 200);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());
        }

    }

    @Override
    public void slabMaterialQrCodeSelfReceiving(Long slabMaterialId, LocalDateTime cellPhoneTime) {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(slabMaterialId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        boolean slabExists = alertLogRepository.existsBySlabMaterialAndAlertTypeAndHasDeleted(slabMaterialEntity, CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode(), Boolean.FALSE);

        if (slabExists) {
            throw new BadRequestException("해당 자재는 이미 입고되었습니다.");
        }

        //String slabFloorStr = String.valueOf(slabMaterialEntity.getSlab().getSlabFloor() + "층");

        //String slabMaterialProcessStr = String.valueOf(slabMaterialEntity.getSlabMaterialProcess()+ "번 자재 입고 완료되었습니다.");

        String slabFloorProcess = String.valueOf(slabMaterialEntity.getSlabMaterialName()+ " 자재 입고");

        AlertLogEntity savedAlertLog = alertLogRepository.save(
                AlertLogEntity.builder()
                        .alertContext(slabFloorProcess)
                        .alertType(CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode())
                        .slabMaterial(slabMaterialEntity)
                        .build());

        slabMaterialEntity.slabStatusUpdate(CommonCode.SLAB_MATERIAL_STATUS_RECEIVING.getCode());

        slabMaterialTimeUpdate(cellPhoneTime, savedAlertLog.getId());
    }

    @Override
    public void slabMaterialTimeUpdate(LocalDateTime cellPhoneTime , Long alertId) {

        AlertLogEntity alertLogEntity = alertLogRepository.findById(alertId)
                .orElseThrow(() -> new BadRequestException("슬라브 알림메세지를 확인할 수 없습니다."));

        alertLogEntity.dateUpdate(cellPhoneTime, cellPhoneTime);
    }

    @Override
    public void slabMaterialQrCodeSelfCompletion(QrSelfCompletionDto dto) {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(dto.getSlabMaterialId(), Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        if(slabMaterialEntity.getSlabMaterialStatus().equals(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode())) {
            throw new BadRequestException("이미 시공완료된 자재입니다.");
        }

        // 입고 정보 확인
        boolean slabExists = alertLogRepository.existsBySlabMaterialAndAlertTypeAndHasDeleted(slabMaterialEntity, CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode(), Boolean.FALSE);

        String alertContext;
        String alertCode;
        //String slabFloorProcess = String.valueOf(slabMaterialEntity.getSlab().getSlabFloor()) + "층" + String.valueOf(slabMaterialEntity.getSlabMaterialProcess()) + "번 자재 ";
        String slabFloorProcess = slabMaterialEntity.getSlabMaterialName();


        if (slabExists) {
            // 입고된 경우
            alertContext = slabFloorProcess + "시공 완료";
            alertCode = CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode();

        } else {
            // 입고되지 않은 경우
            alertContext = slabFloorProcess + "입고 완료";
            alertCode = CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode();
        }

        AlertLogEntity alertLogEntity = AlertLogEntity.builder()
                .alertContext(alertContext)
                .alertType(alertCode)
                .slabMaterial(slabMaterialEntity)
                .build();

        alertLogRepository.save(alertLogEntity);

        slabMaterialTimeUpdate(dto.getCellPhoneTime(), alertLogEntity.getId());

        // 입고되지 않은 경우 추가 메시지 저장
        if (!slabExists) {
            AlertLogEntity alertLogSubEntity = AlertLogEntity.builder()
                    .alertContext(slabFloorProcess + "시공 완료")
                    .alertType(CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode())
                    .slabMaterial(slabMaterialEntity)
                    .build();

            alertLogRepository.save(alertLogSubEntity);

            slabMaterialTimeUpdate(dto.getCellPhoneTime(), alertLogEntity.getId());
        }

        if(slabMaterialEntity.getSlabMaterialHorizontal() == null && slabMaterialEntity.getSlabMaterialVertical() == null) {
            slabMaterialEntity.sensorUpdate(dto.getSlabMaterialHorizontal(), dto.getSlabMaterialHorizontal());
        }

        slabMaterialEntity.slabStatusUpdate(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode());

    }

    @Override
    public void slabMaterialQrDownload(HttpServletResponse response, Long slabMaterialId) throws IOException, WriterException {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(slabMaterialId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String codeUrl = new String(slabMaterialEntity.getId().toString().getBytes("UTF-8"), "ISO-8859-1");

        BitMatrix bitMatrix = qrCodeWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 200, 200);

        // 이미지 생성
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // HttpServletResponse를 이용하여 파일 다운로드
        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; filename=QRCode.png");
        response.setHeader("Content-Transfer-Encoding", "binary");

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(qrCodeImage, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void slabMaterialQrCodeReceiving(Long slabMaterialId) {

        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(slabMaterialId, Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        boolean slabExists = alertLogRepository.existsBySlabMaterialAndAlertTypeAndHasDeleted(slabMaterialEntity, CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode(), Boolean.FALSE);

        if (slabExists) {
            throw new BadRequestException("해당 자재는 이미 입고되었습니다.");
        }

        //String slabFloorStr = String.valueOf(slabMaterialEntity.getSlab().getSlabFloor() + "층");

        //String slabMaterialProcessStr = String.valueOf(slabMaterialEntity.getSlabMaterialProcess()+ "번 자재 입고 완료되었습니다.");

        String slabFloorProcess = String.valueOf(slabMaterialEntity.getSlabMaterialName()+ " 자재 입고 완료");

        alertLogRepository.save(
                AlertLogEntity.builder()
                        .alertContext(slabFloorProcess)
                        .alertType(CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode())
                        .slabMaterial(slabMaterialEntity)
                        .build());

        slabMaterialEntity.slabStatusUpdate(CommonCode.SLAB_MATERIAL_STATUS_RECEIVING.getCode());
    }

    @Override
    public void slabMaterialQrCodeCompletion(QrCompletionDto dto) {
        SlabMaterialEntity slabMaterialEntity = slabMaterialRepository.findByIdAndHasDeleted(dto.getSlabMaterialId(), Boolean.FALSE)
                .orElseThrow(() -> new BadRequestException("슬라브 자재정보를 확인할 수 없습니다."));

        if(slabMaterialEntity.getSlabMaterialStatus().equals(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode())) {
            throw new BadRequestException("이미 시공완료된 자재입니다.");
        }

        // 입고 정보 확인
        boolean slabExists = alertLogRepository.existsBySlabMaterialAndAlertTypeAndHasDeleted(slabMaterialEntity, CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode(), Boolean.FALSE);

        String alertContext;
        String alertCode;
        //String slabFloorProcess = (slabMaterialEntity.getSlab().getSlabFloor()) + "층" + (slabMaterialEntity.getSlabMaterialProcess()) + "번 자재 ";

        String slabFloorProcess = slabMaterialEntity.getSlabMaterialName();

        if (slabExists) {
            // 입고된 경우
            alertContext = slabFloorProcess + "시공 완료";
            alertCode = CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode();

        } else {
            // 입고되지 않은 경우
            alertContext = slabFloorProcess + "입고 완료";
            alertCode = CommonCode.ALERT_LOG_TYPE_RECEIVING.getCode();
        }

        AlertLogEntity alertLogEntity = AlertLogEntity.builder()
                .alertContext(alertContext)
                .alertType(alertCode)
                .slabMaterial(slabMaterialEntity)
                .build();

        alertLogRepository.save(alertLogEntity);

        // 입고되지 않은 경우 추가 메시지 저장
        if (!slabExists) {
            AlertLogEntity alertLogSubEntity = AlertLogEntity.builder()
                    .alertContext(slabFloorProcess + "시공 완료")
                    .alertType(CommonCode.ALERT_LOG_TYPE_COMPLETION.getCode())
                    .slabMaterial(slabMaterialEntity)
                    .build();

            alertLogRepository.save(alertLogSubEntity);
        }

        if(slabMaterialEntity.getSlabMaterialHorizontal() == null && slabMaterialEntity.getSlabMaterialVertical() == null) {
            slabMaterialEntity.sensorUpdate(dto.getSlabMaterialHorizontal(), dto.getSlabMaterialHorizontal());
        }

        slabMaterialEntity.slabStatusUpdate(CommonCode.SLAB_MATERIAL_STATUS_COMPLETION.getCode());
    }

}
