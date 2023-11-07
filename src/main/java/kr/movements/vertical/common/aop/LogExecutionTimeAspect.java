package kr.movements.vertical.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @file name : ExecutionTimerAspect.java
 * @project : spring-boot-init
 * @date : Nov 10, 2022
 * @author : ckr
 * @history:
 * @program comment : 메서드 실행시간 측정
 */
@Component
@Aspect
public class LogExecutionTimeAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 조인포인트를 어노테이션으로 설정
    @Pointcut("@annotation(kr.movements.vertical.common.aop.LogExecutionTime)")
    private void timer(){};

	/**
	 * @deprecated : 사용시 return 결과가 null 이됨.
	 * @method name : assumeExecutionTime
	 * @date : Nov 11, 2022
	 * @author : ckr
	 * @history :
	 * @method comment :
	 * @param joinPoint
	 * @return
	 * @throws Throwable :
	 */
    @Around("timer()")
    public void assumeExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        joinPoint.proceed(); // 조인포인트의 메서드 실행
        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        logger.debug("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);
    }

}
