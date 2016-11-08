package com.hstudio.ngontinh.async;

import android.os.AsyncTask;

import com.hstudio.ngontinh.ChapActivity;
import com.hstudio.ngontinh.object.Chap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by phhien on 6/16/2016.
 */
public class LoadChap extends AsyncTask<String, Integer, Chap> {

    ChapActivity mChapActivity;
    public LoadChap(ChapActivity chapActivity) {
        mChapActivity = chapActivity;
    }

    @Override
    protected Chap doInBackground(String... strings) {
        String url = strings[0];
        Chap chap = new Chap();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mChapActivity.getAssets().open(url)));
            chap.setTitle(reader.readLine());
            String text = "";
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text += mLine;
            }
            chap.setData(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chap;
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
