package com.example.fabiandrees.model;

import java.io.Serializable;

public class Flashcard implements Serializable {
    private String topic;
    private String text;
    private String title;
    private boolean answeredCorrectly;
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

    public boolean getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        answeredCorrectly = answeredCorrectly;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public void incrementLevel(){ level++; }

    public void decrementLevel(){ level--; }
}
