package com.example.assignment2_canadiandemocracy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LegislatureRepresentative extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RepresentativeRecyclerAdapter mAdapter;
    private String representativeSetUrl;
    private RepresentativeSet seletedSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislature_representative);

        // Get the intent and its data.
        Intent intent = getIntent();
        seletedSet = (RepresentativeSet) intent.getSerializableExtra(MainActivity.SELECTED_REPRESENTATIVE_SET);
        setTitle(seletedSet.getName());
        representativeSetUrl = seletedSet.getRelated().get("representatives_url");
        getRepresentatives();
    }

    /**
     * It will get the list of representatives from a provided url. Retrofit is used here
     */
    public void getRepresentatives() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://represent.opennorth.ca/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RepresentativeApi representativeApi = retrofit.create(RepresentativeApi.class);
        Call<RepresentativeList> call = representativeApi.getRepresentativeList(representativeSetUrl);
        call.enqueue(new Callback<RepresentativeList>() {
            @Override
            public void onResponse(Call<RepresentativeList> call, Response<RepresentativeList> response) {
                RepresentativeList representativeList = response.body();
                List<Representative> representatives = representativeList.getObjects();

                // Get a handle to the RecyclerView.
                mRecyclerView = findViewById(R.id.recyclerView);
                // Create an adapter and supply the data to be displayed.
                mAdapter = new RepresentativeRecyclerAdapter(LegislatureRepresentative.this, representatives);
                // Connect the adapter with the RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                // Give the RecyclerView a default layout manager.
                mRecyclerView.setLayoutManager(new LinearLayoutManager(LegislatureRepresentative.this));

            }

            @Override
            public void onFailure(Call<RepresentativeList> call, Throwable t) {
                System.out.println(call);
            }
        });
    }
}
