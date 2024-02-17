package ru.kuzds.scheduling;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final TaskScheduler taskScheduler;
    private final Map<Integer, Context> contextMap = new ConcurrentHashMap<>();
    private final Map<Integer, ScheduledFuture<?>> scheduleMap = new ConcurrentHashMap<>();

    @GetMapping("/start/{id}")
    public String start(@PathVariable("id") int id) {
        if (scheduleMap.get(id) != null) {
            return "id is busy";
        }
        log.info("Start task with id={}", id);

        Context context = new Context();
        context.setCount(5);
        contextMap.put(id, context);

        ScheduledFuture<?> schedule = taskScheduler.scheduleAtFixedRate(
                () -> {
                    log.info("id={} | random={}", id, UUID.randomUUID());
                    if (contextMap.get(id).decrement() <= 0) {
                        stop(id);
                    }
                },
                Instant.now().plusMillis(5000),
                Duration.ofMillis(2000)
        );
        scheduleMap.put(id, schedule);

        return "OK";
    }

    @GetMapping("/stop/{id}")
    public void stop(@PathVariable("id") int id) {
        log.info("Stop task with id={}", id);
        ScheduledFuture<?> scheduledFuture = scheduleMap.remove(id);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        } else {
            log.warn("Unable cancel schedule with id={}", id);
        }
    }

    @Data
    public static class Context {
        private volatile int count;
        public synchronized int decrement() {
            return --count;
        }
    }

}
