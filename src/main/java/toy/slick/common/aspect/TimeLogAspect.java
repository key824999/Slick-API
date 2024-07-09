package toy.slick.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import toy.slick.common.Const;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Aspect
@Component
public class TimeLogAspect {

    @Before("@annotation(toy.slick.common.annotation.TimeLog)")
    public void logStartTime(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature() + " start : " + ZonedDateTime.now(ZoneId.of(Const.ZoneId.SEOUL)));
    }

    @After("@annotation(toy.slick.common.annotation.TimeLog)")
    public void logEndTime(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature() + " end : " + ZonedDateTime.now(ZoneId.of(Const.ZoneId.SEOUL)));
    }

    @Around("@annotation(toy.slick.common.annotation.TimeLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info(joinPoint.getSignature() + " execution time : " + (end - start) + "ms");

        return proceed;
    }
}
