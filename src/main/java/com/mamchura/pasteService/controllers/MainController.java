package com.mamchura.pasteService.controllers;

import com.mamchura.pasteService.dto.TextEntityDTO;
import com.mamchura.pasteService.models.TextEntity;
import com.mamchura.pasteService.services.TextEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

@RestController
public class MainController {

    private final TextEntityService textEntityService;

    @Autowired
    public MainController(TextEntityService textEntityService) {
        this.textEntityService = textEntityService;
    }

    @GetMapping("/{hash}")
    public TextEntityDTO getByHash(@PathVariable("hash") int hash) {
        return convertToDTO(textEntityService.findOneById(hash));
    }

    @GetMapping("/")
    public Collection<TextEntityDTO> getPasteList() {
        return textEntityService.findAllWithCheck().stream().map(this::convertToDTO).toList();
    }

    @PostMapping("/")
    public String add(@RequestBody TextEntity request, HttpServletRequest httpServletRequest) {  //AtomicInteger
        int hash = textEntityService.save(request);
        return httpServletRequest.getRequestURL() + "" + hash;
    }

    public TextEntityDTO convertToDTO(TextEntity textEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(textEntity, TextEntityDTO.class);
    }
}
