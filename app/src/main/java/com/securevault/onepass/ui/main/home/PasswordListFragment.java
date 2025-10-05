package com.securevault.onepass.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.securevault.onepass.data.DatabaseHelper;
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

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(requireContext());
        allPasswordItems = (ArrayList<PasswordItem>) databaseHelper.passwordDao().retrieveRecord();
        resetDataSet();

        if (getParentFragment() != null) {
            ((HomeFragment) getParentFragment()).changePasswordStoredNumber(allPasswordItems.size());
        }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}