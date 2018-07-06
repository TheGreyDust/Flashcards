package com.example.fabiandrees.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fabiandrees.list.ExpandableListDataPump;
import com.example.fabiandrees.model.Category;
import com.example.fabiandrees.model.Flashcard;
import com.example.fabiandrees.util.PersistenceManager;

import java.util.ArrayList;

public class NewCard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private String selectedCategory;
    private Spinner categorySpinner;
    private ArrayAdapter<String> categorySpinnerAdapter;
    private ArrayList<String> categoryTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        categoryTitles = ExpandableListDataPump.categoryNamesToList();

        //Anlegen der Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Anlegen des Spinners und befüllen mit Daten aus der category_array.xml
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryTitles);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        int[] positions = this.getIntent().getExtras().getIntArray("EditCard");
        if(positions != null) {
            categorySpinner.setSelection(positions[0]);

            EditText title = (EditText) findViewById(R.id.input_title);
            title.getText().clear();
            title.getText().append(ExpandableListDataPump.getData().get(positions[0]).getCards().get(positions[1]).getTitle());

            EditText text = (EditText) findViewById(R.id.input_text);
            text.getText().clear();
            text.getText().append(ExpandableListDataPump.getData().get(positions[0]).getCards().get(positions[1]).getText());
        }

        Button newCategoryButton = (Button) findViewById(R.id.btn_new_category);
        newCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewCategoryDialog();
            }
        });

        //Anlegen des Buttons zum Speichern der neuen Karteikarte
        Button addNewCardButton = (Button) findViewById(R.id.btn_save_new_card);
        addNewCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText)findViewById(R.id.input_title);
                EditText text = (EditText)findViewById(R.id.input_text);
                Flashcard card = new Flashcard(selectedCategory, title.getText().toString(), text.getText().toString());
                if(title.getText() != null && text.getText() != null && selectedCategory != null) {
                    if(positions != null) {
                        Category oldCategory  = ExpandableListDataPump.getData().get(positions[0]);
                        card = ExpandableListDataPump.getData().get(positions[0]).getCards().get(positions[1]);
                        card.setText(text.getText().toString());
                        card.setTitle(title.getText().toString());
                        if(!oldCategory.getCategoryName().equals(selectedCategory)) {
                            card.setTopic(selectedCategory);
                            oldCategory.removeCard(card);
                            ExpandableListDataPump.addData(selectedCategory, card);
                            PersistenceManager.persist(card, getFilesDir());
                            if(oldCategory.getCards().size() == 0) ExpandableListDataPump.getData().remove(oldCategory);
                        }
                    }
                    else ExpandableListDataPump.addData(selectedCategory,
                            new Flashcard(selectedCategory, title.getText().toString(), text.getText().toString()));
                } else if(selectedCategory == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCard.this);
                    builder.setMessage("Bitte wähle eine Kategorie aus!")
                            .setTitle("Keine Kategorie ausgewählt!");
                    AlertDialog newCategoryDialog = builder.create();
                    newCategoryDialog.show();
                    ExpandableListDataPump.addData(selectedCategory,
                            card);
                    PersistenceManager.persist(card, getFilesDir());
                } else if(title.getText() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCard.this);
                    builder.setMessage("Bitte geben Sie einen Titel ein!")
                            .setTitle("Kein Titel festgelegt!");
                    AlertDialog newCategoryDialog = builder.create();
                    newCategoryDialog.show();
                } else if(text.getText() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCard.this);
                    builder.setMessage("Bitte geben sie einen Text für die Karteikarte ein!")
                            .setTitle("Kein Text festgelegt!");
                    AlertDialog newCategoryDialog = builder.create();
                    newCategoryDialog.show();
                }
                Intent intent = new Intent(NewCard.this, CardAdministration.class);
                startActivity(intent);
                toolbar.getLogo();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CardAdministration.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_card, menu);
        return true;
    }

    protected void showNewCategoryDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(NewCard.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_new_category, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewCard.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.input_new_category_title);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Erstellen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String categoryTitle = editText.getText().toString();
                        categoryTitles.add(categoryTitle);
                        categorySpinnerAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton( "Abbrechen",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }
}
