package kr.movements.vertical.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("codeCache")
@RequiredArgsConstructor
public class CodeCacheUtil {
//	private final CodeRepository codeRepository;
//    private final PipeModelRepository pipeModelRepository;
//
//	@PostConstruct
//	public void findAll() {
//		try {
//			CodeCache.init(codeRepository.findAll());
//		} catch (Exception e) {
//			throw new BootException(e);
//		}
//	}
//
//    public void materialCodeCheck(String buriedCode, String materialCode, String usageCode) {
//        if (CodeCache.getValue(materialCode) == null) {
//            throw new BadRequestException("입력한 재질 코드가 존재하지 않습니다. code: " + materialCode);
//        }
//
//        if (CodeCache.getValue(buriedCode) == null) {
//            throw new BadRequestException("입력한 매설물 코드가 존재하지 않습니다. code: " + buriedCode);
//        }
//
//        if (CodeCache.getValue(usageCode) == null) {
//            throw new BadRequestException("입력한 용도 코드가 존재하지 않습니다. code: " + usageCode);
//        }
//    }
//
//    public void materialCodeCheck(String buriedCode, String materialCode, String usageCode, Long pipeModelId) {
//        // 시설물 > 매설물종류(상/하수) > 재질..
//        // 시설물 > 매설물종류(상/하수) > 용도..
//
//        if (CodeCache.getValue(materialCode) == null) {
//            throw new BadRequestException("입력한 재질 코드가 존재하지 않습니다. code: " + materialCode);
//        }
//
//        if (CodeCache.getValue(buriedCode) == null) {
//            throw new BadRequestException("입력한 매설물 코드가 존재하지 않습니다. code: " + buriedCode);
//        }
//
//        if (CodeCache.getValue(usageCode) == null) {
//            throw new BadRequestException("입력한 용도 코드가 존재하지 않습니다. code: " + usageCode);
//        }
//
//        if(pipeModelId != null){
//            if(!pipeModelRepository.existsById(pipeModelId)){
//                throw new BadRequestException("해당 id의 파이프 모델 정보가 존재하지 않습니다. id: " + pipeModelId);
//            }
//        }
//    }

}
