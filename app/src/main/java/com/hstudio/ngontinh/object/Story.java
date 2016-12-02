package com.hstudio.ngontinh.object;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by phhien on 6/8/2016.
 */
@Setter
@Getter
public class Story {
    private int id;
    private String name;
    private String image;
    private String author;
    private String url;
    private String des;
}
