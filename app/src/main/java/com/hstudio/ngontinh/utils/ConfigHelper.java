package com.hstudio.ngontinh.utils;

import android.content.Context;

import com.hstudio.ngontinh.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by phhien on 8/2/2016.
 */
public class ConfigHelper {

    private static JSONObject jsonObject = null;

    public static JSONObject getJsonObject(Context context) {
        try {
            if (jsonObject == null) {
                loadConfig(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static void loadConfig(Context context) throws Exception {
        InputStream is = context.getResources().openRawResource(R.raw.config);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        jsonObject = new JSONObject(writer.toString());
    }
}
