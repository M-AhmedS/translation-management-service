package com.example.tms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.tms.dto.TranslationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TranslationFeatureTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTranslationEndpoint() throws Exception {
        TranslationDto dto = new TranslationDto(null, "en", "test_key", "Test Content", Set.of("web"));
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/translations").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }
}
