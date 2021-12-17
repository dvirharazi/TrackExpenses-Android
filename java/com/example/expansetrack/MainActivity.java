package com.example.expansetrack;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rateBtn = findViewById(R.id.rate_us);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , RateUsAndExit.class);
                startActivity(intent);
            }
        });

        Button enter = findViewById(R.id.enter_app);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DefineExpenses.class);
                startActivity(intent);
            }
        });

        Button instruction_btn = findViewById(R.id.instruction);
        instruction_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(MainActivity.this);
                LinearLayout dialogImgLayout = new LinearLayout(MainActivity.this);
                dialogImgLayout.setGravity(1);
                dialogImgLayout.addView(imageView);
                imageView.setImageResource(R.drawable.screenshot);
                LinearLayout.LayoutParams imgParam = new LinearLayout.LayoutParams(650,650);
                imageView.setLayoutParams(imgParam);

                new AlertDialog.Builder(MainActivity.this)

                        .setTitle(getResources().getString(R.string.dialogTitle))
                        .setMessage(getResources().getString(R.string.dialogtext))

                        .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(dialogImgLayout)
                        .show();
            }
        });

    }
}