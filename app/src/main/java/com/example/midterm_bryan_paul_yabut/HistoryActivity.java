package com.example.midterm_bryan_paul_yabut;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView historyList;
    ArrayAdapter<Integer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = findViewById(R.id.historyList);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.history);
        historyList.setAdapter(adapter);
    }
}
