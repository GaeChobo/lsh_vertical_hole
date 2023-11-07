package kr.movements.vertical.controller.construction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.entity.FileEntity;
import kr.movements.vertical.repo.FileRepository;
import kr.movements.vertical.repo.VerticalHoleRepository;
import kr.movements.vertical.service.AreaService;
import kr.movements.vertical.service.FileService;
import kr.movements.vertical.service.SlabMaterialService;
import kr.movements.vertical.service.VerticalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "웹")
public class ConstructionController {

    private final AreaService areaService;
    private final VerticalService verticalService;
    private final SlabMaterialService slabMaterialService;
    private final FileRepository fileRepository;
    private final FileService fileService;

    @ApiOperation(value = "수직구 도면 추출 후 .dxf 변환 다운로드")
    @PostMapping(value = "/web/design/export/{verticalId}")
    public ResponseEntity<byte[]> convertDxf(HttpServletRequest request, HttpServletResponse response,
                                             @PathVariable Long verticalId,
                                             @RequestPart("file") MultipartFile multipartFile,
                                             @RequestPart String floorName) {
        return fileService.processFiles(verticalId, multipartFile, floorName);
    }

    @ApiOperation(value = "수직구 도면 파일 다운로드")
    @GetMapping(value = "/web/design/{verticalId}")
    public ResponseEntity<byte[]> downloadImage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long verticalId) {
        try {

            Long fileId = verticalService.getDrawingFileId(verticalId);

            FileEntity file = fileRepository.findByIdAndHasDeleted(fileId, false)
                    .orElseThrow(() -> new BadRequestException("해당 id의 파일이 존재하지 않습니다."));

            byte[] fileData = fileService.getImage(fileId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename(file.getOriginalName()).build());

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (IOException e) {
            // 파일 다운로드 실패 처리
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "재플린 - 설계 수직구 도면 여부, 라이더 여부 리스트")
    @GetMapping(value = "/web/design/area/{areaId}")
    public Payload<List<ConstructionVerticalResponse>> verticalFileExistList (HttpServletRequest request, HttpServletResponse response,
                                               @PathVariable Long areaId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), areaService.verticalFileExistList(areaId) ,response);
    }

    @ApiOperation(value = "12p 화면설계 - 현장 목록")
    @GetMapping(value = "/web/areas")
    public Payload<List<AreasResponse>> memberAreaList(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestParam Long memberId,
                                                       @RequestParam(required = false) String areaName) {
        if (areaName == null) {
            areaName = ""; // null일 경우 빈 문자열로 처리
        }
        return new Payload<>(HttpStatus.OK, request.getServletPath(), areaService.memberAllAreaList(memberId, areaName) ,response);
    }



    @ApiOperation(value = "12p 화면설계서 - 현장 시공 정보")
    @GetMapping(value = "/web/area/{areaId}")
    public Payload<AreaInfoResponse> areaInfo (HttpServletRequest request, HttpServletResponse response,
                                               @PathVariable Long areaId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), areaService.areaInfo(areaId) ,response);
    }


    @ApiOperation(value = "13p 화면설계서 - 상세 현장 수직구 현황")
    @GetMapping(value = "/web/vertical/{verticalId}")
    public Payload<VerticalDetailResponse> verticalPercentage (HttpServletRequest request, HttpServletResponse response,
                                                               @PathVariable Long verticalId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), verticalService.verticalPercentage(verticalId) ,response);
    }

    @ApiOperation(value = "12p 화면설계서 - 현장 수직구 현황 리스트")
    @GetMapping(value = "/web/vertical-situation/{areaId}")
    public Payload<List<VerticalHoleInfoResponse>> verticalInfoList (HttpServletRequest request, HttpServletResponse response,
                                                               @PathVariable Long areaId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), verticalService.verticalInfoList(areaId) ,response);
    }

    @ApiOperation(value = "12p 화면설계서 - 현장 전체 시공현황")
    @GetMapping(value = "/web/area-completion/{areaId}")
    public Payload<AreaSlabMaterialResponse> areaInfoCompletion (HttpServletRequest request, HttpServletResponse response,
                                               @PathVariable Long areaId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), areaService.areaCompletionSituation(areaId) ,response);
    }

    @ApiOperation(value = "13p 화면설계서 - 수직구 시공일정 수정")
    @PutMapping(value = "/web/vertical/{verticalId}")
    public Payload<String> verticalScheduleUpdate(@PathVariable Long verticalId,
                                                    @RequestBody verticalScheduleDto dto,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {

        verticalService.verticalScheduleUpdate(verticalId, dto.getStartDate(), dto.getEndDate());

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

    @ApiOperation(value = "13p 화면설계서 - 수직구 알림 리스트")
    @GetMapping(value = "/web/vertical-alert/{verticalId}")
    public Payload<List<AlertLogResponse>> verticalAlertList(@PathVariable Long verticalId,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {



        return new Payload<>(HttpStatus.OK, request.getServletPath(), verticalService.verticalAlertList(verticalId), response);
    }

    @ApiOperation(value = "15p 화면설계서 - 슬라브 자재 상세 조회")
    @GetMapping(value = "/web/slab-material/{slabMaterialId}")
    public Payload<SlabMaterialInfoResponse> slabMaterialInfo (HttpServletRequest request, HttpServletResponse response,
                                                                     @PathVariable Long slabMaterialId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), slabMaterialService.slabMaterialInfo(slabMaterialId) ,response);
    }

    @ApiOperation(value = "13p 화면설계서 - 타임라인 조회")
    @GetMapping(value = "/web/vertical-time/{verticalId}")
    public Payload<VerticalHoleTimeResponse> verticalHoleTimeLine (HttpServletRequest request, HttpServletResponse response,
                                                               @PathVariable Long verticalId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), verticalService.getVerticalHoleInfo(verticalId) ,response);
    }
}
