package com.example.fabiandrees.model;

import java.util.ArrayList;

public class Category {
    private ArrayList<Flashcard> cards;
    private String categoryName;

    public Category(String categoryName) {
        cards = new ArrayList<>();

        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void addCard(Flashcard card) {
        cards.add(card);
    }

    public ArrayList<Flashcard> getCards() {
        return this.cards;
    }

    public boolean removeCard(Flashcard card) {
        return cards.remove(card);
    }

    public float calculatePercentageCorrect() {
        return (getAnsweredCorrectlyCount() / cards.size()) * 100;
    }

    private int getAnsweredCorrectlyCount() {
        int count = 0;
        for(Flashcard card : cards) {
            if(card.getAnsweredCorrectly() == true) count++;
        }
        return count;
    }
}
