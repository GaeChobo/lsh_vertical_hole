package kr.movements.vertical.controller;

import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.*;
import kr.movements.vertical.entity.FileEntity;
import kr.movements.vertical.repo.FileRepository;
import kr.movements.vertical.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "임시 공통 컨트롤러")
public class CommonController {

    private final MemberAreaService memberAreaService;
    private final AreaService areaService;
    private final VerticalService verticalService;
    private final MemberService memberService;
    private final SlabService slabService;
    private final SlabMaterialService slabMaterialService;

    private final FileRepository fileRepository;

    private final FileService fileService;



    @ApiOperation(value = "슬라브 자재 상세 조회")
    @GetMapping(value = "/common/slab-material/{slabMaterialId}")
    public Payload<SlabMaterialInfoResponse> slabMaterialInfo (HttpServletRequest request, HttpServletResponse response,
                                                               @PathVariable Long slabMaterialId) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), slabMaterialService.slabMaterialInfo(slabMaterialId) ,response);
    }

    /*
    @ApiOperation(value = "현장 도면 파일 혹은 수직구 모델링파일 업로드")
    @PostMapping(value = "/common/file/upload")
    public Payload<String> areaOrVerticalFileUpload(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    @RequestPart(value = "file") MultipartFile multipartFile,
                                                    @RequestPart FileUploadDto dto) {

        Long fileId = null;
        FileEntity fileEntity = null;

        Long customId = areaService.areaDrawingFileUpload(dto.getAreaOrVerticalId());

        if (multipartFile != null) {
            try (InputStream inputStream = multipartFile.getInputStream()) {
                fileEntity = fileService.saveFile(inputStream, "vertical", multipartFile.getOriginalFilename(), customId);
                fileId = fileEntity.getId();
            } catch (IOException e) {
                e.printStackTrace();
                // 예외 처리
            }
        }

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }
    */

    @ApiOperation(value = "수직구 모델 파일 다운로드")
    @GetMapping(value = "/model/download/{verticalId}")
    public ResponseEntity<byte[]> downloadModel(HttpServletRequest request, HttpServletResponse response, @PathVariable Long verticalId) {
        try {

            Long fileId = verticalService.getModelFileId(verticalId);

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

    @ApiOperation(value = "수직구 모델링파일 업로드")
    @PostMapping(value = "/common/file/model-upload")
    public Payload<String> verticalModelFileUpload(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    @RequestPart(value = "file") MultipartFile multipartFile,
                                                    @RequestPart FileUploadDto dto) {

        Long fileId = null;
        FileEntity fileEntity = null;

        if (multipartFile != null) {
            fileEntity = fileService.saveFile(multipartFile, "vertical");
            fileId = fileEntity.getId();
        }

        verticalService.verticalModelFileUpdate(dto.getVerticalId(), fileId, dto.getRiderType());

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

    @ApiOperation(value = "수직구 도면파일 업로드")
    @PostMapping(value = "/common/file/drawing-upload")
    public Payload<String> verticalDrawingFileUpload(HttpServletRequest request,
                                              HttpServletResponse response,
                                              @RequestPart(value = "file") MultipartFile multipartFile,
                                              @RequestPart FileUploadDto dto) {

        Long fileId = null;
        FileEntity fileEntity = null;

        if (multipartFile != null) {
            fileEntity = fileService.saveFile(multipartFile, "vertical");
            fileId = fileEntity.getId();
        }

        verticalService.verticalDrawingFileUpdate(dto.getVerticalId(), fileId);

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }


    @ApiOperation(value = "유저 - 현장 맵핑")
    @PostMapping(value = "/common/member-area/create")
    public Payload<String> memberCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberAreaCreateDto dto) {

        memberAreaService.memberAreaCreate(dto);

        return new Payload<>(HttpStatus.OK, request.getServletPath(), response);
    }

    @ApiOperation(value = "현장 임시 등록")
    @PostMapping(value = "/common/area/create")
    public Payload<Long> memberCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AreaCreateDto dto) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), areaService.areaCreate(dto), response);
    }

    @ApiOperation(value = "수직구 임시 등록")
    @PostMapping(value = "/common/vertical/create")
    public Payload<Long> verticalCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody VerticalCreateDto dto) {

        return new Payload<>(HttpStatus.OK, request.getServletPath(), verticalService.verticalCreate(dto), response);
    }

    @ApiOperation(value = "유저 임시 등록")
    @PostMapping(value = "/common/member/create")
    public Payload<Long> memberCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberCreateDto dto) {


        return new Payload<>(HttpStatus.OK, request.getServletPath(), memberService.memberCreate(dto), response);
    }

    @ApiOperation(value = "수직구 슬라브 임시 등록")
    @PostMapping(value = "/common/slab/create")
    public Payload<Long> slabCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody SlabCreateDto dto) {


        return new Payload<>(HttpStatus.OK, request.getServletPath(), slabService.slabCreate(dto), response);
    }

    @ApiOperation(value = "수직구 슬라브 자재 임시 등록")
    @PostMapping(value = "/common/slab-material/create")
    public Payload<Long> slabMaterialCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody SlabMaterialCreateDto dto) {


        return new Payload<>(HttpStatus.OK, request.getServletPath(), slabMaterialService.slabMaterialCreate(dto), response);
    }

    @ApiOperation(value = "수직구 슬라브 자재 Qr 출력")
    @GetMapping(value = "/common/slab-material/qr-code/{slabMaterialId}")
    public ResponseEntity<byte[]> slabMaterialQrCodeExport(HttpServletRequest request, HttpServletResponse response, @PathVariable Long slabMaterialId) throws IOException, WriterException {


        return slabMaterialService.slabMaterialQrExport(slabMaterialId);
    }

    @ApiOperation(value = "수직구 슬라브 자재 Qr 이미지 다운로드")
    @GetMapping(value = "/common/slab-material/qr-code/download/{slabMaterialId}")
    public void slabMaterialQrCodeDownload(HttpServletRequest request, HttpServletResponse response, @PathVariable Long slabMaterialId) throws IOException, WriterException {


        slabMaterialService.slabMaterialQrDownload(response, slabMaterialId);
    }




}
