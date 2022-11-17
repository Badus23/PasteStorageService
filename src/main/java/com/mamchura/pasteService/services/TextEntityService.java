package com.mamchura.pasteService.services;

import com.mamchura.pasteService.api.PasteStatus;
import com.mamchura.pasteService.models.TextEntity;
import com.mamchura.pasteService.repositories.TextEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextEntityService {

    private final TextEntityRepository repository;

    @Autowired
    public TextEntityService(TextEntityRepository repository) {
        this.repository = repository;
    }

    public int save(TextEntity textEntity) {
        repository.save(textEntity);
        return textEntity.getHash();
    }

    public TextEntity findOneById(int hash) {
        return repository.findByHash(hash);
    }

    public List<TextEntity> findAll() {
        return repository.findAll();
    }

    public List<TextEntity> findAllWithCheck() {
        return repository.findAll().stream().filter(e -> e.getPublicStatus().equals(PasteStatus.PUBLIC)).toList();
    }
}
