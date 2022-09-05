package net.koreate.greatescape.common.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// @Component
@Aspect
@Slf4j
public class ServiceAdvice {

	@Around("execution(* net.koreate.greatescape.*.service.*.*(..))")
	public Object checkService(ProceedingJoinPoint pjp) throws Throwable {
		// 전처리
		log.info("================= START =================");
		log.info("--- 실행 클래스 : " + pjp.getTarget());
		log.info("--- 실행 메소드 : " + pjp.getSignature().getName());
		log.info("--- 입력 값 : " + Arrays.toString(pjp.getArgs()));
		Object o = pjp.proceed(); // 실제 메소드 실행부분
		// 후처리
		log.info("--- 반환 값  :" + o);
		log.info("================== END ==================");
		return o;
	}

}
