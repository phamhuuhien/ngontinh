package com.hstudio.ngontinh.async;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hstudio.ngontinh.StoryActivity;
import com.hstudio.ngontinh.object.Story;
import com.hstudio.ngontinh.object.StoryDetail;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phhien on 6/16/2016.
 */
public class LoadStoryDetail extends AsyncTask<Integer, Integer, StoryDetail> {

    private StoryActivity mStoryActivity;

    public LoadStoryDetail(StoryActivity storyActivity) {
        mStoryActivity = storyActivity;
    }

    @Override
    protected StoryDetail doInBackground(Integer... ids) {
        Integer id = ids[0];
        StoryDetail storyDetail = new StoryDetail();
        Request request = new Request.Builder()
                .url("http://truyenserver.esy.es/story.php/" + id)
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            Gson gson = new Gson();
            Type type = new TypeToken<StoryDetail>(){}.getType();
            storyDetail = gson.fromJson(response.body().string(), type);
            return storyDetail;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return storyDetail;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(final StoryDetail result) {
        mStoryActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStoryActivity.updateUI(result);
            }
        });
    }
}
