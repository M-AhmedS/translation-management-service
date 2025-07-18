package com.example.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslationDto {
    private Long id;
    private String locale;
    private String key;
    private String content;
    private Set<String> tags;
}
