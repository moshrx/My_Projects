package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class request_item extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String itemoid,donorid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_item);
        e1=findViewById(R.id.editTextTextPersonName3);
        b1=findViewById(R.id.button8);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        itemoid=getIntent().getStringExtra("itid");

        donorid=getIntent().getStringExtra("did");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = e1.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(request_item.this);

                String url = "http://" + sh.getString("ip", "") + ":5000/user_request";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");


                            if (res.equalsIgnoreCase("success")) {

                                Toast.makeText(request_item.this, "sended successfully!!", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(), user_home.class);
                                startActivity(ik);

                            } else {
                                Toast.makeText(request_item.this, "sending failed !!!", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("description", description);
                        params.put("lid",sh.getString("lid", ""));
                        params.put("item_id", itemoid);
                        params.put("donor_id", donorid);
                        return params;
                    }
                };
                queue.add(stringRequest);




            }





        });
    }
}