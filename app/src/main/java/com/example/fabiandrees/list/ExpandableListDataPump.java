package com.example.fabiandrees.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    private static HashMap<String, List<Flashcard>> expandableListDetail = new HashMap<String, List<Flashcard>>();

    public static void addData(String topic, Flashcard card) {
        List<Flashcard> cardList = expandableListDetail.get(topic);
        if(cardList == null) cardList = new ArrayList<Flashcard>();
        cardList.add(card);
        expandableListDetail.put(topic, cardList);
    }

    public static HashMap<String, List<Flashcard>> getData() {
        return expandableListDetail;
    }
}
