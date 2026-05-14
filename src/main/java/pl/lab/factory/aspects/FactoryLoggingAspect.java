package pl.lab.factory.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import pl.lab.factory.SystemLogger;

// Tydzień 11 - Programowanie aspektowe
// Aspekt 1 - Logowanie wywołań metod execute() we wszystkich procesach fabryki.
// Rejestruje wejście i wyjście każdego procesu bez modyfikacji kodu procesów
@Aspect
public class FactoryLoggingAspect {

    private static final String ASPECT_CONTEXT = "LoggingAspect";

    // Rada przed (Before)
    // Przechwytuje każde wywołanie execute() w pakiecie processes
    @Before("execution(* pl.lab.factory.processes.*.execute(..))")
    public void logProcessStart(JoinPoint joinPoint) {
        String processName = resolveProcessName(joinPoint);
        SystemLogger.INSTANCE.logInfo(ASPECT_CONTEXT,
            ">>> Process started: " + processName);
    }

    // Rada po (After)
    // Rejestruje zakończenie procesu (wykonuje się zawsze, nawet przy wyjątku)
    @After("execution(* pl.lab.factory.processes.*.execute(..))")
    public void logProcessEnd(JoinPoint joinPoint) {
        String processName = resolveProcessName(joinPoint);
        SystemLogger.INSTANCE.logInfo(ASPECT_CONTEXT,
            "<<< Process finished: " + processName);
    }

    private String resolveProcessName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getSimpleName();
    }
}
// Koniec, Tydzień 11
// Aspekt 1
