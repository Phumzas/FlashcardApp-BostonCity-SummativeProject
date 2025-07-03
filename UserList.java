package com.example.androidsummative1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> title, description;
    DBHelper DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        DB = new DBHelper(this);
        title = new ArrayList<>();
        description = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MyAdapter(this, title, description);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displaydata();

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showEditDeleteDialog(position);
            }
        });
    }

    private void displaydata() {
        Cursor cursor = DB.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(UserList.this, "No Note Exists", Toast.LENGTH_SHORT).show();
            return;
        } else {
            title.clear();
            description.clear();
            while (cursor.moveToNext()) {
                title.add(cursor.getString(0));
                description.add(cursor.getString(1));
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void showEditDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Note");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_note, null);
        final EditText inputTitle = viewInflated.findViewById(R.id.editTitle);
        final EditText inputDescription = viewInflated.findViewById(R.id.editDescription);

        inputTitle.setText(title.get(position));
        inputDescription.setText(description.get(position));

        builder.setView(viewInflated);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newTitle = inputTitle.getText().toString().trim();
            String newDesc = inputDescription.getText().toString().trim();

            if (newTitle.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(this, "Title and Description cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Boolean updateResult = DB.updateuserdata(newTitle, newDesc);
            if (updateResult) {
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
                title.set(position, newTitle);
                description.set(position, newDesc);
                adapter.notifyItemChanged(position);
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Delete", (dialog, which) -> {
            String titleToDelete = title.get(position);
            Boolean deleteResult = DB.deletedata(titleToDelete);
            if (deleteResult) {
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
                title.remove(position);
                description.remove(position);
                adapter.notifyItemRemoved(position);
            } else {
                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}
