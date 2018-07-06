package com.example.fabiandrees.util;

import android.os.Environment;

import com.example.fabiandrees.model.Category;
import com.example.fabiandrees.model.Flashcard;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PersistenceManager {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void persist(Flashcard card, File filesDir){
        try {
            String title = card.getTitle();
            //String path = "/home/river/.android/avd/Nexus_5X_API_26.avd/data" + File.separator
            //        + "Flashcards" + File.separator + card.getTopic();
            String path = filesDir.getPath() + File.separator
                    + "Flashcards" + File.separator + card.getTopic();
            File dir = new File(path);
            if(!dir.exists()) dir.mkdirs();
            File file = new File(path + File.separator + title.substring(0,15<title.length()?15:title.length()) + ".json");
            System.out.println(file.getAbsolutePath());
            if(!file.exists())
                file.createNewFile();
            System.out.println("\n\n\n\n" + file.getAbsolutePath()+ "\n\n\n\n" );
            mapper.writeValue(file, card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Category> open(File filesDir){
        ArrayList<Category> list = new ArrayList<>();
        File dir = new File(filesDir.getPath() + File.separator
                + "Flashcards");
        if(!dir.exists()) dir.mkdirs();
        String[] subdirs = dir.list((current, name) -> new File(current, name).isDirectory());
        if(subdirs != null) {
            for (String topic : subdirs) {
                Category category = new Category(topic);
                try {
                    String path = Environment.getDataDirectory() + File.separator
                            + "Flashcards" + File.separator + topic;
                    File subdir = new File(path);
                    String[] files = subdir.list();
                    if(files!= null && files.length>0) {
                        for (String filename : files) {
                            Flashcard card = mapper.readValue(new File(path + File.separator + filename)
                                    , Flashcard.class);
                            category.addCard(card);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                list.add(category);
            }
        }
        return list;
    }
}
