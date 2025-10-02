package com.securevault.onepass.data;

import android.text.SpannableString;

public class ScreenItem {
    private int id;
    private SpannableString title;
    private String description;

    public ScreenItem(int id, SpannableString title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public SpannableString getTitle() {
        return title;
    }

    public void setTitle(SpannableString title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
