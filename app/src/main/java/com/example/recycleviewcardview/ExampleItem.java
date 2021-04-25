package com.example.recycleviewcardview;

public class ExampleItem {
    private String mImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(String ImageResource,String text1,String text2){
        mImageResource = ImageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public String getmImageResource() {
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
