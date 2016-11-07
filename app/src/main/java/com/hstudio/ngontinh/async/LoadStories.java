package com.hstudio.ngontinh.async;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.hstudio.ngontinh.NewsFragment;
import com.hstudio.ngontinh.object.Story;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
        try {
//            Document doc = Jsoup.connect(url).get();
//            Elements news = doc.select("div[itemtype=\"http://schema.org/Book\"]");
//            for(Element element: news) {
//                Story story = new Story();
//                Elements a = element.select("a[href]").not("[itemprop=\"genre\"]");
//                Elements img = element.select("div[data-image]");
//                story.setTitle(a.get(0).text());
//                story.setImage(img.attr("data-image"));
//                story.setUrl(a.get(0).attr("href"));
//                story.setAuthor(element.select(".author").text());
//                result.add(story);
//            }
            AssetManager am = mFragment.getActivity().getAssets();

            String [] aplist = am.list("");
            for(String s : aplist) {
                if(!s.contains(".") && s.contains("-")) {
                    Story story = new Story();
                    story.setTitle(s);
                    story.setUrl(s);
                    story.setImage(s + "/cover_image.jpg");
                    result.add(story);
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
