package ar.edu.unq.desapp.grupon022020.backenddesappapi.aspects.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;


@Aspect
@Component
public class LogEndpointAspect {

    Logger logger = LoggerFactory.getLogger(LogEndpointAspect.class);

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        getExecutionTime(joinPoint, "started");
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        getExecutionTime(joinPoint, "finished");
        String logMessage = "Method " + joinPoint.getSignature().getName() +
                " executed in " + executionTime + "ms";
        logger.info(logMessage);
        return proceed;
    }

    @Around("@annotation(LogExecutionArguments)")
    public Object logExecutionArguments(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        String arguments = (String) args.stream().reduce("", (previous, arg) -> previous + " " + arg.toString());
        String logMessage = "Method " + joinPoint.getSignature().getName() +
                " called with arguments " + "[" + arguments + "]";
        logger.info(logMessage);
        Object proceed = joinPoint.proceed();
        return proceed;
    }

    private void getExecutionTime(ProceedingJoinPoint joinPoint, String operation) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);
        logger.info("Method " + joinPoint.getSignature().getName() + " " + operation + " at " +
                hour + ":" + minute + ":" + second + ":" + millis + " on " +
                day + "-" + month + "-" + year);
    }
}
