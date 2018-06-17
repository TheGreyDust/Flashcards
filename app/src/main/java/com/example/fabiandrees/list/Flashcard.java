package com.example.fabiandrees.list;

public class Flashcard {
    private String topic;
    private String text;
    private String title;

    public Flashcard(String topic, String title, String text) {
        this.topic = topic;
        this.title = title;
        this.text = text;
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
}
