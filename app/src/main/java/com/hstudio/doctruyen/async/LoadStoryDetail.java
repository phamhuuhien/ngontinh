package com.hstudio.doctruyen.async;

import android.os.AsyncTask;

import com.hstudio.doctruyen.StoryActivity;
import com.hstudio.doctruyen.object.ChapItem;
import com.hstudio.doctruyen.object.Story;
import com.hstudio.doctruyen.object.StoryDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
        try {
            Document doc = Jsoup.connect(url).get();
            Elements img = doc.select("div.books img");
            storyDetail.setImage(img.attr("src"));
            Elements title = doc.select("h3.title");
            storyDetail.setTitle(title.text());
            Elements desc = doc.select("div.desc-text");
            storyDetail.setDescription(desc.toString());
            Elements els = doc.select("ul.list-chapter a[href]");
            List<ChapItem> chaps = new ArrayList<>();
            for(Element e : els) {
                ChapItem chapItem = new ChapItem();
                chapItem.setTitle(e.text().trim());
                System.out.println("e.text()="+e.text());
                chapItem.setLink(e.attr("href"));
                chaps.add(chapItem);
            }
            storyDetail.setChaps(chaps);
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
