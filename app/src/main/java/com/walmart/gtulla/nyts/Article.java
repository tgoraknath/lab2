package com.walmart.gtulla.nyts;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gtulla on 5/29/16.
 */
//@Parcel
public class Article implements Parcelable {

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
    private String webUrl;
    private String headLine;
    private String thumbNail = "";
    public Article() {
    }
    protected Article(Parcel in) {
        this.webUrl = in.readString();
        this.headLine = in.readString();
        this.thumbNail = in.readString();
    }
    public Article(JSONObject object) {
        try {
            webUrl =  object.getString("web_url") ;
            headLine = object.getJSONObject("headline").getString("main");
            JSONArray multimedia = object.getJSONArray("multimedia");
            if (multimedia.length() > 0) {
              JSONObject multimediaJson =   multimedia.getJSONObject(0);
                thumbNail =  "http://www.nytimes.com/"+multimediaJson.getString("url");
            }
        }catch(JSONException e) {
          e.printStackTrace();
        }
    }

    public static List<Article> getArticles(JSONArray articles) {
        List<Article> retList = new ArrayList<>();
        for (int i = 0; i < articles.length(); i++) {
            try {
                retList.add(new Article(articles.getJSONObject(i)));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return retList;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.webUrl);
        dest.writeString(this.headLine);
        dest.writeString(this.thumbNail);
    }

}
