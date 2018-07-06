package com.example.fabiandrees.screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fabiandrees.listener.CardAddListener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    PieChart pieChart;
    List<PieEntry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //PieChart
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(35f);
        pieChart.setHoleRadius(30f);
        pieChart.animateY(500);

        //TODO: Je nach Kategorie die Anzahl der richtigen/falschen Antworten auslesen -> Verhältnis berechnen -> in Entries setzen
        entries.add(new PieEntry(70.0f, "Richtig"));
        entries.add(new PieEntry(30.0f, "Falsch"));

        PieDataSet dataSet = new PieDataSet(entries,"");

        dataSet.setColors(new int[] {R.color.green, R.color.red}, getApplicationContext());

        dataSet.setValueTextSize(13f);

        Legend legend = pieChart.getLegend();
        legend.setTextSize(14f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        pieChart.setData(data);
        pieChart.invalidate();
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
        getMenuInflater().inflate(R.menu.statistics, menu);
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
        } else if (id == R.id.nav_random) {
            intent = new Intent(this, RandomSelection.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this, Settings.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            intent = new Intent(this, About.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
