package simplilearn.sportyshoes.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectForAll {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Around(value = "execution(* simplilearn.sportyshoes.controller.*.*(..))")
	public Object aroundWebController(ProceedingJoinPoint proJoinPoint) throws Throwable {
		logger.info("Enter  ["+ proJoinPoint.getTarget().getClass().getName()  + " =>  " + proJoinPoint.getSignature().getName()+ "]");
		logger.info("Arguments  : " + Arrays.toString(proJoinPoint.getArgs()));
		Object proceed = proJoinPoint.proceed();
		logger.info("Exit  ["+ proJoinPoint.getTarget().getClass().getName()  + " =>  " + proJoinPoint.getSignature().getName()+ "]");
		return proceed;
	}
	
	@Around(value = "execution(* simplilearn.sportyshoes.service.*.*(..))")
	public Object aroundServiceController(ProceedingJoinPoint proJoinPoint) throws Throwable {
		logger.info("Enter  ["+ proJoinPoint.getTarget().getClass().getName()  + " =>  " + proJoinPoint.getSignature().getName()+ "]");
		logger.info("Arguments  : " + Arrays.toString(proJoinPoint.getArgs()));
		Object proceed = proJoinPoint.proceed();
		logger.info("Exit  ["+ proJoinPoint.getTarget().getClass().getName()  + " =>  " + proJoinPoint.getSignature().getName()+ "]");
		return proceed;
	}

}
