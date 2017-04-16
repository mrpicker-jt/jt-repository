package com.gproject.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;


public class TextProperty {

    private int height;      //读入文本的行数
    private String[] context = new String[1024];    //存储读入的文本

    public TextProperty(String str) throws Exception {
        TextProperty(str.length(), new InputStreamReader(new ByteArrayInputStream(str.getBytes("UTF-8"))));
    }

    public void TextProperty(int wordNum, InputStreamReader in) throws Exception {
        int i = 0;
        BufferedReader br = new BufferedReader(in);
        String s;
        while ((s = br.readLine()) != null) {
            if (s.length() > wordNum) {
                int k = 0;
                while (k + wordNum <= s.length()) {
                    context[i++] = s.substring(k, k + wordNum);
                    k = k + wordNum;
                }
                context[i++] = s.substring(k, s.length());
            } else {
                context[i++] = s;
            }
        }
        this.height = i;
        in.close();
        br.close();
    }


    public int getHeight() {
        return height;
    }

    public String[] getContext() {
        return context;
    }


}
