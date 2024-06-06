package com.example.deliveryagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public  class deliveryagent_works extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> name,date, phone, description,dname,work_id,reqid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryagent_works);
        l1 = findViewById(R.id.hello);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String url = "http://" + sh.getString("ip", "") + ":5000/assign_work";
        RequestQueue queue = Volley.newRequestQueue(deliveryagent_works.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    name = new ArrayList<>();
                    date = new ArrayList<>();
                    phone = new ArrayList<>();
                    description = new ArrayList<>();

                    dname = new ArrayList<>();
                    work_id = new ArrayList<>();
                    reqid = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        name.add(jo.getString("First_Name")+""+jo.getString("Last_Name")+"\n"+jo.getString("Phone"));
                        date.add(jo.getString("Date"));
                        phone.add(jo.getString("Phone"));
                        description.add(jo.getString("Description"));
                        dname.add(jo.getString("dname")+""+jo.getString("dlname"));

                        work_id.add(jo.getString("Assign_id"));
                        reqid.add(jo.getString("Request_id"));


                    }

//                    ArrayAdapter<String> ad = new ArrayAdapter<String>(deliveryagent_works.this, android.R.layout.simple_list_item_1, item);
//                    l1.setAdapter(ad);

                    l1.setAdapter(new custom3(deliveryagent_works.this,name,description,date));
                    l1.setOnItemClickListener(deliveryagent_works.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(deliveryagent_works.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("agent_id",sh.getString("lid",""));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        Intent a=new Intent(getApplicationContext(),update_work.class);
        a.putExtra("dname",dname.get(i));
        a.putExtra("work_id",work_id.get(i));
        a.putExtra("reqid",reqid.get(i));

        startActivity(a);

    }
}