package com.hstudio.ngontinh.async;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hstudio.ngontinh.ChapActivity;
import com.hstudio.ngontinh.object.Chap;
import com.hstudio.ngontinh.object.Story;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phhien on 6/16/2016.
 */
public class LoadChap extends AsyncTask<Integer, Integer, Chap> {

    ChapActivity mChapActivity;
    public LoadChap(ChapActivity chapActivity) {
        mChapActivity = chapActivity;
    }

    @Override
    protected Chap doInBackground(Integer... ids) {
        Integer id = ids[0];
        Chap chap = new Chap();
        Request request = new Request.Builder()
                .url("http://truyenserver.esy.es/chap.php/" + id)
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            Gson gson = new Gson();
            Type type = new TypeToken<Chap>(){}.getType();
            chap = gson.fromJson(response.body().string(), type);
            return chap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(final Chap result) {
        mChapActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChapActivity.setData(result);
            }
        });
    }
}
