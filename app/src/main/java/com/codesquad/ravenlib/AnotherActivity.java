package com.codesquad.ravenlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codesquad.raven.Raven;
import com.codesquad.raven.RavenMap;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AnotherActivity extends AppCompatActivity {

    TextView dataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        dataView = findViewById(R.id.text_data);
        Raven.init(this);
        Raven.getValues(this, new Raven.OnMessagesLoadedListener() {
            @Override
            public void onMessagesLoaded(RavenMap map) {
                List<Person> people = map.get("SOME", new TypeToken<List<Person>>(){});
                StringBuilder result = new StringBuilder();
                for (Person person : people) {
                    result.append(person.getName()).append("\n").append(person.getMoreData()).append("\n");
                }
                dataView.setText(result.toString());
            }
        });
    }
}
