package com.hstudio.ngontinh.object;

import java.util.List;

/**
 * Created by phhien on 6/16/2016.
 */
public class StoryDetail {

    private String image;
    private String title;
    private String description;
    private List<ChapItem> chaps;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ChapItem> getChaps() {
        return chaps;
    }

    public void setChaps(List<ChapItem> chaps) {
        this.chaps = chaps;
    }
}
