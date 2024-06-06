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

public class Forgot_password extends AppCompatActivity {
        EditText e1,e2;
        Button b1;
        SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        e1=findViewById(R.id.username);
        e2=findViewById(R.id.email);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=e1.getText().toString();
                final String email=e2.getText().toString();
                if(username.equalsIgnoreCase(""))
                {
                    e1.setError("Enter the username");
                }
                else if(email.equalsIgnoreCase(""))
                {
                    e2.setError("Enter the Email Address");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(Forgot_password.this);

                    String url = "http://" + sh.getString("ip", "") + ":5000/forgotpassword";

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

                                    Toast.makeText(Forgot_password.this, "Check your Mail", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), Login.class);
                                    startActivity(ik);

                                } else {
                                    Toast.makeText(Forgot_password.this, "sending failed !!!", Toast.LENGTH_SHORT).show();

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
                            params.put("username",username);
                            params.put("email", email);
                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });
    }
}