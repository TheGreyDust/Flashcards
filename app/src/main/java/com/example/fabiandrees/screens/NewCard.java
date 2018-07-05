package com.example.fabiandrees.screens;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fabiandrees.list.ExpandableListDataPump;
import com.example.fabiandrees.list.Flashcard;

public class NewCard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        //Anlegen der Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Anlegen des Spinners und befüllen mit Daten aus der category_array.xml
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        /*spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = (String)adapterView.getItemAtPosition(i);

                if(selectedCategory.equals("Neue Kategorie...")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCard.this);
                    builder.setMessage("test")
                            .setTitle("test");
                    AlertDialog newCategoryDialog = builder.create();
                    newCategoryDialog.show();
                }
            }
        });
        */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = (String)adapterView.getItemAtPosition(i);

                if(selectedCategory.equals("Neue Kategorie...")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCard.this);
                    builder.setMessage("test")
                            .setTitle("test");
                    AlertDialog newCategoryDialog = builder.create();
                    newCategoryDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //Anlegen des Buttons zum Speichern der neuen Karteikarte
        Button addNewCardButton = (Button) findViewById(R.id.btn_save_new_card);
        addNewCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText)findViewById(R.id.input_title);
                EditText text = (EditText)findViewById(R.id.input_text);
                if(title.getText() != null && text.getText() != null && selectedCategory != null) {
                    ExpandableListDataPump.addData(selectedCategory,
                            new Flashcard(selectedCategory, title.getText().toString(), text.getText().toString()));
                } else if(title.getText() == null) {
                    System.out.println("Title darf nicht null sein!");
                } else if(text.getText() == null) {
                    System.out.println("Text darf nicht null sein!");
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
