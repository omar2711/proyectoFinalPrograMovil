package com.example.productos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.productos.estructural.Producto;
import com.example.productos.logica.ServicioDBProducto;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Listar extends AppCompatActivity {
     ListView txtListar;
     private ServicioDBProducto dbProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbarr);
        txtListar = (ListView) findViewById(R.id.txtListaS);
        dbProducto = new ServicioDBProducto(this);

        };



    public void cargar (View view) throws IOException {

        ArrayList<String> listado = new ArrayList<>();
        String cadena;
        ArrayList<Producto> productos = dbProducto.Listar();
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getStatus().equals(Producto.STATUS_AC))
                listado.add(productos.get(i).getNombre());
        }
        Toast.makeText(this, "carga: "+ listado.size() , Toast.LENGTH_LONG).show();
        String Datos[] = listado.toArray(new String[listado.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String >(this , android.R.layout.simple_list_item_1 , Datos);
            txtListar.setAdapter(adapter);
        }

    public void cargarFilter (View view) throws IOException {

        ArrayList<String> listado = new ArrayList<>();
        String cadena;
        ArrayList<Producto> productos = dbProducto.Listar();
        for (int i = 0; i < productos.size(); i++) {
            listado.add(productos.get(i).getNombre()+" - "+ productos.get(i).getStatus());
        }
        Toast.makeText(this, "carga: "+ listado.size() , Toast.LENGTH_LONG).show();
        String Datos[] = listado.toArray(new String[listado.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String >(this , android.R.layout.simple_list_item_1 , Datos);
        txtListar.setAdapter(adapter);
    }

    }






