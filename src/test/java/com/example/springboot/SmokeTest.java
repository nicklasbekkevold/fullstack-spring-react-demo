package com.example.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springboot.controller.ContextController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

    @Autowired
    private ContextController contextController;

    @Test
    void contextLoads() throws Exception {
        assertThat(contextController).isNotNull();
    }
}
