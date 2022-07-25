package com.example.kitchenkompanionv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kitchenkompanionv1.databinding.AddFoodBinding;
import com.example.kitchenkompanionv1.databinding.UserBinding;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AddFood extends AppCompatActivity {
    protected AddFoodBinding binding;
    protected UserBinding userBinding;

    private String boolStr(boolean b) {
        return b ? "1" : "0";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddFoodBinding.inflate(getLayoutInflater());
        userBinding = UserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ImageView submit = binding.done;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data[] = {binding.foodName.getText().toString(),
                        binding.foodQuan.getText().toString(), binding.foodExpiry.getText().toString(),
                        binding.foodDescription.getText().toString(), boolStr(binding.switch1.isChecked()),
                        boolStr(binding.switch2.isChecked()), boolStr(binding.switch3.isChecked())};
                for(int i=0; i<4; i++)
                    if(data[i].isEmpty())
                        data[i] = (i == 0 || i == 3) ? "None" : "0";
                String toWrite = String.join(",", data);
                SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();

                int rowWrite = 0;
                for(int i=0; i<6; i++) {
                    if(sh.getString("ROW"+(i+1), "").isEmpty()) {
                        rowWrite = i+1;
                        break;
                    }
                }

                editor.putString("ROW"+rowWrite, toWrite);
                editor.apply();
                finish();
            }
        });

        final ImageView cancel = binding.cancel;
        cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                finish();
                return true;
            }
        });
    }
}