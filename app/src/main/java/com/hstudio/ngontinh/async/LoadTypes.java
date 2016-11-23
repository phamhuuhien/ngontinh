package com.hstudio.ngontinh.async;

import android.os.AsyncTask;

import com.hstudio.ngontinh.MainActivity;
import com.hstudio.ngontinh.object.Type;
import com.hstudio.ngontinh.utils.ConfigHelper;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
//        try {
//            Document doc = Jsoup.connect(url).get();
//            Elements li = doc.select(config.getString("type"));
//            for(Element e : li) {
//                Type type = new Type();
//                type.setName(e.text());
//                type.setLink(e.attr("href"));
//                types.add(type);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
