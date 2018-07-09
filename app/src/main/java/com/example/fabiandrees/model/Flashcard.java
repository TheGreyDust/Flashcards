package com.example.fabiandrees.model;

import java.io.Serializable;

public class Flashcard implements Serializable {
    private String topic;
    private String text;
    private String title;
    private boolean answeredCorrectly;
    private int level;
    private boolean wasViewed;

    public Flashcard(String topic, String title, String text) {
        this.topic = topic;
        this.title = title;
        this.text = text;
        this.level = 0;
        this.wasViewed = answeredCorrectly = false;
    }

    public Flashcard(){ }

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
        return this.answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public void incrementLevel(){ level++; }

    public void decrementLevel(){ level--; }

    public boolean equals(Flashcard card) {
        if(topic.equals(card.getTopic()) && title.equals(card.getTitle())) return true;
        return false;
    }

    public void setWasViewed(boolean wasViewed) {
        this.wasViewed = wasViewed;
    }

    public boolean wasViewed() {
        return this.wasViewed;
    }
}
