package com.example.mainfeddback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlertMess extends AppCompatDialogFragment {
    private ListView mListView;
    private List<String> mItems;
    private RequestQueue requestQueue;
    private Class<?> mActivityToLaunch;
    private  SharedPreferences sharedPreferences;

    public AlertMess(Context context,Class<?> activityToLaunch) {
        mActivityToLaunch = activityToLaunch;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_layout, null);
        builder.setView(view);

        mListView = view.findViewById(R.id.dialog_list);
        List<String> namesList = new ArrayList<>();
        //shared preference
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://mess-shivshakya.vercel.app/getMess";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Toast.makeText(getActivity(), "Successfully received the response", Toast.LENGTH_SHORT).show();
                    Log.d("API", response.toString());

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String description = jsonObject.getString("description");
                        Log.d("name", name);
                        namesList.add(name);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, namesList);
                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                String selectedItem = namesList.get(position);
                                Intent intent = new Intent(getActivity(), mActivityToLaunch);
                                editor.putString("name", selectedItem);
                                editor.apply();
                                startActivity(intent);
                            } catch (Exception e) {
                                Log.e("Error", e.getMessage(), e);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

        return builder.create();
    }
}
