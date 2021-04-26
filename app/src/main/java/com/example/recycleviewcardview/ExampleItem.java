package com.example.recycleviewcardview;

public class ExampleItem {
    private String mImageResource;
    private String mId;
    private String mLikes;

    public ExampleItem(String ImageResource,String id,String likes){
        mImageResource = ImageResource;
        mId = id;
        mLikes = likes;
    }

    public String getmImageResource() {
        return mImageResource;
    }

    public String getmId() {
        return mId;
    }

    public String getmLikes() {
        return mLikes;
    }
}
