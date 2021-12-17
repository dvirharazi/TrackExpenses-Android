package com.example.expansetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DefineExpenses extends AppCompatActivity {


    int maxFood, maxClothing, maxHome, maxElectronics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_expenses);
        Button start_btn = findViewById(R.id.start);



        Button backToMenu = findViewById(R.id.back_to_manu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefineExpenses.this, MainActivity.class);
                startActivity(intent);
            }
        });



        EditText max_food = findViewById(R.id.max_food);
        EditText max_clothing = findViewById(R.id.max_clothing);
        EditText max_home = findViewById(R.id.max_home);
        EditText max_electronics = findViewById(R.id.max_electronics);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                start_btn.setEnabled((max_food.getText().toString()).length() > 0
                        && (max_clothing.getText().toString()).length() > 0
                        && (max_home.getText().toString()).length() > 0
                        && (max_electronics.getText().toString()).length() > 0);
                start_btn.setBackgroundResource(0);
                start_btn.setBackground(getResources().getDrawable(R.drawable.btns_shape));
                start_btn.setTextColor(Color.parseColor("#FFFFFFFF"));


            }
        };
        max_food.addTextChangedListener(textWatcher);
        max_clothing.addTextChangedListener(textWatcher);
        max_home.addTextChangedListener(textWatcher);
        max_electronics.addTextChangedListener(textWatcher);


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maxFood = Integer.parseInt(max_food.getText().toString());
                maxClothing = Integer.parseInt(max_clothing.getText().toString());
                maxHome = Integer.parseInt(max_home.getText().toString());
                maxElectronics = Integer.parseInt(max_electronics.getText().toString());

                Intent intent = new Intent(DefineExpenses.this, DetailsActivity.class);
                intent.putExtra("newMaxFood", maxFood);
                intent.putExtra("newMaxClothing", maxClothing);
                intent.putExtra("newMaxHome", maxHome);
                intent.putExtra("newMaxElectronics", maxElectronics);

                startActivity(intent);
            }
        });
    }

}