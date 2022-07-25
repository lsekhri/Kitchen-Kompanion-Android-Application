package com.example.kitchenkompanionv1.groceries;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kitchenkompanionv1.R;

import com.example.kitchenkompanionv1.databinding.FridgeBinding;
import com.example.kitchenkompanionv1.databinding.GroceryListBinding;

public class groceryList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ImageView addButton;
    private ImageView backButton;
    private List<Grocery> groceryList;
    protected GroceryListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GroceryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.grocery_list);
        recyclerView = binding.recyclerView;//findViewById(R.id.recyclerView);

        addButton = binding.addButton;//findViewById(R.id.addButton);
        backButton = binding.back;//findViewById(R.id.back);
        groceryList = new ArrayList<>();


        adapter = new MyAdapter(groceryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(view -> showDialog());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.add_grocerylist,null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        view.findViewById(R.id.cancel).setOnClickListener(view12 -> dialog.dismiss());

        view.findViewById(R.id.done).setOnClickListener(view1 -> {
            final EditText quantity = view.findViewById(R.id.editQuantity);
            final EditText name = view.findViewById(R.id.editName);
            Grocery grocery = new Grocery(Integer.parseInt(quantity.getText().toString()),
                    name.getText().toString());
            adapter.addItem(grocery);
            dialog.dismiss();
        });

        dialog.show();
    }
}