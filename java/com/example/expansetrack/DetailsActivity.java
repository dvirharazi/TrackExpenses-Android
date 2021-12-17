package com.example.expansetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressBar pbFood, pbClothing, pbHome, pbElectronics;
    int maxFood, maxClothing, maxHome, maxElectronics;
    int sumFood, sumClothing, sumHome, sumElectronics;
    EditText expanseAmountTxt;
    LinearLayout expansesLayout;
    static boolean isCreditBool = false;
    static boolean isCheckBool = false;
    volatile boolean isOpen = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        LinearLayout progressLayout = findViewById(R.id.progress_layout);
        LinearLayout expanses_list = findViewById(R.id.expanses_list);

        Button start_over = findViewById(R.id.start_over);

        ImageView eyeBtn = findViewById(R.id.eyeBtn);
        expanseAmountTxt = findViewById(R.id.expanse_price);
        expansesLayout = findViewById(R.id.expanse_layout);
        Button add_btn = findViewById(R.id.add_expanse);

        CheckBox checkbox_credit = findViewById(R.id.checkbox_credit);
        CheckBox checkbox_cash = findViewById(R.id.checkbox_cash);
        ImageView cashView = findViewById(R.id.cash_img);
        ImageView creditView = findViewById(R.id.credit_img);

        checkbox_cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cashView.setVisibility(View.VISIBLE);
                    if (checkbox_credit.isChecked()) {
                        checkbox_credit.setChecked(false);
                        creditView.setVisibility(View.INVISIBLE);
                        cashView.setVisibility(View.VISIBLE);
                        isCreditBool = checkbox_credit.isChecked();
                    }
                    isCheckBool = true;
                } else {
                    cashView.setVisibility(View.INVISIBLE);
                    isCheckBool = false;
                }
                add_btn.setEnabled(isChecked && (expanseAmountTxt.getText().toString()).length() > 0);

            }
        });

        checkbox_credit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    creditView.setVisibility(View.VISIBLE);
                    if (checkbox_cash.isChecked()) {
                        checkbox_cash.setChecked(false);
                        cashView.setVisibility(View.INVISIBLE);
                        creditView.setVisibility(View.VISIBLE);
                        isCreditBool = checkbox_credit.isChecked();
                    }
                    isCheckBool = true;
                } else {
                    creditView.setVisibility(View.INVISIBLE);
                    isCheckBool = false;
                }
                add_btn.setEnabled(isChecked && (expanseAmountTxt.getText().toString()).length() > 0);

            }
        });




        Switch aSwitch = findViewById(R.id.switch_progress);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    progressLayout.setVisibility(View.VISIBLE);
                    //expanses_list.getLayoutParams().height = (int) getResources().getDimension(R.dimen.sm_value);
                    eyeBtn.setPressed(true);
                    eyeBtn.setImageResource(R.drawable.open);
                }
                else{
                    progressLayout.setVisibility(View.GONE);
                    //expanses_list.getLayoutParams().height = (int) getResources().getDimension(R.dimen.xl_value);
                    eyeBtn.setPressed(false);
                    eyeBtn.setImageResource(R.drawable.close);
                }
                //expanses_list.requestLayout();
            }
        });

        start_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Intent newMaxSettingIntent = getIntent();
        Bundle b = newMaxSettingIntent.getExtras();

        
        maxFood = (int) b.get("newMaxFood");
        maxClothing = (int) b.get("newMaxClothing");
        maxHome = (int) b.get("newMaxHome");
        maxElectronics = (int) b.get("newMaxElectronics");

        pbFood = findViewById(R.id.foodPB);
        pbFood.setMax(maxFood);

        pbClothing = findViewById(R.id.clothingPB);
        pbClothing.setMax(maxClothing);

        pbHome = findViewById(R.id.homePB);
        pbHome.setMax(maxHome);

        pbElectronics = findViewById(R.id.electronicsPB);
        pbElectronics.setMax(maxElectronics);


        Spinner spin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);



        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                add_btn.setEnabled((expanseAmountTxt.getText().toString()).length() > 0
                        && (isCheckBool));
            }
        };
        expanseAmountTxt.addTextChangedListener(textWatcher);

        add_btn.setEnabled((expanseAmountTxt.getText().toString()).length() > 0);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String expanseAmount = expanseAmountTxt.getText().toString();
                String expanseName = spin.getSelectedItem().toString();


                switch (expanseName) {
                    case "Food":
                    case "אוכל":
                        sumFood += Integer.parseInt(expanseAmount);
                        pbFood.setProgress(sumFood);
                        if(pbFood.getProgress()==pbFood.getMax()){
                            Toast.makeText(DetailsActivity.this, getResources().getString(R.string.warning_toast), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Clothing":
                    case "ביגוד":
                        sumClothing += Integer.parseInt(expanseAmount);
                        pbClothing.setProgress(sumClothing);
                        if(pbClothing.getProgress()==pbClothing.getMax()){
                            Toast.makeText(DetailsActivity.this, getResources().getString(R.string.warning_toast), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Home":
                    case "בית":
                        sumHome += Integer.parseInt(expanseAmount);
                        pbHome.setProgress(sumHome);
                        if(pbHome.getProgress()==pbHome.getMax()){
                            Toast.makeText(DetailsActivity.this, getResources().getString(R.string.warning_toast), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Electronics":
                    case "מיחשוב":
                        sumElectronics += Integer.parseInt(expanseAmount);
                        pbElectronics.setProgress(sumElectronics);
                        if(pbElectronics.getProgress()==pbElectronics.getMax()){
                            Toast.makeText(DetailsActivity.this, getResources().getString(R.string.warning_toast), Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                Drawable drawableDlt = getResources().getDrawable(R.drawable.delete_btn);
                Drawable expanse_draw = getResources().getDrawable(R.drawable.expanse_draw);

                //Create elements
                TextView nameTextView = new TextView(DetailsActivity.this);
                nameTextView.setTextColor(getResources().getColor(R.color.black));
                TextView priceTextView = new TextView(DetailsActivity.this);
                priceTextView.setTextColor(getResources().getColor(R.color.black));
                Button delete = new Button(DetailsActivity.this);
                delete.setTextColor(getResources().getColor(R.color.black));
                LinearLayout expanse = new LinearLayout(DetailsActivity.this);

                ImageView isCredit = new ImageView(DetailsActivity.this);

                //Set delete and text from user input
                nameTextView.setText(expanseName);
                nameTextView.setPadding(0, 0, 0, 0);
                priceTextView.setText(expanseAmount);
                priceTextView.setPadding(0, 0, 0, 0);

                if(checkbox_credit.isChecked()){
                    isCredit.setImageResource(R.drawable.credit);
                }
                else if(checkbox_cash.isChecked()){
                    isCredit.setImageResource(R.drawable.cash);
                }

                isCredit.setPadding(0, 0, 0, 0);
                delete.setText("X");
                delete.setPadding(0, 0, 0, 0);

                expanseAmountTxt.setText("");

                //Design text alignment views
                nameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                priceTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                //Define layoutParams of textView
                LinearLayout.LayoutParams viewTextLayoutParams = new LinearLayout.LayoutParams(100, 100);
                viewTextLayoutParams.weight = 2;
                viewTextLayoutParams.setMargins(10, 10, 10, 10);

                LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(100, 100);
                viewTextLayoutParams.weight = 2;
                viewTextLayoutParams.setMargins(10, 10, 10, 10);
                imageViewLayoutParams.gravity = Gravity.CENTER;

                //Define layoutParams of expanse
                LinearLayout.LayoutParams expanseLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                expanseLayoutParams.setMargins(0, 10, 0, 10);


                //Define layoutParams of delete button
                LinearLayout.LayoutParams dltBtnLayoutParams = new LinearLayout.LayoutParams(20, 120);
                dltBtnLayoutParams.weight = 1;
                dltBtnLayoutParams.setMargins(10, 10, 10, 10);

                //Set layoutsParams
                delete.setLayoutParams(dltBtnLayoutParams);
                expanse.setLayoutParams(expanseLayoutParams);
                nameTextView.setLayoutParams(viewTextLayoutParams);
                priceTextView.setLayoutParams(viewTextLayoutParams);
                //isCreditTextView.setLayoutParams(viewTextLayoutParams);
                isCredit.setLayoutParams(imageViewLayoutParams);

//                expanse.setBackgroundColor(Color.parseColor("#FF000000"));

                //Add elements to expanse
                expanse.addView(delete);
                expanse.addView(nameTextView);
                expanse.addView(isCredit);
                expanse.addView(priceTextView);

                //Design expanse
                expanse.setOrientation(LinearLayout.HORIZONTAL);
                expanse.setWeightSum(5);

                //Set custom design
                delete.setBackground(drawableDlt);
                expanse.setBackground(expanse_draw);

                expansesLayout.addView(expanse);

                checkbox_credit.setChecked(false);
                cashView.setVisibility(View.INVISIBLE);
                checkbox_cash.setChecked(false);
                creditView.setVisibility(View.INVISIBLE);
                isCheckBool = false;


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ViewGroup) v.getParent().getParent()).removeView((ViewGroup) v.getParent());
                        switch (expanseName) {
                            case "Food":
                            case "אוכל":
                                sumFood -= Integer.parseInt(expanseAmount);
                                pbFood.setProgress(sumFood);
                                break;
                            case "Clothing":
                            case "ביגוד":
                                sumClothing -= Integer.parseInt(expanseAmount);
                                pbClothing.setProgress(sumClothing);
                                break;
                            case "Home":
                            case "בית":
                                sumHome -= Integer.parseInt(expanseAmount);
                                pbHome.setProgress(sumHome);
                                break;
                            case "Electronics":
                            case "מיחשוב":
                                sumElectronics -= Integer.parseInt(expanseAmount);
                                pbElectronics.setProgress(sumElectronics);
                                break;
                        }
                    }
                });
            }
        });
        RadioButton scrollDown = findViewById(R.id.scroll_down);
        RadioButton scrollUp = findViewById(R.id.scroll_up);

        scrollDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView scrollView = findViewById(R.id.scroll_view);
                scrollView.fullScroll(View.FOCUS_DOWN);
                if(scrollUp.isChecked()){
                    scrollUp.setChecked(false);
                }
            }
        });

        scrollUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView scrollView = findViewById(R.id.scroll_view);
                scrollView.fullScroll(View.FOCUS_UP);
                if(scrollDown.isChecked()){
                    scrollDown.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView selectedText = (TextView) parent.getChildAt(0);
        if (selectedText != null) {
            selectedText.setTextColor(Color.parseColor("#FF000000"));
            Toast.makeText(DetailsActivity.this, selectedText.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}