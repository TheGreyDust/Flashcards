package com.example.fabiandrees.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fabiandrees.list.ExpandableListDataPump;
import com.example.fabiandrees.model.Flashcard;
import com.example.fabiandrees.listener.CardAddListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSelection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    Random random = new Random();
    private static List<Flashcard> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_selection);

        if(cards.size()<=0) {
            if (getIntent().getExtras() == null || getIntent().getExtras().get("topic") == null){
                cards = ExpandableListDataPump.getAllCards();
            }
            //else
                //cards.addAll(ExpandableListDataPump.getData().get(getIntent().getExtras().get("topic")));
        }

        if(cards.size()<=0){
            startActivity(new Intent(RandomSelection.this, CardAdministration.class));
        }
        else {
            int index = random.nextInt(cards.size());

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
            TextView textView = (TextView) findViewById(R.id.textview_prompt);
            textView.setText(cards.get(index).getTitle());

            //Anlegen des "Weiter"-Buttons
            ImageButton showAnswerButton = (ImageButton) findViewById(R.id.btn_show_answer);
            showAnswerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RandomSelection.this, CardAnswer.class);
                    Bundle b = new Bundle();
                    b.putSerializable("card", cards.get(index));
                    intent.putExtras(b);
                    cards.remove(index);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.random_selection, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    //TODO(Fabian): Logik für Navigationsleiste
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_card_administration) {
            intent = new Intent(this, CardAdministration.class);
            startActivity(intent);
        } else if (id == R.id.nav_statistics) {
            intent = new Intent(this, Statistics.class);
            startActivity(intent);
        } /*else if (id == R.id.nav_settings) {
            intent = new Intent(this, Settings.class);
            startActivity(intent);
        } */ else if (id == R.id.nav_about) {
            intent = new Intent(this, About.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
