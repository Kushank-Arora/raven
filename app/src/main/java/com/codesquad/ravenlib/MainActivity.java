package com.codesquad.ravenlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codesquad.raven.Raven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Raven.init(this);
    }

    public void onSendClick(View view) {
        String name = ((EditText)findViewById(R.id.text_name)).getText().toString();
        String moreDate = ((EditText) findViewById(R.id.text_more)).getText().toString();
        List<Person> person = new ArrayList<>(Arrays.asList(
                new Person(name, moreDate),
                new Person(name, moreDate),
                new Person(name, moreDate)
        ));

        Raven.startCommunication(this, AnotherActivity.class)
                .add("SOME", person)
                .save(new Raven.OnMessagesSavedListener() {
                    @Override
                    public void onMessagesSaved() {
                        startActivity(new Intent(MainActivity.this, AnotherActivity.class));
                    }
                });
    }
}
