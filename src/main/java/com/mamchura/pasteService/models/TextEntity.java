package com.mamchura.pasteService.models;

import com.mamchura.pasteService.api.PasteStatus;
import com.mamchura.pasteService.services.TextEntityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "text_entity")
public class TextEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private String data;

    @Column(name = "expiration_time")
    private long expirationTime;

    @Column(name = "public_status")
    private PasteStatus publicStatus;

    @Column(name = "hash")
    private int hash;

    private static final Set<Integer> set = new HashSet<>();

    public TextEntity(String data, long expirationTime, PasteStatus publicStatus) {
        this.data = data;
        this.expirationTime = expirationTime;
        this.publicStatus = publicStatus;
    }

    @PrePersist
    private void hashInit() {  // Привязать к БД
        int hashCode = this.hashCode();
        if (set.contains(hashCode)) {
            hashCode *= new Random().nextInt(32);
        }
        set.add(hashCode);
        this.hash = hashCode;
    }

    public TextEntity() {
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public PasteStatus getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(PasteStatus publicStatus) {
        this.publicStatus = publicStatus;
    }
}
