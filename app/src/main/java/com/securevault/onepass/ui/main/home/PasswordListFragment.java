package com.securevault.onepass.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.securevault.onepass.data.PasswordItem;
import com.securevault.onepass.databinding.FragmentPasswordListBinding;

import java.util.ArrayList;

public class PasswordListFragment extends Fragment {
    private FragmentPasswordListBinding binding;
    private ArrayList<PasswordItem> allPasswordItems;
    private ArrayList<PasswordItem> passwordItemList;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPasswordListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allPasswordItems = new ArrayList<>();
        allPasswordItems.add(new PasswordItem(1, "Facebook"));
        allPasswordItems.add(new PasswordItem(2, "Microsoft"));
        allPasswordItems.add(new PasswordItem(3, "Google"));
        allPasswordItems.add(new PasswordItem(4, "Instagram"));
        allPasswordItems.add(new PasswordItem(5, "GitHub"));
        allPasswordItems.add(new PasswordItem(6, "Facebook"));
        allPasswordItems.add(new PasswordItem(7, "Microsoft"));
        allPasswordItems.add(new PasswordItem(8, "Google"));
        allPasswordItems.add(new PasswordItem(9, "Instagram"));
        allPasswordItems.add(new PasswordItem(10, "GitHub"));
        allPasswordItems.add(new PasswordItem(11, "Facebook"));
        allPasswordItems.add(new PasswordItem(12, "Microsoft"));
        allPasswordItems.add(new PasswordItem(13, "Google"));
        allPasswordItems.add(new PasswordItem(14, "Instagram"));
        allPasswordItems.add(new PasswordItem(15, "GitHub"));
        allPasswordItems.add(new PasswordItem(16, "Facebook"));
        allPasswordItems.add(new PasswordItem(17, "Microsoft"));
        allPasswordItems.add(new PasswordItem(18, "Google"));
        allPasswordItems.add(new PasswordItem(19, "Instagram"));
        allPasswordItems.add(new PasswordItem(20, "GitHub"));

        resetDataSet();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecyclerViewAdapter(requireContext(), passwordItemList);
        binding.recyclerView.setAdapter(adapter);
    }

    public void filterList(String text) {
        if (text.isEmpty()) {
            resetDataSet();
            return;
        }

        ArrayList<PasswordItem> filteredList = new ArrayList<>();
        for (PasswordItem item : allPasswordItems) {
            if (item.getPasswordName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            if (getParentFragment() != null) {
                ((HomeFragment) getParentFragment()).showNoPasswordFound(false);
            }
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    private void resetDataSet() {
        passwordItemList = new ArrayList<>(allPasswordItems);
    }
}