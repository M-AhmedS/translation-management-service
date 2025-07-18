package com.example.tms;

import com.example.tms.dto.TranslationDto;
import com.example.tms.entity.Translation;
import com.example.tms.repository.TranslationRepository;
import com.example.tms.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationServiceTests {

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private TranslationService translationService;

    @Test
    void testCreateTranslation() {
        TranslationDto dto = new TranslationDto(null, "en", "key1", "content1", Set.of("web"));
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setLocale("en");
        translation.setKey("key1");
        translation.setContent("content1");
        translation.setTags(Set.of("web"));

        when(translationRepository.save(any(Translation.class))).thenReturn(translation);

        TranslationDto result = translationService.createTranslation(dto);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLocale()).isEqualTo("en");
    }
}
