package com.example.assignment2_canadiandemocracy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private String selectedRepresentativeSetUrl;
    List<String> namesOfRepresentativesSet;
    List<RepresentativeSet> representativeSets;
    RepresentativeSet selectedSet;
    public static final String SELECTED_REPRESENTATIVE_SET = "SelectedRepresentativeSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://represent.opennorth.ca/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RepresentativeApi representativeApi = retrofit.create(RepresentativeApi.class);
        Call<RepresentativeSetList> call = representativeApi.getRepresentativeSetList();
        call.enqueue(new Callback<RepresentativeSetList>() {
            @Override
            public void onResponse(Call<RepresentativeSetList> call, Response<RepresentativeSetList> response) {
                RepresentativeSetList representativeSetList = response.body();
                representativeSets = representativeSetList.getObjects();
                namesOfRepresentativesSet = new ArrayList<>();
                for (RepresentativeSet representativeSet : representativeSets) {
                    namesOfRepresentativesSet.add(representativeSet.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        namesOfRepresentativesSet
                );

                spinner.setAdapter(adapter);
                System.out.println(representativeSets);
            }

            @Override
            public void onFailure(Call<RepresentativeSetList> call, Throwable t) {
                System.out.println(call);
            }
        });
    }

    /**
     * On press of button it will launch LegislatureRepresentative activity
     * @param view
     */
    public void launchLegislatureRepresentatives(View view) {
        Intent intent = new Intent(this, LegislatureRepresentative.class);
        intent.putExtra(SELECTED_REPRESENTATIVE_SET, selectedSet);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //selectedRepresentativeSetUrl = representativeSets.get(position).getRelated().get("representatives_url");
        selectedSet = representativeSets.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
