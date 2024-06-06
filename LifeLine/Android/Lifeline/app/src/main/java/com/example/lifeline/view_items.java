package com.example.lifeline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

import java.util.ArrayList;

public class view_items extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> itemName,Quantity,itemid,donorid;
    String type_id,donrid,itemidd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        l1=findViewById(R.id.items);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        type_id=getIntent().getStringExtra("typeid");


       String url ="http://"+sh.getString("ip", "") + ":5000/user_viewitem";
        RequestQueue queue = Volley.newRequestQueue(view_items.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    itemName= new ArrayList<>();
                    Quantity= new ArrayList<>();
                    itemid= new ArrayList<>();
                    donorid= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        itemName.add(jo.getString("Item_name"));
                        Quantity.add(jo.getString("Quantity"));
                        itemid.add(jo.getString("Item_id"));
                        donorid.add(jo.getString("Login_id"));



                    }

//                     ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    l1.setAdapter(ad);

                    l1.setAdapter(new custom2(view_items.this,itemName,Quantity));
                    l1.setOnItemClickListener(view_items.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_items.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type",type_id);


                return params;
            }
        };
        queue.add(stringRequest);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        donrid=donorid.get(i);
        itemidd=itemid.get(i);

        Intent ii=new Intent(getApplicationContext(),request_item.class);
        ii.putExtra("did",donrid);
        ii.putExtra("itid",itemidd);
        startActivity(ii);


    }
}