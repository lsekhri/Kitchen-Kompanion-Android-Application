package com.example.kitchenkompanionv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitchenkompanionv1.databinding.UserBinding;

public class User extends AppCompatActivity {
    protected UserBinding binding;
    protected TextView data[][];
    public boolean IS_SETUP = false, HELPED = false;

    public void setup() {
        if(!IS_SETUP) {
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            editor.putString("ROW1","Apples,5,15,Red Delicious,1,1,0\n");
            editor.putString("ROW2","Eggs,10,12,None,0,0,0\n");
            editor.apply();
            IS_SETUP = true;
            //popup();
        }
    }

    public void refresh(){
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        for(int i = 0; i < 6; i++) {
            String key = "ROW" + Integer.toString(i+1);
            String rowdata[] = sh.getString(key, "").split(","), hover = "";
            for(int j = 0; j < 3; j++)
                data[i][j].setText(j < rowdata.length ? rowdata[j] : "");
            for(int j = 3; j < 7; j++) {
                if(j < rowdata.length)
                    hover += rowdata[j];
            }
            for(String s:rowdata)
                System.out.println(s);
            data[i][0].setTooltipText("Description: " + (3 < rowdata.length ? rowdata[3] : "None") + "" +
                    "\nShared With: " + (4 < rowdata.length && rowdata[4].equals("1") ? "Abishek, " : "") +
                    (5 < rowdata.length && rowdata[5].equals("1") ? "Laksh, " : "") +
                    (6 < rowdata.length && rowdata[6].equals("1") ? "Zach" : ""));
        }
    }

    public void popup() {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.help, null);
        View user = inflater.inflate(R.layout.user, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(user, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //popup();

        final ImageView add = binding.add;
        add.setOnClickListener(new View.OnClickListener() {
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

        final Button adds[] = {binding.add1, binding.add2, binding.add3, binding.add4, binding.add5,
                binding.add6};
        for(int i=0; i<6; i++) {
            int finalI = i;
            adds[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    String rowdata[] = sh.getString("ROW"+(finalI +1), "").split(",");
                    if(rowdata.length >= 2) {
                        Integer newval = Integer.valueOf(rowdata[1]) + 1;
                        rowdata[1] = newval.toString();
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString("ROW"+(finalI+1), String.join(",", rowdata));
                        editor.apply();
                        refresh();
                    }
                }
            });
        }

        final Button minus[] = {binding.minus1, binding.minus2, binding.minus3, binding.minus4,
                binding.minus5, binding.minus6};
        for(int i=0; i<6; i++) {
            int finalI = i;
            minus[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    String rowdata[] = sh.getString("ROW" + (finalI + 1), "").split(",");
                    if (rowdata.length >= 2) {
                        Integer newval = Integer.valueOf(rowdata[1]) - 1;
                        if (newval > 0) {
                            rowdata[1] = newval.toString();
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putString("ROW" + (finalI + 1), String.join(",", rowdata));
                            editor.apply();
                            refresh();
                        }
                    }
                }
            });
        }

        final Button edits[] = {binding.delete1, binding.delete2, binding.delete3, binding.delete4,
                binding.delete5, binding.delete6};
        for(int i=0; i<6; i++) {
            int finalI = i;
            edits[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView name = data[finalI][0], quan = data[finalI][1], expiry = data[finalI][2];
                    name.setFocusable(true);
                    name.setEnabled(true);
                    name.setClickable(true);
                    name.setFocusableInTouchMode(true);
                    quan.setFocusable(true);
                    quan.setEnabled(true);
                    quan.setClickable(true);
                    quan.setFocusableInTouchMode(true);
                    expiry.setFocusable(true);
                    expiry.setEnabled(true);
                    expiry.setClickable(true);
                    expiry.setFocusableInTouchMode(true);
                    edits[finalI].setVisibility(View.INVISIBLE);
                }
            });

            edits[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    int b = 0;
                    for(int j=finalI; j<6; j++) {
                        String next = sh.getString("ROW"+(j+2), "");
                        //System.out.println(j + " : " + next);
                        if(next.isEmpty()) {
                            b = j+1;
                            break;
                        }
                        else {
                            editor.putString("ROW"+(j+1), next);
                            editor.apply();
                        }
                    }
                    editor.putString("ROW"+b, "");
                    editor.apply();
                    for(int a=0; a<6; a++)
                        System.out.println("ROW"+(a+1)+": "+sh.getString("ROW"+(a+1), ""));
                    refresh();
                    return true;
                }
            });
        }

        final Button save = binding.save;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                for(int i=0; i<6; i++) {
                    String toWrite[] = {data[i][0].getText().toString(),
                            data[i][1].getText().toString(), data[i][2].getText().toString()};
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("ROW"+(i+1), String.join(",", toWrite));
                    editor.apply();
                    for(int j=0; j<3; j++) {
                        data[i][j].setFocusable(false);
                        data[i][j].setEnabled(false);
                        data[i][j].setClickable(false);
                        data[i][j].setFocusableInTouchMode(false);
                    }
                    edits[i].setVisibility(View.VISIBLE);
                }
            }
        });

        final ImageView a = binding.edit;
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
            }
        });

        data = new TextView[6][6];
        data[0][0] = binding.food1;
        data[0][1] = binding.quan1;
        data[0][2] = binding.expiry1;
        data[1][0] = binding.food2;
        data[1][1] = binding.quan2;
        data[1][2] = binding.expiry2;
        data[2][0] = binding.food3;
        data[2][1] = binding.quan3;
        data[2][2] = binding.expiry3;
        data[3][0] = binding.food4;
        data[3][1] = binding.quan4;
        data[3][2] = binding.expiry4;
        data[4][0] = binding.food5;
        data[4][1] = binding.quan5;
        data[4][2] = binding.expiry5;
        data[5][0] = binding.food6;
        data[5][1] = binding.quan6;
        data[5][2] = binding.expiry6;
        setup();
        refresh();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, AddFood.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

}
