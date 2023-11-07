package kr.movements.vertical.service;

import com.google.zxing.WriterException;
import kr.movements.vertical.dto.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;


public interface SlabMaterialService {

    void slabMaterialTimeUpdate(LocalDateTime cellPhoneTime , Long alertId);

    void slabMaterialQrCodeSelfReceiving(Long slabMaterialId, LocalDateTime cellPhoneTime);

    void slabMaterialQrCodeSelfCompletion(QrSelfCompletionDto qrSelfCompletionDto);

    SlabMaterialNameResponse slabMaterialNameAndId(Long slabMaterialId);

    SlabMaterialInfoResponse slabMaterialInfo(Long slabMaterialId);

    Long slabMaterialCreate(SlabMaterialCreateDto dto);

    void slabMaterialSensorUpdate(Long slabMaterialId, SlabMaterialSensorDto dto);

    ResponseEntity<byte[]> slabMaterialQrExport(Long slabMaterialId) throws IOException, WriterException;

    void slabMaterialQrDownload(HttpServletResponse response, Long slabMaterialId) throws IOException, WriterException;

    void slabMaterialQrCodeReceiving(Long slabMaterialId);

    void slabMaterialQrCodeCompletion(QrCompletionDto dto);
}
