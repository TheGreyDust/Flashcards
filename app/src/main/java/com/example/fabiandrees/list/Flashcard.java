package com.example.fabiandrees.list;

import java.io.Serializable;

public class Flashcard implements Serializable {
    private String topic;
    private String text;
    private String title;
    private int level;

    public Flashcard(String topic, String title, String text) {
        this.topic = topic;
        this.title = title;
        this.text = text;
        this.level = 0;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public void incrementLevel(){ level++; }

    public void decrementLevel(){ level--; }
}
