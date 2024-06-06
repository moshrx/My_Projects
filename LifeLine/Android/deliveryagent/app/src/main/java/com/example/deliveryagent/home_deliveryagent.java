package com.example.deliveryagent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public  class home_deliveryagent extends AppCompatActivity {
    Button b1,b2;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_deliveryagent);
        b1=findViewById(R.id.button5);

        b2=findViewById(R.id.button7);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a=new Intent(getApplicationContext(),deliveryagent_works.class);
                startActivity(a);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent b=new Intent(getApplicationContext(),Login.class);
                startActivity(b);


            }
        });
    }
}
