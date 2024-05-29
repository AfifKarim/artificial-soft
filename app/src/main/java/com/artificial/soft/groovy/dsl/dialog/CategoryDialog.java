package com.artificial.soft.groovy.dsl.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artificial.soft.groovy.dsl.R;
import com.artificial.soft.groovy.dsl.database.entity.CategoryEntity;
import com.artificial.soft.groovy.dsl.viewModel.CategoryViewModel;
import com.artificial.soft.groovy.dsl.viewModel.ProductViewModel;

public class CategoryDialog extends AppCompatDialog {

    private CategoryViewModel categoryViewModel;
    private ProductViewModel productViewModel;
    private Context context;
    private RecyclerView recyclerView;
    private OnCategorySelectedListener categorySelectedListener;

    public CategoryDialog(@NonNull Context context, CategoryViewModel categoryViewModel, ProductViewModel productViewModel) {
        super(context);
        this.context = context;
        this.categoryViewModel = categoryViewModel;
        this.productViewModel = productViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_category, null);
        setContentView(view);

        Toolbar toolbar = view.findViewById(R.id.dialogToolbar);
        toolbar.setTitle("Categories");
        toolbar.setNavigationOnClickListener(v -> dismiss());

        recyclerView = view.findViewById(R.id.categoryListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        categoryViewModel.getCategories().observe((LifecycleOwner) context, categories -> {
            if (categories != null) {
                CategoryAdapter adapter = new CategoryAdapter(context, categories, new CategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, CategoryEntity category) {
                        if (categorySelectedListener != null) {
                            categorySelectedListener.onCategorySelected(category);
                        }
                        dismiss();
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void setOnCategorySelectedListener(OnCategorySelectedListener listener) {
        this.categorySelectedListener = listener;
    }

    public interface OnCategorySelectedListener {
        void onCategorySelected(CategoryEntity category);
    }
}