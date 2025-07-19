package com.example.tms;

import com.example.tms.dto.TranslationDto;
import com.example.tms.entity.Translation;
import com.example.tms.repository.TranslationRepository;
import com.example.tms.service.TranslationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationServiceTests {

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private TranslationService translationService;
    private Translation translation;

    @BeforeEach
    void setup() {
        translation = new Translation();
        translation.setId(1L);
        translation.setLocale("en");
        translation.setTranslationKey("greeting");
        translation.setContent("Hello");
        translation.setTags(Set.of("web"));
    }

    @Test
    void testUpdateTranslation_NotFound() {
        TranslationDto dto = new TranslationDto(99L, "en", "k", "v", Set.of());
        when(translationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> translationService.updateTranslation(99L, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Translation not found with id: 99");
    }

    @Test
    void testExportTranslations() {
        when(translationRepository.findByLocale("en")).thenReturn(List.of(translation));

        Map<String, String> result = translationService.exportTranslations("en");

        assertThat(result).containsEntry("greeting", "Hello");
    }

    @Test
    void testSearchTranslations() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Translation> page = new PageImpl<>(List.of(translation));
        when(translationRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Map<String, String> filters = Map.of("locale", "en");
        Page<TranslationDto> result = translationService.searchTranslations(filters, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getLocale()).isEqualTo("en");
    }
    @Test
    void testCreateTranslation() {
        TranslationDto dto = new TranslationDto(null, "en", "key1", "content1", Set.of("web"));
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setLocale("en");
        translation.setTranslationKey("key1");
        translation.setContent("content1");
        translation.setTags(Set.of("web"));

        when(translationRepository.save(any(Translation.class))).thenReturn(translation);

        TranslationDto result = translationService.createTranslation(dto);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLocale()).isEqualTo("en");
    }

    @Test
    void testUpdateTranslation() {
        Translation existing = new Translation();
        existing.setId(1L);
        existing.setLocale("en");
        existing.setTranslationKey("key1");
        existing.setContent("old");
        existing.setTags(Set.of("web"));

        TranslationDto dto = new TranslationDto(1L, "en", "key1", "updated", Set.of("mobile"));

        when(translationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(translationRepository.save(any(Translation.class))).thenReturn(existing);

        TranslationDto updated = translationService.updateTranslation(1L, dto);
        assertThat(updated.getContent()).isEqualTo("updated");
        assertThat(updated.getTags()).containsExactly("mobile");
    }

    @Test
    void testGetByLocale() {
        Translation en = new Translation();
        en.setId(1L);
        en.setLocale("en");
        en.setTranslationKey("k");
        en.setContent("c");
        en.setTags(Set.of("web"));
        when(translationRepository.findByLocale("en")).thenReturn(List.of(en));
        var result = translationService.exportTranslations("en");
        assertThat(result).hasSize(1);
        assertThat(result).containsEntry("k","c");
    }
    @Test
    void testSearchTranslationsByTag() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Translation> page = new PageImpl<>(List.of(translation));
        Map<String, String> filters = Map.of("tag", "web");

        when(translationRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<TranslationDto> result = translationService.searchTranslations(filters, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getTags()).contains("web");
    }

    @Test
    void testCreateTranslation2() {
        TranslationDto dto = new TranslationDto(null, "en", "greeting", "Hello", Set.of("web"));
        when(translationRepository.save(any(Translation.class))).thenReturn(translation);

        TranslationDto result = translationService.createTranslation(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLocale()).isEqualTo("en");
        assertThat(result.getKey()).isEqualTo("greeting");
    }

    @Test
    void testUpdateTranslation2() {
        TranslationDto dto = new TranslationDto(1L, "en", "greeting", "Hi there", Set.of("mobile"));
        when(translationRepository.findById(1L)).thenReturn(Optional.of(translation));
        when(translationRepository.save(any(Translation.class))).thenReturn(translation);

        TranslationDto updated = translationService.updateTranslation(1L, dto);

        assertThat(updated.getContent()).isEqualTo("Hi there");
        assertThat(updated.getTags()).contains("mobile");
    }
}
