package com.mamchura.pasteService.repositories;

import com.mamchura.pasteService.models.TextEntity;
import com.mamchura.pasteService.services.TextEntityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TextEntityRepository extends JpaRepository<TextEntity, Integer> {
    Optional<TextEntity> findById(int id);
    TextEntity findByHash(int hash);
}
