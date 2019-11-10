package com.example.githubapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et_uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_uname = findViewById(R.id.et_uname);
    }

    public void getUser(View view)
    {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("STRING_I_NEED", et_uname.getText().toString());
        startActivity(intent);
    }
}
