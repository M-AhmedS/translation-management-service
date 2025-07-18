package com.example.tms.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "translations")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable = false)
    private String locale;

    @Column(nullable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String content;

    @ElementCollection
    @CollectionTable(name = "translation_tags",joinColumns = @JoinColumn(name = "translation_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
}
