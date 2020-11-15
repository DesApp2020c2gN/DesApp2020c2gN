package ar.edu.unq.desapp.grupon022020.backenddesappapi.aspects.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Iterator;
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
        logger.info("Method " + joinPoint.getSignature().getName() + " called with arguments:");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        List<Parameter> parameters = Arrays.asList(method.getParameters());
        List<Object> arguments = Arrays.asList(joinPoint.getArgs());
        logArguments(parameters, arguments);
        Object proceed = joinPoint.proceed();
        return proceed;
    }

    private void logArguments(List<Parameter> parameters, List<Object> arguments) {
        Iterator it1 = parameters.iterator();
        Iterator it2 = arguments.iterator();
        while(it1.hasNext() && it2.hasNext()) {
            Parameter parameter = (Parameter) it1.next();
            Object argument = it2.next();
            logger.info("TYPE: " + parameter.getType().getSimpleName() +
                    " | NAME: " + parameter.getName() +
                    " | VALUE:  " + argument.toString());
        }
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
