package com.example.shapiperetz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodMenu extends AppCompatActivity {
    RecyclerView recyclerView;
    Retrofit retrofit;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        buildRetrofit();
//        btn_back = findViewById(R.id.to_back_image);

        ServerApi serverApi = retrofit.create(ServerApi.class);

        Call<List<Constructor>> parsedFood = serverApi.getMenu();
        recyclerView = findViewById(R.id.recyclerViewFood);

        parsedFood.enqueue(new Callback<List<Constructor>>() {
            @Override
            public void onResponse(Call<List<Constructor>> call, Response<List<Constructor>> response) {
                if (response.isSuccessful()){
                    List<Constructor> responseFood = response.body();
                    dataToAdapter(responseFood);
                }
            }

            @Override
            public void onFailure(Call<List<Constructor>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void buildRetrofit(){
       Gson gson = new GsonBuilder()
                .setLenient()
                .create();
       retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gambit-app.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public void returnToMainActivity(View view) {
        Intent toMainActivity = new Intent(FoodMenu.this, MainActivity.class);
        startActivity(toMainActivity);
    }

    private void dataToAdapter(List<Constructor> list){
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rvAdapter);

    }
}