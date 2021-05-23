package com.example.newversion;

public class product_item_container {
    private String mtxtprice;
    private String mTextName;
    private String mTextqty;
    public product_item_container(String price, String textname, String textqty) {
        mtxtprice = price;
        mTextName = textname;
        mTextqty = textqty;
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
