package com.hstudio.ngontinh.async;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.hstudio.ngontinh.NewsFragment;
import com.hstudio.ngontinh.object.Story;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
        String url = urls[0];
        List<Story> result = new ArrayList<>();
        BufferedReader reader = null;
        try {
            AssetManager am = mFragment.getActivity().getAssets();

            String [] aplist = am.list("");
            for(String s : aplist) {
                if(!s.contains(".") && s.contains("-")) {
                    reader = new BufferedReader(
                            new InputStreamReader(mFragment.getActivity().getAssets().open(s + "/desc.txt")));
                    String title = reader.readLine();
                    title = title.replace("Title:<h1>", "");
                    title = title.replace("</h1>", "");
                    Story story = new Story();
                    story.setTitle(title);
                    story.setUrl(s);
                    story.setImage(s + "/cover_image.jpg");
                    result.add(story);
                    reader.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
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
