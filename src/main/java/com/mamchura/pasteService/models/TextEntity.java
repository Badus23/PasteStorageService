package com.mamchura.pasteService.models;

import com.mamchura.pasteService.api.PasteStatus;
import com.mamchura.pasteService.services.TextEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Column(name = "hash", unique = true)
    private int hash;

    @Column(name = "date_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiringDate;

    private static Set<Integer> hashes;

    public TextEntity(String data, long expirationTime, PasteStatus publicStatus) {
        this.data = data;
        this.expirationTime = expirationTime;
        this.publicStatus = publicStatus;
    }

    @PrePersist
    private void hashInit() {  // Привязать к БД
        int hashCode = this.hashCode();
        if (hashes.contains(hashCode)) {
            hashCode *= new Random().nextInt(32);
        }
        hashes.add(hashCode);
        this.hash = hashCode;
    }

    public TextEntity() {
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public Set<Integer> getHashes() {
        return hashes;
    }

    public void setHashes(Set<Integer> set) {
        hashes = set;
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
