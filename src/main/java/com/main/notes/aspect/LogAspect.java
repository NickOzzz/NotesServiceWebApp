package com.main.notes.aspect;

import com.main.notes.dto.MessageDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private long currentTime;

    @Pointcut("execution(* com.main.notes.service.MessageService.*Message(..))")
    private void logMessagePointCut() {}

    @Before("logMessagePointCut()")
    public void logMessageOperationBefore(JoinPoint joinPoint)
    {
        currentTime = System.currentTimeMillis();

        Object messageId = joinPoint.getArgs()[0];
        Object methodName = joinPoint.getSignature().getName();

        if (messageId instanceof MessageDto)
        {
            System.out.println("Starting call to "
                    + methodName
                    + "; Current time: "
                    + currentTime
                    + " millis");
        }
        else
        {
            System.out.println("Starting call to "
                    + methodName
                    + " with message id "
                    + messageId
                    + "; Current time: "
                    + currentTime
                    + " millis");
        }
    }

    @After("logMessagePointCut()")
    public void logMessageOperationAfter(JoinPoint joinPoint)
    {
        Object totalTime = System.currentTimeMillis() - currentTime;
        Object methodName = joinPoint.getSignature().getName();
        System.out.println("Finishing call to " + methodName + "; Execution time: " + totalTime + " millis");
    }
}
