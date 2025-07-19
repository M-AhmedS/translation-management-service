package com.example.tms.repository;

import com.example.tms.entity.Translation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TranslationRepository translationRepository;

    public DataSeeder(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }
    @Override
    @Transactional
    public void run(String... args) {
        if (translationRepository.count() == 0) {
            List<Translation> bulk = new ArrayList<>();
            for (int i=0; i<100000; i++){
                Translation trans = new Translation();
                trans.setTranslationKey("key_"+ i);
                trans.setContent("Content "+ i);
                trans.setLocale("en");
                trans.setTags(Set.of("web"));
                bulk.add(trans);
            }
            translationRepository.saveAll(bulk);
        }
    }
}
