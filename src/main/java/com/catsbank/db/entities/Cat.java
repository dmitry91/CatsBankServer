package com.catsbank.db.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Arrays;

public class Cat {

    private int id;
    private String text;
    private byte[] photo;

    public Cat() {
    }

    public Cat(int id, String text, byte[] photo) {
        this.id = id;
        this.text = text;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String toJson() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
