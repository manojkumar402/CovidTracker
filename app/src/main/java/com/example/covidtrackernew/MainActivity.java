package com.example.covidtrackernew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView stateRV;
    StateAdapter StateRVAdapter;
    List<StateModel> stateList;
    List<StateModel> sortStateList;
    LinearLayoutManager layoutManager;
    private TextView wwTCases;
    private TextView wwRCases;
    private TextView wwDCases;
    private Button button;
    private Button Desbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateRV = findViewById(R.id.recycler_view);
        wwTCases = findViewById(R.id.totalCases);
        wwRCases = findViewById(R.id.recoveredCases);
        wwDCases = findViewById(R.id.deathCases);
        button = findViewById(R.id.Sortbutton);
        Desbutton = findViewById(R.id.DesButton);
        getStateInfo();

        Desbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(sortStateList, new Comparator<StateModel>() {
                    @Override
                    public int compare(StateModel s1, StateModel s2) {
                        return s1.getmCases() - s2.getmCases();
                    }
                });
                StateRVAdapter.notifyDataSetChanged();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(sortStateList);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                stateRV.setLayoutManager(layoutManager);
                StateRVAdapter = new StateAdapter(sortStateList);
                stateRV.setAdapter(StateRVAdapter);
                StateRVAdapter.notifyDataSetChanged();

            }
        });

    }






    private void getStateInfo(){
        String url = "https://api.rootnet.in/covid19-in/stats/latest";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            stateList = new ArrayList<StateModel>();
                            sortStateList = new ArrayList<StateModel>();
                            JSONObject dataObject = response.getJSONObject("data");
                            JSONObject summaryObj = dataObject.getJSONObject("summary");

                            int tCases = summaryObj.getInt("total");
                            int rCases = summaryObj.getInt("discharged");
                            int dCases = summaryObj.getInt("deaths");
                            wwTCases.setText(String.valueOf(tCases));
                            wwRCases.setText(String.valueOf(rCases));
                            wwDCases.setText(String.valueOf(dCases));

                            JSONArray regionalArray = dataObject.getJSONArray("regional");
                            for(int i=1;i<regionalArray.length();i++){
                                JSONObject regionalObj = regionalArray.getJSONObject(i);
                                String stateName = regionalObj.getString("loc");
                                int totalCases = regionalObj.getInt("totalConfirmed");
                                int deathCases = regionalObj.getInt("deaths");
                                int recoverdCases = regionalObj.getInt("discharged");

                                StateModel stateModel = new StateModel(stateName,recoverdCases,deathCases,totalCases);

                                stateList.add(stateModel);
                                sortStateList.add(stateModel);

//                                Log.v("mainActivity", "The number of case are " + stateName);
//                                Log.v("mainActivity", "The total number of case are " + totalCases);
//                                Log.v("mainActivity", "The Death number of case are " + deathCases);
//                                Log.v("mainActivity", "The Recovered number of case are " + recoverdCases);

                            }

                            layoutManager = new LinearLayoutManager(MainActivity.this);
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            stateRV.setLayoutManager(layoutManager);
                            StateRVAdapter = new StateAdapter(stateList);
                            stateRV.setAdapter(StateRVAdapter);
                            StateRVAdapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Unable to Fetch Data\nCheck your wifi connection", Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



}