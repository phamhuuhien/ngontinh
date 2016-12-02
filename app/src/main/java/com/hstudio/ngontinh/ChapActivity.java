package com.hstudio.ngontinh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hstudio.ngontinh.async.LoadChap;
import com.hstudio.ngontinh.object.Chap;

/**
 * Created by phhien on 6/16/2016.
 */
public class ChapActivity extends AppCompatActivity {

    private TextView title;
    private TextView data;
    private Button previous, next;
    private Chap mChap;
    private Toolbar toolbar;
    private String first, last;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chap);

        toolbar =  (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.title);
        data = (TextView) findViewById(R.id.data);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadChap(ChapActivity.this).execute(mChap.getId() - 1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadChap(ChapActivity.this).execute(mChap.getId() + 1);
            }
        });

        first = getIntent().getStringExtra("FIRST");
        last = getIntent().getStringExtra("LAST");
        Integer id = getIntent().getIntExtra("ID", -1);
        new LoadChap(this).execute(id);
    }

    public void setData(Chap chap) {
        mChap = chap;
        toolbar.setTitle(chap.getName());
        title.setText(chap.getName());
        data.setText(Html.fromHtml(chap.getContent()));
        if(first.equals(chap.getName())) {
            previous.setEnabled(false);
        } else {
            previous.setEnabled(true);
        }

        if(last.equals(chap.getName())) {
            next.setEnabled(false);
        } else {
            next.setEnabled(true);
        }
    }
}
