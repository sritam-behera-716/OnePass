package com.securevault.onepass.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.securevault.onepass.R;

public class EditTextHelper {
    @SuppressLint("ClickableViewAccessibility")
    public void setDrawableEndIcon(Context context, AppCompatEditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        final boolean[] isPasswordVisible = {false};

        Drawable visibilityOff = ContextCompat.getDrawable(context, R.drawable.ic_eye_close);
        Drawable visibilityOn = ContextCompat.getDrawable(context, R.drawable.ic_eye_open);

        setTintColor(visibilityOff, ContextCompat.getColor(context, R.color.text_color_gray));
        setTintColor(visibilityOn, ContextCompat.getColor(context, R.color.text_color_gray));

        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, visibilityOff, null);

        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {

                    Typeface currentTypeface = editText.getTypeface();
                    int cursorPosition = editText.getSelectionStart();

                    isPasswordVisible[0] = !isPasswordVisible[0];
                    if (isPasswordVisible[0]) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, visibilityOn, null);
                    } else {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, visibilityOff, null);
                    }

                    editText.setTypeface(currentTypeface);
                    editText.setSelection(cursorPosition);
                    return true;
                }
            }
            return false;
        });

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

                Drawable endDrawable = editText.getCompoundDrawables()[2];
                if (endDrawable != null) {
                    if (s.length() > 0) {
                        setTintColor(visibilityOff, ContextCompat.getColor(context, R.color.default_color));
                        setTintColor(visibilityOn, ContextCompat.getColor(context, R.color.default_color));
                    } else {
                        setTintColor(visibilityOff, ContextCompat.getColor(context, R.color.text_color_gray));
                        setTintColor(visibilityOn, ContextCompat.getColor(context, R.color.text_color_gray));
                    }
                }
            }
        });
    }

    private void setTintColor(Drawable drawable, int color) {
        if (drawable != null) {
            drawable.setTint(color);
        }
    }

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
