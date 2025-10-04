package com.securevault.onepass.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;

public class SearchViewHelper {
    public static void setStrokeColorByTextListener(SearchView searchView) {
        int searchViewEditId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchViewEditText = searchView.findViewById(searchViewEditId);

        if (searchViewEditText != null) {
            searchViewEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchView.setActivated(s.length() > 0);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }
}
