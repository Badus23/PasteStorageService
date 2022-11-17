package com.mamchura.pasteService.dto;

public class TextEntityDTO {

    private String data;

    public TextEntityDTO(String data) {
        this.data = data;
    }

    public TextEntityDTO() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
