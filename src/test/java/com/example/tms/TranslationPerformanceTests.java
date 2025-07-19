package com.example.tms;

import com.example.tms.dto.TranslationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TranslationPerformanceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTranslationResponseTimeUnder200ms() throws Exception {
        int totalRequests = 100;
        long totalTime = 0;
        long maxResponseTime = 0;

        for (int i = 0; i < totalRequests; i++) {
            TranslationDto dto = new TranslationDto(null, "en", "perf_key_" + i,
                    "Performance Test Content " + i, Set.of("web", "perf"));
            String json = objectMapper.writeValueAsString(dto);

            long start = System.nanoTime();
            MvcResult result = mockMvc.perform(post("/api/v1/translations").contentType(MediaType.APPLICATION_JSON)
                            .content(json)).andReturn();
            long duration = (System.nanoTime() - start) / 1_000_000;
            totalTime += duration;
            maxResponseTime = Math.max(maxResponseTime, duration);

            int status = result.getResponse().getStatus();
            assertThat(status).isEqualTo(200);
            assertThat(duration)
                    .withFailMessage("Request %d took too long: %dms", i, duration)
                    .isLessThan(200);
        }

        double avg = totalTime / (double) totalRequests;
        System.out.printf("Avg: %.2fms | Max: %dms for %d requests%n", avg, maxResponseTime, totalRequests);

        assertThat(avg)
                .withFailMessage("Average response time too high: %.2fms", avg)
                .isLessThan(200);
    }
}
