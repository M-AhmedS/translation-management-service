package com.example.tms.controller;

import com.example.tms.dto.TranslationDto;
import com.example.tms.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<List<TranslationDto>> getAll(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(translationService.searchTranslations(filters));
    }
    @PostMapping
    public ResponseEntity<TranslationDto> create(@RequestBody @Valid TranslationDto translationDto) {
        return ResponseEntity.ok(translationService.createTranslation(translationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TranslationDto> update(@PathVariable Long id, @RequestBody @Valid TranslationDto translationDto) {
        return ResponseEntity.ok(translationService.updateTranslation(id, translationDto));
    }
    @GetMapping("/export/{locale}")
    public ResponseEntity<Map<String, String>> exportByLocale(@PathVariable String locale) {
        return ResponseEntity.ok(translationService.exportTranslations(locale));
    }

}
