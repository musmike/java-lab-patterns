package pl.lab.factory.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import pl.lab.factory.SystemLogger;

// Tydzień 11 - Programowanie aspektowe
// Aspekt 3 - Pomiar czasu wykonania metod demonstrate*() w FunctionalProgrammingProcess.
// Używa rady Around do obejmowania wywołania i mierzenia jego czasu bez zmiany kodu docelowego.
@Aspect
public class PerformanceMonitoringAspect {

    private static final String ASPECT_CONTEXT = "PerformanceAspect";
    private static final long MILLISECONDS_PER_SECOND = 1_000L;

    // Rada Around
    // Przechwytuje metody demonstracyjne procesu funkcyjnego i mierzy ich czas
    @Around("execution(* pl.lab.factory.processes.FunctionalProgrammingProcess.demonstrate*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = resolveMethodName(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();

        try {
            return proceedingJoinPoint.proceed();
        } finally {
            long elapsed = System.currentTimeMillis() - startTime;
            logElapsedTime(methodName, elapsed);
        }
    }

    private void logElapsedTime(String methodName, long elapsedMillis) {
        String duration = elapsedMillis >= MILLISECONDS_PER_SECOND
            ? (elapsedMillis / MILLISECONDS_PER_SECOND) + "s"
            : elapsedMillis + "ms";

        SystemLogger.INSTANCE.logInfo(ASPECT_CONTEXT,
            "Method '" + methodName + "' executed in: " + duration);
    }

    private String resolveMethodName(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getSignature().getName();
    }
}
// Koniec, Tydzień 11
// Aspekt 3
