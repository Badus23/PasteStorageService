package com.mamchura.pasteService.services;

import com.mamchura.pasteService.api.PasteStatus;
import com.mamchura.pasteService.exceptions.HashNotFoundException;
import com.mamchura.pasteService.models.TextEntity;
import com.mamchura.pasteService.repositories.TextEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@Component
public class TextEntityService extends com.mamchura.pasteService.services.Service {

    private final TextEntityRepository repository;

    @Autowired
    public TextEntityService(TextEntityRepository repository) {
        this.repository = repository;
    }

    public int save(TextEntity textEntity) {
        textEntity.setExpiringDate(new Date());
        repository.save(textEntity);
        return textEntity.getHash();
    }

    public TextEntity findOneByHash(int hash) {
        return repository.findByHash(hash)
                .orElseThrow(HashNotFoundException::new);
    }

    public List<TextEntity> findAll() {
        return repository.findAll();
    }

    public List<TextEntity> findAllWithCheck() {
        return repository.findAll().stream()
                .filter(e -> e.getPublicStatus().equals(PasteStatus.PUBLIC))
                .toList();
    }
}
