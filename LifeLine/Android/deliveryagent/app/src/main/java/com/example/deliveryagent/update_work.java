package com.example.deliveryagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class update_work extends AppCompatActivity {
    SharedPreferences sh;
    String dname,work_id,reqid;
    TextView t1,t2;
    EditText e1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_work);
        dname=getIntent().getStringExtra("dname");
        work_id=getIntent().getStringExtra("work_id");
        reqid=getIntent().getStringExtra("reqid");
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=findViewById(R.id.editTextTextPersonName11);
        b1=findViewById(R.id.button10);
        t1=findViewById(R.id.textView2);
        t2=findViewById(R.id.textView3);
        t1.setText(reqid);
        t2.setText(dname);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final String status=e1.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(update_work.this);

                String url = "http://" + sh.getString("ip","")+ ":5000/update_work";

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

                                Toast.makeText(update_work.this, "success", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(), home_deliveryagent.class);
                                startActivity(ik);

                            }
                            else {
                                Toast.makeText(update_work.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

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
                        params.put("status", status);
                        params.put("req_id", work_id);



                        return params;
                    }
                };
                queue.add(stringRequest);



            }
        });


    }
}