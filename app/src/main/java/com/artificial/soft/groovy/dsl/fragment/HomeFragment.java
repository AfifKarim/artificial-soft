package com.artificial.soft.groovy.dsl.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.artificial.soft.groovy.dsl.R;
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize UI components
        Button productBtn = rootView.findViewById(R.id.productBtn);
        Button cartBtn = rootView.findViewById(R.id.cartBtn);

        // Set onClickListeners
        productBtn.setOnClickListener(view -> {
            // Navigate to ProductFragment
            Navigation.findNavController(view).navigate(R.id.productFragment);
        });

        cartBtn.setOnClickListener(view -> {
            // Handle cart button click
            Navigation.findNavController(view).navigate(R.id.cartFragment);
        });

        return rootView;
    }
}