package com.example.logapi2;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);


        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://prub.colegiohessen.edu.pe/api/") // Reemplaza "URL_DEL_API" con la URL real del API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia del servicio API
        apiService = retrofit.create(ApiService.class);

        // Configurar el botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos de entrada
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Realizar la solicitud de inicio de sesión
                Call<LoginResponse> call = apiService.login(email, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            // El inicio de sesión fue exitoso
                            LoginResponse loginResponse = response.body();
                            String accessToken = loginResponse.getToken();

                            SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("accessToken", accessToken);
                            editor.apply();
                            // Manejar la respuesta del servidor aquí
                            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso. Token: " + accessToken, Toast.LENGTH_SHORT).show();
                            // Redireccionar a MainActivity2
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent);
                        } else {
                            // El inicio de sesión falló
                            Toast.makeText(MainActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Error de red
                        Log.e("Login", "Error: " + t.getMessage());
                        Toast.makeText(MainActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

