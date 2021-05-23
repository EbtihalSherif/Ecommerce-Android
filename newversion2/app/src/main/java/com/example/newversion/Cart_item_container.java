package com.example.newversion;

public class Cart_item_container {
    private String mtxtprice;
    private String mTextName;
    private String mTextqty;
    private int maxavaliable;

    public Cart_item_container(String price, String textname, String textqty,int maxQty) {
        mtxtprice = price;
        mTextName = textname;
        mTextqty = textqty;
        maxavaliable=maxQty;
    }
    public void changeText1(String text) {
        mTextqty = text;
    }

    public int getMaxavaliable() {
        return maxavaliable;
    }

    public String getmtxtprice() {
        return mtxtprice;
    }
    public String getTextName() {
        return mTextName;
    }
    public String getTextqty() {
        return mTextqty;
    }
}
