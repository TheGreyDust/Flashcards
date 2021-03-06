package com.example.fabiandrees.list;

import com.example.fabiandrees.model.Category;
import com.example.fabiandrees.model.Flashcard;

import java.util.ArrayList;

public class ExpandableListDataPump {
    private static ArrayList<Category> categoryList;

    public static void addData(String topic, Flashcard card) {
        for(Category category : categoryList) {
            if (category.getCategoryName().equals(topic)) {
                category.addCard(card);
                return;
            }
        }

        Category category = new Category(topic);
        category.addCard(card);
        categoryList.add(category);
    }

    public static void setCategoryList(ArrayList<Category> list) {
        if(list != null) categoryList = list;
        else categoryList = new ArrayList<>();
    }

    public static ArrayList<Category> getData() {
        return categoryList;
    }

    public static Category getCategoryByName(String categoryName) {
        for(Category category : categoryList) {
            if(category.getCategoryName().equals(categoryName)) return category;
        }
        return null;
    }

    public static ArrayList<String> categoryNamesToList() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for(Category category : categoryList) {
            categoryNames.add(category.getCategoryName());
        }

        return categoryNames;
    }

    public static ArrayList<Flashcard> getAllCards() {
        ArrayList<Flashcard> cards = new ArrayList<>();
        for(Category category : categoryList) {
            for(Flashcard card : category.getCards()) {
                cards.add(card);
            }
        }

        return cards;
    }

    public static void setCardCorrect(Flashcard card, boolean isCorrect) {
        for(Category category : categoryList) {
            for(Flashcard originalCard : category.getCards()) {
                if(originalCard.equals(card)) {
                    if(isCorrect)  originalCard.incrementLevel();
                    else originalCard.decrementLevel();
                    originalCard.setAnsweredCorrectly(isCorrect);
                }
            }
        }
    }

    public static void setCardViewed(Flashcard card) {
        for(Category category : categoryList) {
            for(Flashcard originalCard : category.getCards()) {
                if(originalCard.equals(card)) {
                    originalCard.setWasViewed(true);
                }
            }
        }
    }
}
