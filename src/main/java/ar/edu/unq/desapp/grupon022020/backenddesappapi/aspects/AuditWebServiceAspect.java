package ar.edu.unq.desapp.grupon022020.backenddesappapi.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Aspect
@Component
public class AuditWebServiceAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String logMessage = "Method " + joinPoint.getSignature().getName() +
                " executed in " + executionTime + "ms";
        System.out.println(logMessage);
        return proceed;
    }

    @Around("@annotation(LogExecutionArguments)")
    public Object logExecutionArguments(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        String arguments = (String) args.stream().reduce("", (previous, arg) -> previous + " " + arg.toString());
        String logMessage = "Method " + joinPoint.getSignature().getName() +
                " called with arguments " + "[" + arguments + "]";
        System.out.println(logMessage);
        Object proceed = joinPoint.proceed();
        return proceed;
    }
}
