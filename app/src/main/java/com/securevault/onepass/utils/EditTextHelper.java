package com.securevault.onepass.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatEditText;

public class EditTextHelper {
    public void setStrokeColorByTyping(AppCompatEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateStrokeColor(editText, editText.hasFocus());
            }
        });
    }

    private void updateStrokeColor(AppCompatEditText editText, boolean hasFocus) {
        int strokeColor;

        if (hasFocus && editText.getText() != null && editText.getText().length() > 0) {
            strokeColor = Color.parseColor("#FF6464");
        } else {
            strokeColor = Color.parseColor("#BABABA");
        }

        if (editText.getBackground() instanceof GradientDrawable) {
            GradientDrawable background = (GradientDrawable) editText.getBackground();
            background.setStroke(5, strokeColor);
        }
    }
}
