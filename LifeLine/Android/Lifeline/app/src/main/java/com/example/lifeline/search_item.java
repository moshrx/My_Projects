package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class search_item extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1;
    Button b1;
    SharedPreferences sh;
    ArrayList<String> id,type;
    String itid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        s1=findViewById(R.id.spinner);
        b1=findViewById(R.id.button9);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        String url ="http://"+sh.getString("ip","")+":5000//user_viewitems";
        s1.setOnItemSelectedListener(search_item.this);
        RequestQueue queue = Volley.newRequestQueue(search_item.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    id= new ArrayList<String>(ar.length());
                    type= new ArrayList<String>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        type.add(jo.getString("Item_type"));
                        id.add(jo.getString("Item_id"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(search_item.this,android.R.layout.simple_spinner_item,type);
                    s1.setAdapter(ad);

                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);






        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(getApplicationContext(),view_items.class);
                a.putExtra("typeid",itid);
                startActivity(a);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        itid=type.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}