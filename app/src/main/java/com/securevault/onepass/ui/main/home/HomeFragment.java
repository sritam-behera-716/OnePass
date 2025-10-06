package com.securevault.onepass.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.securevault.onepass.R;
import com.securevault.onepass.data.DatabaseHelper;
import com.securevault.onepass.data.PasswordItem;
import com.securevault.onepass.databinding.FragmentHomeBinding;
import com.securevault.onepass.utils.SearchViewHelper;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<PasswordItem> allPasswordItems;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(requireContext());
        allPasswordItems = (ArrayList<PasswordItem>) databaseHelper.passwordDao().retrieveRecord();

        setUpSearchView();
        showPasswordItems();
    }

    private void setUpSearchView() {
        binding.searchView.clearFocus();
        SearchViewHelper.setStrokeColorByTextListener(requireContext(), binding.searchView);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText.trim());
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }

    public void filterList(String text) {
        if (text.isEmpty()) {
            showPasswordItems();
            return;
        }

        ArrayList<PasswordItem> filteredList = new ArrayList<>();
        for (PasswordItem item : allPasswordItems) {
            if (item.getPasswordName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            showNoResult(false);
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    private void showPasswordItems() {
        if (allPasswordItems.isEmpty()) {
            showNoResult(true);
            return;
        }

        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.searchIllustration.setVisibility(View.GONE);
        binding.title.setVisibility(View.GONE);
        binding.description.setVisibility(View.GONE);

        binding.passwordStoreNumber.setText(String.valueOf(allPasswordItems.size()));
        adapter = new RecyclerViewAdapter(requireContext(), allPasswordItems);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    private void showNoResult(boolean flag) {
        if (flag) {
            binding.title.setText(R.string.no_passwords);
            binding.description.setText(R.string.no_passwords_stored);
        }

        binding.recyclerView.setVisibility(View.GONE);
        binding.searchIllustration.setVisibility(View.VISIBLE);
        binding.title.setVisibility(View.VISIBLE);
        binding.description.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}