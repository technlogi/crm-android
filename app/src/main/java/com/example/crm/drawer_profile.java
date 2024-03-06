package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class drawer_profile extends AppCompatActivity {
    View view;
    TextView logout, user_name, user_contact;
    String name, contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_drawer_profile);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        name = sharedPreferences.getString("user_name", "");
        contact = sharedPreferences.getString("user_contact", "");

        logout = findViewById(R.id.logout);
        user_name =findViewById(R.id.user_name);
        user_contact = findViewById(R.id.user_contact);

        user_name.setText(name);
        user_contact.setText(contact);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences preferences = view.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(view.getContext(), LoginScreenActivity.class);
                startActivity(i);
            }
        });
    }
}