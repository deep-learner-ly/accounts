package com.example.bookkeeping.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Aspect
@Component
public class ApiLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ApiLogAspect.class);

    @Pointcut("execution(* com.example.bookkeeping.controller..*(..))")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        logger.info("Request: {}.{} with args: {}", className, methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            logger.info("Response: {}.{} completed in {} ms", className, methodName, executionTime);
            return result;
        } catch (Throwable e) {
            long executionTime = System.currentTimeMillis() - start;
            logger.error("Exception in {}.{} after {} ms: {}", className, methodName, executionTime, e.getMessage(), e);

            // If the controller method is expected to return ResponseEntity, we can return a standardized error response
            // However, most of our controllers already have try-catch blocks.
            // This aspect acts as a safety net for unhandled exceptions.
            if (joinPoint.getSignature().toString().contains("ResponseEntity")) {
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", e.getMessage() != null ? e.getMessage() : "Internal Server Error"));
            }
            throw e;
        }
    }
}
