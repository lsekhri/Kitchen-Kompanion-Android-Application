package com.example.kitchenkompanionv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitchenkompanionv1.databinding.FridgeBinding;
import com.example.kitchenkompanionv1.groceries.groceryList;

public class VirtualFridge extends AppCompatActivity {
    protected FridgeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FridgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Button fridges = binding.user;

        fridges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
            }
        });

        final ImageView back = binding.back;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Button glist = binding.glist;
        glist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage2(view);
            }
        });
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }
    public void sendMessage2(View view) {
        Intent intent2 = new Intent(this, groceryList.class);
        startActivity(intent2);
    }
}
