package com.hstudio.doctruyen;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hstudio.doctruyen.adapter.ChapAdapter;
import com.hstudio.doctruyen.async.LoadStories;
import com.hstudio.doctruyen.async.LoadStoryDetail;
import com.hstudio.doctruyen.object.ChapItem;
import com.hstudio.doctruyen.object.StoryDetail;
import com.hstudio.doctruyen.utils.EndlessRecyclerViewScrollListener;
import com.hstudio.doctruyen.utils.InteractiveScrollView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phhien on 6/14/2016.
 */
public class StoryActivity extends AppCompatActivity {

    private TextView title;
    private ExpandableTextView description;
    private ImageView imageView;
    private RecyclerView listChap;
    private Toolbar toolbar;
    private String link;
    private ChapAdapter chapAdapter;
    private InteractiveScrollView scrollView;
    private int page = 1;
    private boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_layout);

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
        description = (ExpandableTextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.imageView);
        scrollView = (InteractiveScrollView) findViewById(R.id.scrollView);
        listChap = (RecyclerView) findViewById(R.id.listChap);
        listChap.setHasFixedSize(true);
        listChap.setNestedScrollingEnabled(false);
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listChap.setLayoutManager(mLayoutManager);
        listChap.setItemAnimator(new DefaultItemAnimator());
        List<ChapItem> chaps = new ArrayList<>();
        chapAdapter = new ChapAdapter(this, chaps);
        listChap.setAdapter(chapAdapter);

        link = getIntent().getStringExtra("LINK");

        new LoadStoryDetail(this).execute(link);

        InteractiveScrollView.OnBottomReachedListener listener = new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                if(!isStop) {
                    page++;
                    new LoadStoryDetail(StoryActivity.this).execute(link + "trang-" + page);
                } else {
                    Toast.makeText(StoryActivity.this, "Don't have any chaps", Toast.LENGTH_SHORT).show();
                }
                System.out.println("loading: " + link + "trang-" + page);
            }
        };
        scrollView.setOnBottomReachedListener(listener);
    }

    public void updateUI(StoryDetail storyDetail) {
        toolbar.setTitle(storyDetail.getTitle());
        title.setText(storyDetail.getTitle());
        if(!TextUtils.isEmpty(storyDetail.getImage())) {
            Picasso.with(this).load(storyDetail.getImage()).resize(215, 215).centerCrop().into(imageView);
        }
        description.setText(Html.fromHtml(storyDetail.getDescription()));
        if(storyDetail.getChaps().size() < 50) {
            isStop = true;
        }
        chapAdapter.addChaps(storyDetail.getChaps());
        chapAdapter.notifyDataSetChanged();
    }
}
