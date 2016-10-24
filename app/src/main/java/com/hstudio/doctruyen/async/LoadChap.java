package com.hstudio.doctruyen.async;

import android.content.Context;
import android.os.AsyncTask;

import com.hstudio.doctruyen.ChapActivity;
import com.hstudio.doctruyen.object.Chap;
import com.hstudio.doctruyen.object.Story;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

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
        try {
            Document doc = Jsoup.connect(url).get();
            chap.setTitle(doc.title().split("-")[1]);
            Elements data = doc.select("div.chapter-content-rb");
            chap.setData(data.toString());
            Elements prev = doc.select("#prev_chap");
            chap.setPrevious(prev.get(0).attr("href"));
            Elements next = doc.select("#next_chap");
            chap.setNext(next.get(0).attr("href"));
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
