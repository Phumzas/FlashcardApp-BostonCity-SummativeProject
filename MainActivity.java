package com.example.androidsummative1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText title, description;
    Button insert, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        insert = findViewById(R.id.btnInsert);
        view = findViewById(R.id.btnView);

        DB = new DBHelper(this);

        // Correct Intent target: UserList.class (your list display activity)
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserList.class));
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = title.getText().toString().trim();
                String descriptionText = description.getText().toString().trim();

                if (titleText.isEmpty() || descriptionText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both Title and Description", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean checkInsertData = DB.insertuserdata(titleText, descriptionText);
                if (checkInsertData) {
                    Toast.makeText(MainActivity.this, "Note Inserted", Toast.LENGTH_SHORT).show();
                    title.setText("");
                    description.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Note Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
