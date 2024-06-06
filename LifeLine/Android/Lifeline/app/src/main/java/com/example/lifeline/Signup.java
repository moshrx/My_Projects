package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
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

public class Signup extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    Button b1;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        e1=findViewById(R.id.editTextTextPersonName4);
        e2=findViewById(R.id.editTextTextPersonName6);
        e3=findViewById(R.id.editTextTextPersonName8);
        e4=findViewById(R.id.editTextTextPersonName9);
        e5=findViewById(R.id.editTextNumber);
        e6=findViewById(R.id.editTextTextPersonName81);
        e7=findViewById(R.id.editTextNumberSigned);
        e8=findViewById(R.id.editTextTextPersonName84);
        e9=findViewById(R.id.editTextTextPersonName86);
        e10=findViewById(R.id.editTextTextPassword2);
        b1=findViewById(R.id.button1);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname=e1.getText().toString();
                final String lname=e2.getText().toString();
                final String place=e3.getText().toString();
                final String post=e4.getText().toString();
                final String pin=e5.getText().toString();
                final String district=e6.getText().toString();
                final String phone=e7.getText().toString();
                final String email=e8.getText().toString();
                final String username=e9.getText().toString();
                final String password=e10.getText().toString();

                if(fname.equalsIgnoreCase(""))
                {
                    e1.setError("Enter Your First Name");
                }
                else if(!fname.matches("^[a-zA-Z]*$"))
                {
                    e1.setError("characters allowed");
                }
                else if(lname.equalsIgnoreCase(""))
                {
                    e2.setError("Enter Your Last Name");
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e3.setError("Enter Your Place");
                }
                else if(post.equalsIgnoreCase(""))
                {
                    e4.setError("Enter Your Post");
                }
                else if(pin.equalsIgnoreCase(""))
                {
                    e5.setError("Enter Your Pin");
                }

                else if(pin.length()!=6)
                {
                    e5.setError("invalid pin");
                    e5.requestFocus();


                }else if(phone.equalsIgnoreCase(""))
                {
                    e7.setError("Enter Your Phone No");
                }

                else if(phone.length()<10)
                {
                    e7.setError("Phone Number should be 10 ");
                    e7.requestFocus();


                }

                else if(email.equalsIgnoreCase(""))
                {
                    e8.setError("Enter Your Email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e8.setError("Enter Valid Email");
                    e8.requestFocus();
                }

                else if(district.equalsIgnoreCase(""))
                {
                    e6.setError("Enter Your District");
                }

                else if(username.equalsIgnoreCase(""))
                {
                    e9.setError("Enter Your Username");
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e10.setError("Enter Your Password");
                }

                else {


                    RequestQueue queue = Volley.newRequestQueue(Signup.this);

                    String url = "http://" + sh.getString("ip", "") + ":5000/user_register";

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


                                    Toast.makeText(Signup.this, "User Signup success", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), user_home.class);
                                    startActivity(ik);

                                } else {
                                    Toast.makeText(Signup.this, "Failed", Toast.LENGTH_SHORT).show();

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
                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("district", district);
                            params.put("phone", phone);
                            params.put("email", email);
                            params.put("username", username);
                            params.put("pass", password);


                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }



            }
        });

    }
}