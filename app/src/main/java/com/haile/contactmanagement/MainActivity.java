package com.haile.contactmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haile.contactmanagement.adapter.ContactAdapter;
import com.haile.contactmanagement.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(contactAdapter);

        // Add Contact Button
        findViewById(R.id.btnAddContact).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Retrieve new contact details
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");

            // Check for null values and handle appropriately
            if (name != null && phone != null) {
                // Add new contact to list
                Contact newContact = new Contact(name, phone);
                contactList.add(newContact);

                // Notify the adapter that a new item has been inserted
                contactAdapter.notifyItemInserted(contactList.size() - 1);
            } else {
                // Handle null values if necessary, e.g., show a toast message
                Toast.makeText(this, "Invalid contact details", Toast.LENGTH_SHORT).show();
            }
        }
    }
}