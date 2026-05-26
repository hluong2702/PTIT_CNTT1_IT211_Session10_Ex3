package org.example.ptit_cntt1_it211_session10_ex3.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InventoryPerformanceAspect {
    private static final Logger log = LoggerFactory.getLogger(InventoryPerformanceAspect.class);

    @Around("execution(* org.example.ptit_cntt1_it211_session10_ex3.service.InventoryService.*(..))")
    public Object aroundInventoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("Hàm {} chạy mất {} ms.", methodName, elapsed);
            if (elapsed > 500) {
                log.warn("[Performance Alert] Hàm {} quá chậm ({} ms).", methodName, elapsed);
            }
            return result;
        } catch (IllegalArgumentException ex) {
            log.warn("Lỗi nghiệp vụ tại hàm {}: {}", methodName, ex.getMessage());
            throw ex;
        } catch (DataAccessException jakartaOrSpringDataEx) {
            log.error("Lỗi Database tại hàm {}: {}", methodName, jakartaOrSpringDataEx.getMessage(), jakartaOrSpringDataEx);
            throw jakartaOrSpringDataEx;
        } catch (jakarta.persistence.LockTimeoutException lockTimeoutException) {
            log.error("LockTimeout tại hàm {}: {}", methodName, lockTimeoutException.getMessage(), lockTimeoutException);
            throw lockTimeoutException;
        } catch (Exception ex) {
            log.error("Lỗi hệ thống tại hàm {}: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }
    }
}
