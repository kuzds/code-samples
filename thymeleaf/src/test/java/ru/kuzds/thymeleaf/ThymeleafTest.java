package ru.kuzds.thymeleaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@SpringBootTest(properties = {"spring.main.lazy-initialization=true"})
public class ThymeleafTest {

    @Autowired
    private SpringTemplateEngine engine;

    @Test
    void simpleTest() {
        Context ctx = new Context();
        ctx.setVariable("country", "RUSSIA");
        ctx.setVariable("number", "123");
        String content = engine.process("test", ctx);

        Assertions.assertEquals("<test number=\"123\">RUSSIA</test>", content);
    }
}
