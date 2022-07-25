package com.example.kitchenkompanionv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitchenkompanionv1.databinding.MainmenuBinding;

public class MainMenu extends AppCompatActivity {
    protected MainmenuBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainmenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Button fridges = binding.fridges;

        fridges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
            }
        });
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, VirtualFridge.class);
        startActivity(intent);
    }
}
