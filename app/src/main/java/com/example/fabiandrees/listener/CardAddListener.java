package com.example.fabiandrees.listener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.fabiandrees.screens.NewCard;

public class CardAddListener<T extends AppCompatActivity> implements View.OnClickListener {
    private T appCompatActivity;

    public CardAddListener(T appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(appCompatActivity, NewCard.class);
        appCompatActivity.startActivity(intent);
    }
}
