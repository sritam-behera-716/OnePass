package com.securevault.onepass.ui.onboarding;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.securevault.onepass.R;
import com.securevault.onepass.data.ScreenItem;
import com.securevault.onepass.databinding.ActivityOnboardingBinding;
import com.securevault.onepass.ui.main.MainActivity;
import com.securevault.onepass.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.Objects;

public class OnboardingActivity extends AppCompatActivity {
    private ActivityOnboardingBinding binding;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<ScreenItem> screenItemList = new ArrayList<>();
        screenItemList.add(getScreenItem(0));
        screenItemList.add(getScreenItem(1));
        screenItemList.add(getScreenItem(2));
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, screenItemList);
        binding.screenViewPager.setAdapter(adapter);

        setupTabLayoutWithCustomViews();
        binding.screenViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == screenItemList.size() - 1) {
                    binding.nextButton.setText(getResources().getString(R.string.finish));
                } else {
                    binding.nextButton.setText(getResources().getString(R.string.next));
                }
            }
        });

        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(this);
        binding.skipButton.setOnClickListener(v -> {
            preferenceHelper.setIntroOpenInformation(PreferenceHelper.KEY_INTRO_OPEN, true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        binding.nextButton.setOnClickListener(v -> {
            currentPosition = binding.screenViewPager.getCurrentItem();
            if (currentPosition < screenItemList.size()) {
                currentPosition++;
                binding.screenViewPager.setCurrentItem(currentPosition);
            }

            if (currentPosition == screenItemList.size()) {
                preferenceHelper.setIntroOpenInformation(PreferenceHelper.KEY_INTRO_OPEN, true);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    @SuppressLint("InflateParams")
    private void setupTabLayoutWithCustomViews() {
        binding.tabIndicator.setBackground(null);
        binding.tabIndicator.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        String[] tabNumbers = getResources().getStringArray(R.array.tab_titles);

        new TabLayoutMediator(binding.tabIndicator, binding.screenViewPager,
                (tab, position) -> {
                    View customView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
                    TextView tabTextView = customView.findViewById(R.id.tabTextView);
                    tabTextView.setText(tabNumbers[position]);
                    tab.setCustomView(customView);
                }).attach();

        binding.tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabAppearance(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabAppearance(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                updateTabAppearance(tab, true);
            }
        });

        if (binding.tabIndicator.getTabCount() > 0) {
            updateTabAppearance(Objects.requireNonNull(binding.tabIndicator.getTabAt(0)), true);
        }
    }

    private void updateTabAppearance(TabLayout.Tab tab, boolean isSelected) {
        View customView = tab.getCustomView();
        if (customView != null) {
            TextView tabTextView = customView.findViewById(R.id.tabTextView);
            if (isSelected) {
                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                tabTextView.setTextColor(getColor(R.color.default_color));
            } else {
                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tabTextView.setTextColor(getColor(R.color.text_color_gray));
            }
        }
    }

    private ScreenItem getScreenItem(int id) {
        SpannableString screenTitle = null;
        String screenDescription = null;

        switch (id) {
            case 0:
                screenTitle = getSpannableString(id, getResources().getString(R.string.title_1));
                screenDescription = getResources().getString(R.string.description_1);
                break;
            case 1:
                screenTitle = getSpannableString(id, getResources().getString(R.string.title_2));
                screenDescription = getResources().getString(R.string.description_2);
                break;
            case 2:
                screenTitle = getSpannableString(id, getResources().getString(R.string.title_3));
                screenDescription = getResources().getString(R.string.description_3);
        }

        return new ScreenItem(id, screenTitle, screenDescription);
    }

    private SpannableString getSpannableString(int id, String title) {
        SpannableString spannableString = new SpannableString(title);
        switch (id) {
            case 0:
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.span_color)), 0, 8, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.default_color)), 9, 15, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.span_color)), 16, title.length(), 0);
                break;
            case 1:
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.span_color)), 0, 8, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.default_color)), 9, 18, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.span_color)), 19, title.length(), 0);
                break;
            case 2:
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.span_color)), 0, 11, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.default_color)), 12, 20, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.span_color)), 21, title.length(), 0);
                break;
        }
        return spannableString;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}