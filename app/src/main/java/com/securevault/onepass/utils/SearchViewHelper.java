package com.securevault.onepass.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;

import com.securevault.onepass.R;

public class SearchViewHelper {
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void setStrokeColorByTextListener(Context context, SearchView searchView) {
        int searchViewEditId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchViewEditText = searchView.findViewById(searchViewEditId);
        searchViewEditText.setTextCursorDrawable(context.getDrawable(R.drawable.edittext_cursor));

        searchViewEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchView.setActivated(true);
                } else {
                    searchView.clearFocus();
                    searchView.setActivated(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
