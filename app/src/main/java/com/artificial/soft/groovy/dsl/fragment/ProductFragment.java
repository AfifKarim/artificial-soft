package com.artificial.soft.groovy.dsl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artificial.soft.groovy.dsl.R;
import com.artificial.soft.groovy.dsl.database.DB;
import com.artificial.soft.groovy.dsl.database.entity.CartEntity;
import com.artificial.soft.groovy.dsl.database.entity.CategoryEntity;
import com.artificial.soft.groovy.dsl.database.entity.ProductEntity;
import com.artificial.soft.groovy.dsl.dialog.CategoryDialog;
import com.artificial.soft.groovy.dsl.utils.Global;
import com.artificial.soft.groovy.dsl.viewModel.CartViewModel;
import com.artificial.soft.groovy.dsl.viewModel.CategoryViewModel;
import com.artificial.soft.groovy.dsl.viewModel.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;
    private CartViewModel cartViewModel;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DB dB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);

        // Initialize Toolbar
        toolbar = rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        dB = DB.getInstance(requireContext());

        // Check if products LiveData is not null before observing
//        LiveData<List<ProductEntity>> productsLiveData = productViewModel.getProducts();
//        if (productsLiveData != null) {
//            productsLiveData.observe(getViewLifecycleOwner(), products -> {
//                if (products == null || products.isEmpty()) {
//                    Global.showDialog(getContext(), R.drawable.icons8_info_100px, "No Products Found", "Please select a category to view products.");
//                } else {
//                    adapter.setProducts(products);
//                }
//            });
//        }

        // Initialize FAB and set OnClickListener
        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            CategoryDialog dialog = new CategoryDialog(getContext(), categoryViewModel, productViewModel);
            dialog.setOnCategorySelectedListener(category -> {
                if (category != null) {
                    productViewModel.fetchProductsByCategory(category.getId(), category.getCategory_name());
                    productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
                        adapter.setProducts(products);
                    });
                } else {
                    Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        });



        return rootView;
    }

    private void setSupportActionBar(Toolbar toolbar) {
        // Check if the activity already has an action bar
        if (((AppCompatActivity) requireActivity()).getSupportActionBar() == null) {
            // Set the toolbar as the support action bar
            ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
            // Set title
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Products");
        }
    }

    // Adapter class to display products
    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
        private List<ProductEntity> products = new ArrayList<>();
        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            ProductEntity product = products.get(position);
            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public void setProducts(List<ProductEntity> products) {
            this.products = products;
            notifyDataSetChanged();
        }

        // ViewHolder class for product item
        public class ProductViewHolder extends RecyclerView.ViewHolder {
            private TextView productNameTextView;
            private TextView categoryTextView;
            private ImageButton addToCart;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                productNameTextView = itemView.findViewById(R.id.productNameTextView);
                categoryTextView = itemView.findViewById(R.id.categoryTextView);
                addToCart = itemView.findViewById(R.id.addToCart);

                addToCart.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ProductEntity product = products.get(position);

                        // Check if the product is already in the cart
                        cartViewModel.getCartByProductId(product.getId()).observe(getViewLifecycleOwner(), existingCartItem -> {
                            if (existingCartItem == null) {
                                // Product is not in the cart, add it
                                CartEntity cartItem = new CartEntity();
                                cartItem.setCart_product_id(product.getId());
                                cartItem.setCart_product_name(product.getProduct_name());
                                cartItem.setCart_category_id(product.getCategory_id());
                                cartItem.setCart_category_name(product.getCategory_name());
                                cartItem.setCart_status(1); // Assuming 1 means active/added
                                cartViewModel.insert(cartItem);
                                Toast.makeText(getContext(), product.getProduct_name() + " added to cart", Toast.LENGTH_SHORT).show();
                            } else {
                                // Product is already in the cart, show an alert
                                Toast.makeText(getContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

            }

            public void bind(ProductEntity product) {
                productNameTextView.setText(product.getProduct_name());
                categoryTextView.setText("Category Name: " + product.getCategory_name());
            }
        }
    }
}