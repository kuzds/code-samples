package ru.kuzds.scheduling;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final SchedulingService schedulingService;
    private final Map<String, Context> contextMap = new ConcurrentHashMap<>();

    @GetMapping("/start/delayed/{id}")
    public String start(@PathVariable("id") int id) {
        log.info("Start delayed task with id={}", id);
        schedulingService.schedule(() -> log.info("[Delayed] id={} | random={}", id, UUID.randomUUID()));
        return "OK";
    }

    @GetMapping("/start/periodic/{id}")
    public String start(@PathVariable("id") String id) {
        log.info("Start periodic task with id={}", id);

        Context context = new Context();
        context.setCount(5);
        contextMap.put(id, context);

        schedulingService.scheduleAtFixedRate(id, () -> {
                    log.info("[Periodic] id={} | random={}", id, UUID.randomUUID());
                    if (contextMap.get(id).decrement() <= 0) {
                        stop(id);
                    }
                },
                Instant.now().plusMillis(3000),
                Duration.ofMillis(2000));
        return "OK";
    }

    @GetMapping("/stop/{id}")
    public void stop(@PathVariable("id") String id) {
        schedulingService.cancel(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    String handle(IllegalArgumentException e) {
        return e.getMessage();
    }


    @Data
    public static class Context {
        private volatile int count;

        public synchronized int decrement() {
            return --count;
        }
    }

}
