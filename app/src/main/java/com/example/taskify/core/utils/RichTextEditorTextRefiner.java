package com.example.taskify.core.utils;

import android.text.Html;

public class RichTextEditorTextRefiner {
    public static String fromHtml(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }

    public static String addLineBreaks(String text) {
        return text.replaceAll("-", "\n");
    }
}
