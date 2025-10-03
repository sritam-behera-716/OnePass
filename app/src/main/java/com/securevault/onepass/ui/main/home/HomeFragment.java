package com.securevault.onepass.ui.main.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.securevault.onepass.data.PasswordItem;
import com.securevault.onepass.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int searchViewEditId = binding.searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchViewEditText = binding.searchView.findViewById(searchViewEditId);
//        searchViewEditText.setBackground(null);
//        searchViewEditText.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        searchViewEditText.getBackground().clearColorFilter();
//        searchViewEditText.setPadding(0, 0, 0, 0);

        if (searchViewEditText != null) {
            searchViewEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    binding.searchView.setActivated(s.length() > 0);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        ArrayList<PasswordItem> passwordItemList = new ArrayList<>();
        passwordItemList.add(new PasswordItem("Facebook"));
        passwordItemList.add(new PasswordItem("Microsoft"));
        passwordItemList.add(new PasswordItem("Google"));
        passwordItemList.add(new PasswordItem("Instagram"));
        passwordItemList.add(new PasswordItem("GitHub"));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(requireContext(), passwordItemList);
        binding.recyclerView.setAdapter(adapter);
    }
}