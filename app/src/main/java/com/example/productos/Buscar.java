package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productos.estructural.Producto;
import com.example.productos.logica.ServicioDBProducto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Buscar extends AppCompatActivity {
    private TextView txtBuscq;
    private TextView txtBuscado;
    private ServicioDBProducto dbProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbarr);
        txtBuscq = (TextView) findViewById(R.id.txtBuscarIn);
        txtBuscado = findViewById(R.id.txtBuscado);
        dbProducto = new ServicioDBProducto(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.agregar:

                intent = new Intent(this, Agregar.class);
                startActivity(intent);
                return true;
            case R.id.buscar:
                intent = new Intent(this, Buscar.class);
                startActivity(intent);
                return true;
            case R.id.actualizar:
                intent = new Intent(this, Modificar.class);
                startActivity(intent);
                return true;
            case R.id.eliminar:
                intent = new Intent(this, Eliminar.class);
                startActivity(intent);
                return true;
            case R.id.menu:
                intent = new Intent(this, GUIMenu.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void btnBuscar_Click(View view) {
        int txtBust;
        String strCodigo = txtBuscq.getText().toString();
        String cadena;
        Producto proc = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtBuscq.getText().toString().isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }
        String pro;
        txtBust = Integer.parseInt(strCodigo);
        System.out.println(txtBust);
        proc = dbProducto.ObtenerProducto(txtBust);
        if (proc == null) {
            Toast.makeText(this, "No hay productos!", Toast.LENGTH_LONG).show();
            return;
        } else  {
            txtBuscado.setText("");
            cadena = "Código: " + proc.getCodigo() + "\n"
                    + "Nombre: " + proc.getNombre() + "\n"
                    + "Categoria: " + dbProducto.getCategoria(proc.getCategoria()) + "\n"
                    + "PrecioCompra: " + proc.getPrecioCompra() + "\n"
                    + "Iva: " + proc.getIva() + "\n"
                    + "Precioventa: " + proc.getPrecioVenta() + "\n"
                    + "FechaVencimientoGarantia: " + dateFormat.format(proc.getFechaVencimiento()) + "\n"
                    + "Cantidad: " + proc.getCantidad() + "\n";
            txtBuscado.append(cadena);
        }



    }

    public void btnBuscarEnArchivo_Click(View view) {
        int txtBust;
        String strCodigo = txtBuscq.getText().toString();
        String cadena;
        ArrayList proc = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtBuscq.getText().toString().isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }
        String pro;
        txtBust = Integer.parseInt(strCodigo);
        System.out.println(txtBust);


    }

}
