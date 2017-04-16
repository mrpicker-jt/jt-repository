package com.gproject.utils;

import android.content.Context;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.Locale;


public class TextUtils {
    public static String getStudyCountStr(int studyCount) {
        if (studyCount < 1e4) {
            return studyCount + "";
        } else if (studyCount < 1e6) {
            return String.format(Locale.CHINESE, "%.1f万", studyCount / 10000.f);
        } else {
            return String.format(Locale.CHINESE, "%.0f万", studyCount / 10000.f);
        }
    }

    public static void setTextColor(TextView textView, int colorId) {
        Context context = textView.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(context.getResources().getColor(colorId, context.getTheme()));
        } else {
            textView.setTextColor(context.getResources().getColor(colorId));
        }
    }

    public static void setTextSpanColor(TextView textView, String text, String colorfulText, int colorId) {
        if (!text.contains(colorfulText)) {
            System.out.println("要变色的文字不存在，将按默认配色");
            textView.setText(text);
        } else {
            int start = text.indexOf(colorfulText);
            int end = start + colorfulText.length();
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            int color = textView.getContext().getResources().getColor(colorId);
            builder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            textView.setText(builder);
        }
    }
}
