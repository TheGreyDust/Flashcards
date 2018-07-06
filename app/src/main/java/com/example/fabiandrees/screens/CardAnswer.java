package com.example.fabiandrees.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fabiandrees.list.ExpandableListDataPump;
import com.example.fabiandrees.list.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardAnswer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    private Flashcard card;

    private static Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_answer);

        card = (Flashcard) getIntent().getExtras().get("card");

        //Anlegen der Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Anlegen und Befüllen des Fragetexts
        TextView questionView = (TextView) findViewById(R.id.textview_prompt);
        questionView.setText(card.getTitle());

        //Anlegen und Befüllen des Fragetexts
        TextView answerView = (TextView) findViewById(R.id.textView_answer);
        answerView.setText(card.getText());

        //Anlegen des "Falsch"-Buttons
        ImageButton wrongButton = (ImageButton) findViewById(R.id.btn_wrong);
        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.decrementLevel();
                Intent intent = new Intent(CardAnswer.this, RandomSelection.class);
                startActivity(intent);
            }
        });

        //Anlegen des "Richtig"-Buttons
        ImageButton rightButton = (ImageButton) findViewById(R.id.btn_right);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.incrementLevel();
                Intent intent = new Intent(CardAnswer.this, RandomSelection.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
