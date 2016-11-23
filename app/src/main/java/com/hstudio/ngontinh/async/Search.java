package com.hstudio.ngontinh.async;

import android.os.AsyncTask;

import com.hstudio.ngontinh.MainActivity;
import com.hstudio.ngontinh.object.Story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hienpham on 6/24/2016.
 */
public class Search extends AsyncTask<String, Integer, List<Story>> {

    private MainActivity mMainActivity;

    public Search(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    protected List<Story> doInBackground(String... urls) {
        String url = urls[0];
        String query = urls[1];
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("type", "quick_search")
                .add("str", query)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        List<Story> stories = new ArrayList<>();
        Response response = null;
        try {
            response = client.newCall(request).execute();

            String[] data = response.body().string().split("</a>");
            for(String e : data) {
                Story story = new Story();
                story.setName(e.substring(e.lastIndexOf(">") + 1));
                story.setUrl(e.substring(e.indexOf("\"") + 1, e.indexOf("class") - 2));
                stories.add(story);
            }
            stories.remove(stories.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stories;
    }

    protected void onPostExecute(final List<Story> result) {
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMainActivity.setSuggestions(result);
            }
        });
    }
}
