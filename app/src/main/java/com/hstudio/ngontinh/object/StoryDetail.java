package com.hstudio.ngontinh.object;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by phhien on 6/16/2016.
 */
@Setter
@Getter
public class StoryDetail {

    private Story story;
    private String type;
    private String description;
    private List<ChapItem> chaps;
}
