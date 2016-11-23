package com.hstudio.ngontinh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hstudio.ngontinh.adapter.TypeAdapter;
import com.hstudio.ngontinh.async.LoadTypes;
import com.hstudio.ngontinh.async.Search;
import com.hstudio.ngontinh.object.Story;
import com.hstudio.ngontinh.object.Type;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RecyclerView mRecyclerView;
    TypeAdapter mTypeAdapter;
    MaterialSearchView searchView;
    private AdView mAdView;
    private List<Story> suggestionStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        initRecyclerView();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = searchView.getSearchText();
                for(Story story : suggestionStories) {
                    if(story.getName().equals(text)) {
                        Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                        intent.putExtra("LINK", story.getUrl());
                        MainActivity.this.startActivity(intent);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                new Search(MainActivity.this).execute("http://truyenfull.vn/ajax.php", newText);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setSuggestions(new String[]{""});
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        mFragmentTransaction.replace(R.id.containerView, new NewsFragment()).commit();

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void setTypes(List<Type> types) {
        mTypeAdapter.setTypeList(types);
        mTypeAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<Type> types = new ArrayList<>();
        mTypeAdapter = new TypeAdapter(MainActivity.this);
        mTypeAdapter.setTypeList(types);
        mRecyclerView.setAdapter(mTypeAdapter);

        new LoadTypes(MainActivity.this).execute("http://truyenfull.vn");
    }

    public void onTypeClick(Type type) {
        mDrawerLayout.closeDrawers();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new NewsFragment().setUrl(type.getLink())).commit();
    }

    public void setSuggestions(List<Story> stories) {
        suggestionStories = stories;
        ArrayList<String> test = new ArrayList<>();
        for(Story story : stories) {
            test.add(story.getName());
        }
        String[] a = new String[stories.size()];
        test.toArray(a);
        searchView.updateSuggesstions(a);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}