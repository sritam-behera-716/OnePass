package com.securevault.onepass.ui.main.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.securevault.onepass.R;
import com.securevault.onepass.databinding.FragmentNoPasswordFoundBinding;

public class NoPasswordFoundFragment extends Fragment {
    private static final String ARG1 = "title";
    private static final String ARG2 = "description";
    private FragmentNoPasswordFoundBinding binding;

    public static NoPasswordFoundFragment getInstance(Context context) {
        NoPasswordFoundFragment fragment = new NoPasswordFoundFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG1, context.getString(R.string.no_passwords));
        bundle.putString(ARG2, context.getString(R.string.no_passwords_stored));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoPasswordFoundBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String title = getArguments().getString(ARG1);
            String description = getArguments().getString(ARG2);

            binding.title.setText(title);
            binding.description.setText(description);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}