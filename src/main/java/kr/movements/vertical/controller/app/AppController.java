package kr.movements.vertical.controller.app;

import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.service.AreaService;
import kr.movements.vertical.service.SlabMaterialService;
import kr.movements.vertical.service.VerticalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "앱")
public class AppController {

    private final SlabMaterialService slabMaterialService;
    private final AreaService areaService;
    private final VerticalService verticalService;

    @ApiOperation(value = "App - QrCode 찍었을 때 id, 슬라브 자재 명칭 조회")
    @GetMapping(value = "/app/slab-material/qr-code/{slabMaterialId}")
    public Payload<SlabMaterialNameResponse> qrSlabMaterialNameAndId (HttpServletRequest request, HttpServletResponse response,
                                                       @PathVariable Long slabMaterialId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), slabMaterialService.slabMaterialNameAndId(slabMaterialId) ,response);
    }

    /*
    @ApiOperation(value = "화면설계서 10p - 슬라브 자재 센서 등록")
    @PutMapping(value = "/app/slab-material/{slabMaterialId}")
    public Payload<String> slabMaterialSensorUpdate(@PathVariable Long slabMaterialId,
                                        @RequestBody SlabMaterialSensorDto dto,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {

        slabMaterialService.slabMaterialSensorUpdate(slabMaterialId, dto);

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

     */

    @ApiOperation(value = "화면설계서 7p - 수직구 슬라브 자재 Qr 인증 입고완료")
    @PostMapping(value = "/app/slab-material/qr-code/receiving")
    public Payload<String> slabMaterialReceiving(HttpServletRequest request, HttpServletResponse response, @RequestBody QrReceivingDto dto) {

        slabMaterialService.slabMaterialQrCodeReceiving(dto.getSlabMaterialId());

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

    @ApiOperation(value = "로컬 저장 후에 수직구 슬라브 자재 Qr 인증 입고완료")
    @PostMapping(value = "/app/slab-material/qr-code/self-receiving")
    public Payload<String> slabMaterialSelfReceiving(HttpServletRequest request, HttpServletResponse response, @RequestBody QrSelfReceivingDto dto) {

        slabMaterialService.slabMaterialQrCodeSelfReceiving(dto.getSlabMaterialId(), dto.getCellPhoneTime());

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

    @ApiOperation(value = "화면설계서 7p - 수직구 슬라브 자재 Qr 인증 시공완료 및 자재 센서 등록")
    @PostMapping(value = "/app/slab-material/qr-code/completion")
    public Payload<String> slabMaterialCompletion(HttpServletRequest request, HttpServletResponse response, @RequestBody QrCompletionDto dto) {

        slabMaterialService.slabMaterialQrCodeCompletion(dto);

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

    @ApiOperation(value = "로컬 저장 후에 수직구 슬라브 자재 Qr 인증 시공완료 및 자재 센서 등록")
    @PostMapping(value = "/app/slab-material/qr-code/self-completion")
    public Payload<String> slabMaterialSelfCompletion(HttpServletRequest request, HttpServletResponse response, @RequestBody QrSelfCompletionDto dto) {

        slabMaterialService.slabMaterialQrCodeSelfCompletion(dto);

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

    @ApiOperation(value = "4p 화면설계 - 현장 목록")
    @GetMapping(value = "/app/areas")
    public Payload<List<AreasResponse>> memberAreaList(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestParam Long memberId,
                                                       @RequestParam(required = false) String areaName) {
        if (areaName == null) {
            areaName = ""; // null일 경우 빈 문자열로 처리
        }
        return new Payload<>(HttpStatus.OK, request.getServletPath(), areaService.memberAllAreaList(memberId, areaName) ,response);
    }

    @ApiOperation(value = "6p 화면설계 - 수직구 목록")
    @GetMapping(value = "/app/verticals")
    public Payload<List<AppVerticalHoleInfoDto>> appVerticalList (HttpServletRequest request, HttpServletResponse response,
                                                       @RequestParam Long areaId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), verticalService.appVerticalList(areaId) ,response);
    }

    @ApiOperation(value = "6p 화면설계 - 수직구 목록 상세내용")
    @GetMapping(value = "/app/vertical-detail/{verticalId}")
    public Payload<VerticalHoleSlabInfoDto> appVerticalDetail (HttpServletRequest request, HttpServletResponse response,
                                                                  @PathVariable Long verticalId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), verticalService.appVerticalDetail(verticalId) ,response);
    }

}
