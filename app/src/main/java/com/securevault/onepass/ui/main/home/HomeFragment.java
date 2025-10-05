package com.securevault.onepass.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.securevault.onepass.R;
import com.securevault.onepass.databinding.FragmentHomeBinding;
import com.securevault.onepass.utils.SearchViewHelper;

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

        binding.searchView.clearFocus();
        SearchViewHelper.setStrokeColorByTextListener(requireContext(), binding.searchView);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = getChildFragmentManager().findFragmentById(R.id.frameLayout);
                if (fragment instanceof PasswordListFragment) {
                    ((PasswordListFragment) fragment).filterList(newText.trim());
                } else if (fragment instanceof NoPasswordFoundFragment && newText.trim().isEmpty()) {
                    loadFragment(new PasswordListFragment());
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        showPasswordList();
    }

    public void showPasswordList() {
        loadFragment(new PasswordListFragment());
    }

    public void showNoPasswordFound(boolean flag) {
        if (flag) {
            loadFragment(NoPasswordFoundFragment.getInstance(requireContext()));
        } else {
            loadFragment(new NoPasswordFoundFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commitNow();
    }

    public void changePasswordStoredNumber(int size) {
        binding.passwordStoreNumber.setText(String.valueOf(size));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}