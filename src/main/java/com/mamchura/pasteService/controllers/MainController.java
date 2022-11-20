package com.mamchura.pasteService.controllers;

import com.mamchura.pasteService.dto.TextEntityDTO;
import com.mamchura.pasteService.exceptions.ExpiredTimeException;
import com.mamchura.pasteService.exceptions.HashNotFoundException;
import com.mamchura.pasteService.models.TextEntity;
import com.mamchura.pasteService.services.TextEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private final TextEntityService textEntityService;

    @Autowired
    public MainController(TextEntityService textEntityService) {
        this.textEntityService = textEntityService;
    }

    @GetMapping("/{hash}")
    public TextEntityDTO getByHash(@PathVariable("hash") int hash) {
        return convertToDTO(textEntityService.findOneByHash(hash));
    }

    @GetMapping("/")
    public Collection<TextEntityDTO> getPasteList() {
        return textEntityService.findAll().stream().map(this::convertToDTO).toList();
    }

    @PostMapping("/")
    public String add(@RequestBody TextEntity request, HttpServletRequest httpServletRequest) {
        if (request.getHashes() == null)
            request.setHashes(getHashTable());
        int hash = textEntityService.save(request);
        return httpServletRequest.getRequestURL() + "" + hash;
    }

    @ExceptionHandler
    private ResponseEntity<String> handleHashNotFoundException(HashNotFoundException e) {
        return new ResponseEntity<>("Nothing found by this hash", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleExpiredTimeException(ExpiredTimeException e) {
        return new ResponseEntity<>("Record expired", HttpStatus.NOT_FOUND);
    }

    public TextEntityDTO convertToDTO(TextEntity textEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(textEntity, TextEntityDTO.class);
    }

    public Set<Integer> getHashTable() {
        return textEntityService.findAll()
                .stream()
                .map(TextEntity::getHash)
                .collect(Collectors.toSet());
    }
}
