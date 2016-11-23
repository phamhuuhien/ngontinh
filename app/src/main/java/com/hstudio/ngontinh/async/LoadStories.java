package com.hstudio.ngontinh.async;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hstudio.ngontinh.NewsFragment;
import com.hstudio.ngontinh.object.Story;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phhien on 6/8/2016.
 */
public class LoadStories extends AsyncTask<String, Integer, List<Story>> {

    private NewsFragment mFragment;
    public LoadStories(NewsFragment fragment) {
        mFragment = fragment;
    }
    @Override
    protected List<Story> doInBackground(String... urls) {
//        String url = urls[0];

            Request request = new Request.Builder()
                    .url("http://truyenserver.esy.es/index.php/story")
                    .build();

            OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            Gson gson = new Gson();
            Type type = new TypeToken<List<Story>>(){}.getType();
            List<Story> stories = gson.fromJson(response.body().string(), type);
            for (Story story : stories){
                Log.e("Story", story.getName() + "-" + story.getDes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(final List<Story> result) {
        mFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFragment.setStories(result);
            }
        });
    }
}
