package com.artificial.soft.groovy.dsl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.artificial.soft.groovy.dsl.utils.Global;
import com.artificial.soft.groovy.dsl.viewModel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartViewModel cartViewModel;
    private Toolbar toolbar;
    private DB dB;
    private CartAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize Toolbar
        toolbar = rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        recyclerView = rootView.findViewById(R.id.cartrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CartAdapter();
        recyclerView.setAdapter(adapter);


        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        dB = DB.getInstance(requireContext());

        // Observe products LiveData
        LiveData<List<CartEntity>> productsLiveData = cartViewModel.getAllCarts();
        if (productsLiveData != null) {
            productsLiveData.observe(getViewLifecycleOwner(), products -> {
                if (products == null || products.isEmpty()) {
                    Toast.makeText(getContext(), "Please Add Product To Cart", Toast.LENGTH_SHORT).show();
                    adapter.clearProducts();
                } else {
                    adapter.setProducts(products);
                }
            });
        }

        return rootView;
    }

    private void setSupportActionBar(Toolbar toolbar) {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        if (activity.getSupportActionBar() == null) {
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle("Cart");
            }
        }
    }

    // Adapter class to display products
    private class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
        private List<CartEntity> cart = new ArrayList<>();

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
            return new CartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            CartEntity cartItem = cart.get(position);
            holder.bind(cartItem);
        }

        @Override
        public int getItemCount() {
            return cart.size();
        }

        public void setProducts(List<CartEntity> cart) {
            this.cart = cart;
            notifyDataSetChanged();
        }

        public void clearProducts() {
            this.cart.clear();
            notifyDataSetChanged();
        }

        // ViewHolder class for product item
        public class CartViewHolder extends RecyclerView.ViewHolder {
            private TextView productNameTextView;
            private TextView categoryTextView;
            private ImageButton addToCart;

            public CartViewHolder(@NonNull View itemView) {
                super(itemView);
                productNameTextView = itemView.findViewById(R.id.cartproductNameTV);
                categoryTextView = itemView.findViewById(R.id.cartcategoryTV);
                addToCart = itemView.findViewById(R.id.deleteCart);

                addToCart.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        CartEntity cartItem = cart.get(position);
                        cartViewModel.delete(cartItem);
                        Toast.makeText(getContext(), cartItem.getCart_product_name() + " deleted from cart", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public void bind(CartEntity cart) {
                productNameTextView.setText(cart.getCart_product_name());
                categoryTextView.setText("Category Name: " + cart.getCart_category_name());
            }
        }
    }
}