package com.securevault.onepass.ui.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.securevault.onepass.R;
import com.securevault.onepass.data.PasswordItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<PasswordItem> passwordItemList;

    public RecyclerViewAdapter(Context context, ArrayList<PasswordItem> passwordItemList) {
        this.context = context;
        this.passwordItemList = passwordItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PasswordItem passwordItem = passwordItemList.get(position);

        holder.passwordFont.setText(String.valueOf(passwordItem.getPasswordName().charAt(0)));
        holder.passwordName.setText(passwordItem.getPasswordName());
        holder.copyIcon.setOnClickListener(v -> {
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.default_color));
            holder.passwordFont.setBackgroundResource(R.drawable.password_background_copied);
            holder.passwordFont.setTextColor(context.getColor(R.color.default_color));
            holder.passwordName.setText(R.string.copied);
            holder.passwordName.setTextColor(context.getColor(R.color.white));
            Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return passwordItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView passwordFont, passwordName;
        private final ImageView copyIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            passwordFont = itemView.findViewById(R.id.passwordFont);
            passwordName = itemView.findViewById(R.id.passwordName);
            copyIcon = itemView.findViewById(R.id.copyIcon);
        }
    }
}
