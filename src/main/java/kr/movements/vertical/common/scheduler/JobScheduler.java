package kr.movements.vertical.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * packageName : kr.mv.common.scheduler
 * fileName    : JobScheduler
 * author      : ckr
 * date        : 2022/12/21
 * description : 예약 날짜에 따라 주기적으로 지정된 업무를 수행한다.
 */
@Component
@RequiredArgsConstructor
public class JobScheduler {

    /*
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 매일 밤 12시 실행
    public void cleanupLocationInfoData() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thirtyDaysAgo = today.minusDays(30);
        locationInfoRepository.deleteByCreatedDateBeforeAndCreatedDateAfter(thirtyDaysAgo, today);
    }

     */

//    private final CodeService codeService;
//
//    /**
//     * methodName : dailyUpdateExpiredCodes
//     * author : ckr
//     * description : 만료 날짜에 해당하는 코드를 비활성화한다.
//     */
//    @Scheduled(cron = "1 0 0 * * *")
//    public void dailyUpdateExpiredCodes() {
//        codeService.updateExpiredCodes();
//    }
//
//    /**
//     * methodName : dailyUpdateActivationCodes
//     * author : ckr
//     * description : 활성화 시작 날짜에 해당하는 코드를 활성화한다.
//     */
//    @Scheduled(cron = "1 0 0 * * *")
//    public void dailyUpdateActivationCodes() {
//        codeService.updateActivationCodes();
//    }
}
