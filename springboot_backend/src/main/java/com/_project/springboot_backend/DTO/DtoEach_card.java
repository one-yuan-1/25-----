package com._project.springboot_backend.DTO;

public class DtoEach_card {
    String id;
    String username;
    String title;
    String text;
    String image;
    // 无参构造
    public DtoEach_card() {}

    // Getter 和 Setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String un) {
        this.username = un;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String img) {
        this.image = img;
    }
}
