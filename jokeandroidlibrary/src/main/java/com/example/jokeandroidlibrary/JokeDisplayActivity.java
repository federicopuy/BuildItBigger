package com.example.jokeandroidlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String JOKE_INTENT = "jokeIntent";
    private TextView tvJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        tvJoke = findViewById(R.id.tvJoke);

        Intent intent = getIntent();
        if (intent.hasExtra(JOKE_INTENT)){
            String joke = intent.getStringExtra(JOKE_INTENT);
            tvJoke.setText(joke);
        } else{
            finish();
        }
    }
}
