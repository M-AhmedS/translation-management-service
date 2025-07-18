package com.example.tms.service;

import com.example.tms.dto.TranslationDto;
import com.example.tms.entity.Translation;
import com.example.tms.repository.TranslationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TranslationService {

    private final TranslationRepository translationRepository;

    @Transactional(readOnly = true)
    public List<TranslationDto> searchTranslations(Map<String, String> filters) {
        Specification<Translation> spec = Specification.allOf();

        if (filters.containsKey("locale") && !filters.get("locale").isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("locale"), filters.get("locale")));
        }
        if (filters.containsKey("key") && !filters.get("key").isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("key"), "%" + filters.get("key") + "%"));
        }
        if (filters.containsKey("content") && !filters.get("content").isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("content"), "%" + filters.get("content") + "%"));
        }
        if (filters.containsKey("tag") && !filters.get("tag").isBlank()) {
            String tag = filters.get("tag");
            spec = spec.and((root, query, cb) -> cb.isMember(tag, root.get("tags")));
        }

        return translationRepository.findAll(spec).stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    public TranslationDto createTranslation(TranslationDto dto) {
        Translation translation = new Translation();
        translation.setLocale(dto.getLocale());
        translation.setKey(dto.getKey());
        translation.setContent(dto.getContent());
        translation.setTags(dto.getTags());

        Translation saved = translationRepository.save(translation);
        return mapToDto(saved);
    }

    private TranslationDto mapToDto(Translation translation) {
        return new TranslationDto(translation.getId(), translation.getLocale(), translation.getKey(),
                translation.getContent(), translation.getTags());
    }

    @Transactional(readOnly = true)
    public Map<String, String> exportTranslations(String locale){
        List<Translation> translations = translationRepository.findByLocale(locale);
        return translations.stream().collect(Collectors.toMap(Translation::getKey,Translation::getContent,
                (existing, replacement) -> existing));
    }

    public TranslationDto updateTranslation(Long id, TranslationDto dto) {
        Translation existing = translationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Translation not found with id: " + id));
        existing.setLocale(dto.getLocale());
        existing.setKey(dto.getKey());
        existing.setContent(dto.getContent());
        existing.setTags(dto.getTags());

        Translation updated = translationRepository.save(existing);
        return mapToDto(updated);
    }

}
