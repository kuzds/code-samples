package ru.kuzds.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> map = new ConcurrentHashMap<>();

    @Value("${ru.kuzds.task-delay}")
    private Long defaultDelay;

    public void scheduleAtFixedRate(String id, Runnable task, Instant startTime, Duration period) {
        if (map.get(id) != null) {
            throw new IllegalArgumentException(String.format("id=%s already exists", id));
        }

        ScheduledFuture<?> schedule = taskScheduler.scheduleAtFixedRate(task, startTime, period);
        map.put(id, schedule);
    }

    public void schedule(Runnable task) {
        schedule(task, defaultDelay);
    }

    public void schedule(Runnable task, Long delay) {
        taskScheduler.schedule(task, Instant.now().plusMillis(delay));
    }

    public void cancel(String id) {
        ScheduledFuture<?> scheduledFuture = map.remove(id);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        } else {
            log.warn("Unable cancel schedule with id={}", id);
        }
    }
}
