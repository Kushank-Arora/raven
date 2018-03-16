package com.codesquad.ravenlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codesquad.raven.Raven;

public class AnotherActivity extends AppCompatActivity {

    TextView dataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        dataView = findViewById(R.id.text_data);
        Raven.init(this);
        Raven.getValue(this, "SOME", new Raven.OnSingleMessageLoadedListener() {
            @Override
            public void onSingleMessageLoaded(Object value) {
                Person person = (Person) value;
                dataView.setText(person.getName() + "\n" + person.getMoreData());
            }
        });
    }
}
