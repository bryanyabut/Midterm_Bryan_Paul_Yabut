package com.example.midterm_bryan_paul_yabut;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText numberInput;
    Button generateBtn, historyBtn;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> tableList = new ArrayList<>();
    static ArrayList<Integer> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.numberInput);
        generateBtn = findViewById(R.id.generateBtn);
        listView = findViewById(R.id.listView);
        historyBtn = findViewById(R.id.historyBtn);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tableList);
        listView.setAdapter(adapter);

        generateBtn.setOnClickListener(v -> {
            String input = numberInput.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
                return;
            }
            int num = Integer.parseInt(input);
            generateTable(num);
            if (!historyList.contains(num)) historyList.add(num);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selected = tableList.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Delete Row")
                    .setMessage("Delete " + selected + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        tableList.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Deleted: " + selected, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        historyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void generateTable(int number) {
        tableList.clear();
        for (int i = 1; i <= 10; i++) {
            tableList.add(String.valueOf(number * i));
        }
        adapter.notifyDataSetChanged();
    }
}
