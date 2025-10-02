package com.securevault.onepass.ui.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.securevault.onepass.R;
import com.securevault.onepass.data.ScreenItem;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ScreenItem> screenItemList;

    public ViewPagerAdapter(Context context, ArrayList<ScreenItem> screenItemList) {
        this.context = context;
        this.screenItemList = screenItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_screen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.screenTitle.setText(screenItemList.get(position).getTitle());
        holder.screenDescription.setText(screenItemList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return screenItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView screenTitle, screenDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            screenTitle = itemView.findViewById(R.id.screenTitle);
            screenDescription = itemView.findViewById(R.id.screenDescription);
        }
    }
}