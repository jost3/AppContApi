package com.example.logapi2;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
public interface ApiService {
    @FormUrlEncoded
    @POST("auth/login") // Reemplaza "login" con la ruta real del endpoint de inicio de sesión
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @GET("departaments") // Reemplaza "departaments" con la ruta real de la API para obtener los departamentos
    Call<List<Department>> getDepartments(@Header("Authorization") String authorization);


}
