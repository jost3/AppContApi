package com.example.logapi2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    private ApiService apiService;
    private TextView textViewDepartments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Inicializar ApiService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://prub.colegiohessen.edu.pe/api/") // Reemplaza "URL_DEL_API" con la URL real del API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Obtener el token de acceso almacenado
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String accessToken = preferences.getString("accessToken", "");

        // Obtener referencia al TextView
        textViewDepartments = findViewById(R.id.textViewDepartments);

        // Verificar si se obtuvo el token de acceso
        if (!accessToken.isEmpty()) {
            // Agregar el token de acceso como encabezado de autorización
            String authorizationHeader = "Bearer " + accessToken;
            apiService.getDepartments(authorizationHeader).enqueue(new Callback<List<Department>>() {
                @Override
                public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                    if (response.isSuccessful()) {
                        // Respuesta exitosa
                        List<Department> departments = response.body();
                        if (departments != null) {
                            // Construir una cadena con los departamentos
                            StringBuilder departmentsString = new StringBuilder();
                            for (Department department : departments) {
                                departmentsString.append("ID: ").append(department.getId()).append(", Name: ").append(department.getNombre()).append("\n");
                            }
                            // Establecer el texto en el TextView
                            textViewDepartments.setText(departmentsString.toString());
                        } else {
                            textViewDepartments.setText("No hay departamentos disponibles");
                        }
                    } else {
                        // Respuesta no exitosa
                        textViewDepartments.setText("Error al obtener los departamentos");
                    }
                }

                @Override
                public void onFailure(Call<List<Department>> call, Throwable t) {
                    // Manejo de error
                    textViewDepartments.setText("Error de red");
                }
            });
        } else {
            // Si el token de acceso está vacío
            textViewDepartments.setText("No se encontró el token de acceso");
        }
    }
}
