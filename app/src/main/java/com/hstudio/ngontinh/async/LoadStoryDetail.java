package com.hstudio.ngontinh.async;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.hstudio.ngontinh.StoryActivity;
import com.hstudio.ngontinh.object.ChapItem;
import com.hstudio.ngontinh.object.StoryDetail;

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
 * Created by phhien on 6/16/2016.
 */
public class LoadStoryDetail extends AsyncTask<String, Integer, StoryDetail> {

    private StoryActivity mStoryActivity;

    public LoadStoryDetail(StoryActivity storyActivity) {
        mStoryActivity = storyActivity;
    }

    @Override
    protected StoryDetail doInBackground(String... strings) {
        String url = strings[0];
        StoryDetail storyDetail = new StoryDetail();
        BufferedReader reader = null;
        try {
            storyDetail.setImage(url + "/cover_image.jpg");

            String description = "";
            reader = new BufferedReader(
                    new InputStreamReader(mStoryActivity.getAssets().open(url + "/desc.txt")));
            String title = reader.readLine();
            title = title.replace("Title:<h1>", "");
            title = title.replace("</h1>", "");
            storyDetail.setTitle(title);
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                description += mLine;
                System.out.println("mLine="+ mLine);
            }
            storyDetail.setDescription(description);
            List<ChapItem> chaps = new ArrayList<>();
            AssetManager am = mStoryActivity.getAssets();
            String [] files = am.list(url);
            for(String file : files) {
                ChapItem chapItem = new ChapItem();
                chapItem.setTitle(file);
                chapItem.setLink(url + "/" + file);
                chaps.add(chapItem);
            }
            storyDetail.setChaps(chaps);
            reader.close();
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
