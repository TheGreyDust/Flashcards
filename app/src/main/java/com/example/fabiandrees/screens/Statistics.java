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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fabiandrees.list.ExpandableListDataPump;
import com.example.fabiandrees.listener.CardAddListener;
import com.example.fabiandrees.model.Category;
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
    private Spinner categorySpinner;
    private ArrayAdapter<String> categorySpinnerAdapter;
    private ArrayList<String> categoryTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        categoryTitles = ExpandableListDataPump.categoryNamesToList();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView numberViewed = (TextView) findViewById(R.id.textView_num_of_viewed);
        TextView numberOfCards = (TextView) findViewById(R.id.textView_num_of_cards);

        //PieChart
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(35f);
        pieChart.setHoleRadius(30f);
        pieChart.animateY(500);

        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryTitles);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category selectedCategory = ExpandableListDataPump
                        .getCategoryByName((String)adapterView.getItemAtPosition(i));

                numberOfCards.setText("Anzahl Karten: " + selectedCategory.getCards().size());
                numberViewed.setText("Angesehen: " + selectedCategory.getNumberViewed());

                float correctPercentage = selectedCategory.calculatePercentageCorrect();
                entries.clear();
                entries.add(new PieEntry(correctPercentage, "Richtig"));
                entries.add(new PieEntry(100.0f - correctPercentage, "Falsch"));


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
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
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
