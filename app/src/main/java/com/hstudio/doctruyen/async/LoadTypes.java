package com.hstudio.doctruyen.async;

import android.os.AsyncTask;

import com.hstudio.doctruyen.MainActivity;
import com.hstudio.doctruyen.object.Type;
import com.hstudio.doctruyen.utils.ConfigHelper;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by phhien on 6/7/2016.
 */
public class LoadTypes extends AsyncTask<String, Integer, List<Type>> {

    private MainActivity mMainActivity;
    public LoadTypes(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }
    @Override
    protected List<Type> doInBackground(String... urls) {
        String url = urls[0];
        JSONObject config = ConfigHelper.getJsonObject(mMainActivity);
        List<Type> types = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements li = doc.select(config.getString("type"));
            for(Element e : li) {
                Type type = new Type();
                type.setTitle(e.text());
                type.setLink(e.attr("href"));
                types.add(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(final List<Type> result) {
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMainActivity.setTypes(result);
            }
        });
    }
}
