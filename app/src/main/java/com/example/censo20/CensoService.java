package com.example.censo20;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CensoService {
    @GET("/api/censo/{id}")
    Call<List<Coletor>> repoCenso(
            @Path("id") String repo
    );

    @POST("/api/censo/")
    Call<Coletor> PostCenso(
            @Body Coletor Censo
    );

    @PUT("/api/censo/{id}")
    Call<List<Coletor>> updateUser(
            @Part("photo") RequestBody photo,
            @Part("description") RequestBody description
    );

    @DELETE("/api/censo/{id}")
    Call<List<Coletor>> updateUser(
            @Path("id") String id
    );

    public static final Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl("https://whispering-headland-07022.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
}
