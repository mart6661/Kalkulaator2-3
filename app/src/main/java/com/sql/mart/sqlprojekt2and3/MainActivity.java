package com.sql.mart.sqlprojekt2and3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private UOW uow;
    private OperationsAdapter operationsAdapter;
    private OperationTypesAdapter operationTypesAdapter;
    private StatisticsAdapter statisticsAdapter;
    private ListView listView;
    private TextView textView;
    private ArrayAdapter<String> adapter;
    ArrayList<String> myStringArray1 = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.dbResultsListView);
        textView = (TextView) findViewById(R.id.dbHeaderTextView);
        uow = new UOW(getApplicationContext());
        uow.setOperationTypes();
        displayOperations();
        refreshAll();
    }

    private void displayOperations() {
        String heading = "Tehete ajalugu";
        operationsAdapter = new OperationsAdapter(this, uow.operationsRepo.getCursorAll(), uow);
        textView.setText(heading);
        listView.setAdapter(operationsAdapter);

    }

    private void displayOperationTypes() {
        String heading = "Tehetetüübid";
        operationTypesAdapter = new OperationTypesAdapter(this, uow.operationTypesRepo.getCursorAll(), uow);

        textView.setText(heading);
        listView.setAdapter(operationTypesAdapter);
    }

    private void displayStatistics() {
        String heading = "Päeva statistika";
        statisticsAdapter = new StatisticsAdapter(this, uow.statisticsRepo.getCursorAll(), uow);

        textView.setText(heading);
        listView.setAdapter(statisticsAdapter);
    }

    private void deleteAll() {
        new AlertDialog.Builder(this) //start new alert
                .setTitle("Kas oled kindel...")
                .setMessage("...et soovid andmebaasi nullida?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { //yes button
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uow.DropCreateDatabase(); //drop database when clicked
                        Toast.makeText(MainActivity.this, "Andmebaas on nullitud", Toast.LENGTH_SHORT).show(); //show msg that its done
                        uow.setOperationTypes();
                        refreshAll(); //refresh views
                    }
                })
                .setNegativeButton(android.R.string.no, null).show(); //no button, dont do nothing
    }

    private void refreshAll() {
        displayOperations();
        displayOperationTypes();
        displayStatistics();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_history) {
            displayOperations();
        } else if (id == R.id.action_show_statistics) {
            displayStatistics();
        } else if (id == R.id.action_show_operationTypes) {
            displayOperationTypes();
        } else if (id == R.id.action_delete_all) {
            deleteAll();
        } else if (id == R.id.action_refresh_all) {
            refreshAll();}
        return super.onOptionsItemSelected(item);
    }
}
