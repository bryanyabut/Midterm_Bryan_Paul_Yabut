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

    private EditText numberEditText;
    private ListView multiplicationListView;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> multiplicationList;

    // Static lists to hold data across activities
    private static final ArrayList<String> historyList = new ArrayList<>();
    private static final ArrayList<String> currentTableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        numberEditText = findViewById(R.id.numberEditText);
        Button generateButton = findViewById(R.id.insertBtn);
        multiplicationListView = findViewById(R.id.multiplicationListView);
        Button historyButton = findViewById(R.id.historyBtn);

        // Use the static list to persist data on screen rotation or return
        multiplicationList = new ArrayList<>(currentTableList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, multiplicationList);
        multiplicationListView.setAdapter(adapter);

        // Generate button click listener
        generateButton.setOnClickListener(v -> {
            String numberString = numberEditText.getText().toString();
            if (numberString.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int number = Integer.parseInt(numberString);
                generateMultiplicationTable(number);
                if (!historyList.contains(numberString)) {
                    historyList.add(numberString);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
            }
        });

        // ListView item click listener for deletion
        multiplicationListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = adapter.getItem(position);
            showDeleteConfirmationDialog(selectedItem, position);
        });

        // History button click listener
        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void generateMultiplicationTable(int number) {
        multiplicationList.clear(); // Clear previous results
        for (int i = 1; i <= 10; i++) {
            String result = number + " × " + i + " = " + (number * i);
            multiplicationList.add(result);
        }
        currentTableList.clear(); // Update the static list
        currentTableList.addAll(multiplicationList);
        adapter.notifyDataSetChanged(); // Refresh ListView
    }

    private void showDeleteConfirmationDialog(String item, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this row?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    multiplicationList.remove(position);
                    currentTableList.clear();
                    currentTableList.addAll(multiplicationList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Deleted: " + item, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_all) {
            showClearAllConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showClearAllConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Clear All")
                .setMessage("Are you sure you want to clear all items?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    multiplicationList.clear();
                    currentTableList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "All rows were cleared.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
