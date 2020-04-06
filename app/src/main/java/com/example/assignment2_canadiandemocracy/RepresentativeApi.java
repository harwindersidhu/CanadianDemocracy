package com.example.assignment2_canadiandemocracy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RepresentativeApi {
    @GET("representative-sets")
    Call<RepresentativeSetList> getRepresentativeSetList();

    @GET
    Call<RepresentativeList> getRepresentativeList(@Url String url);

}
