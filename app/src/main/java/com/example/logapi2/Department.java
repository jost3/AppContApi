package com.example.logapi2;

import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    // Constructor
    public Department(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Métodos getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
