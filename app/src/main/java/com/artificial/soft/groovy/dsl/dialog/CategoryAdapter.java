package com.artificial.soft.groovy.dsl.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.artificial.soft.groovy.dsl.R;
import com.artificial.soft.groovy.dsl.database.entity.CategoryEntity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context context;
    private final List<CategoryEntity> categories;
    private final OnItemClickListener listener;
    private int selectedItem = RecyclerView.NO_POSITION;

    public CategoryAdapter(Context context, List<CategoryEntity> categories, OnItemClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryEntity category = categories.get(position);
        holder.bind(category, position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setSelectedItem(int position) {
        int previousSelectedItem = selectedItem;
        selectedItem = position;
        notifyItemChanged(previousSelectedItem); // Update previous selected item
        notifyItemChanged(selectedItem); // Update new selected item
    }

    public interface OnItemClickListener {
        void onItemClick(int position, CategoryEntity category);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
        }

        void bind(CategoryEntity category, int position) {
            categoryName.setText(category.getCategory_name());

            // Update background color based on the selected state
//            if (selectedItem == position) {
//                categoryName.setBackgroundResource(R.drawable.category_button_bg_selected);
//            } else {
//                categoryName.setBackgroundResource(R.drawable.category_button_bg);
//            }

            categoryName.setOnClickListener(v -> {
                int previousSelectedItem = selectedItem;
                listener.onItemClick(position, category);
                setSelectedItem(position); // Update selected item position
                notifyItemChanged(previousSelectedItem); // Update previous selected item
            });
        }
    }
}