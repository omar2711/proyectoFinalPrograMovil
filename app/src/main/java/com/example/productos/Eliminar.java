package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productos.estructural.Categoria;
import com.example.productos.estructural.Producto;
import com.example.productos.logica.ServicioDBProducto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Eliminar extends AppCompatActivity {
    private TextView txtAeli;
    private TextView txtnom;
    private Spinner txtCat;
    private TextView txtPrecC;
    private TextView txtIva;
    private TextView txtPrecV;
    private TextView txtFecha;
    private TextView txtCant;
    private ServicioDBProducto dbProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar6);
        setSupportActionBar(toolbarr);
        txtAeli = (TextView) findViewById(R.id.txtAeli);

        txtnom = (TextView) findViewById(R.id.txtCambnom3);
        txtCat = (Spinner) findViewById(R.id.txtCambCat3);
        txtPrecC = (TextView) findViewById(R.id.txtCambPrecC3);
        txtIva = (TextView) findViewById(R.id.txtCambIva3);
        txtPrecV = (TextView) findViewById(R.id.txtCambprecV3);
        txtFecha = (TextView) findViewById(R.id.txtCambFecha3);
        txtCant = (TextView) findViewById(R.id.txtCambcant);
        dbProducto = new ServicioDBProducto(this);

        ArrayList<String> categorias = new ArrayList<String>();
        ArrayList<Categoria> categoriasDB = dbProducto.getCategorias();
        for (int i = 0; i < categoriasDB.size(); i++) {
            categorias.add(categoriasDB.get(i).getCategoria());
        }
        ArrayAdapter<String> adapterDB = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categorias);


        txtCat.setAdapter(adapterDB);
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


    public void btnBuscarOnclick(View view) {
        int codIn;


        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtAeli.getText().toString().isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            String strCodigo = txtAeli.getText().toString();
            codIn = Integer.parseInt(strCodigo);
            Producto producto = dbProducto.ObtenerProducto(codIn);
            String  precioCompra, iva, precioVenta, fechaV, cantida;
            precioCompra = String.valueOf(producto.getPrecioCompra());
            iva = String.valueOf(producto.getIva());
            precioVenta = String.valueOf(producto.getPrecioVenta());
            fechaV = dateFormat.format(producto.getFechaVencimiento());
            cantida = String.valueOf(producto.getCantidad());
            String nombre = producto.getNombre();
            txtnom.setText(nombre);
            txtCat.setSelection(producto.getCategoria());
            txtPrecC.setText(precioCompra);
            txtIva.setText(iva);
            txtPrecV.setText(precioVenta);
            txtFecha.setText(fechaV);
            txtCant.setText(cantida);
            txtnom.requestFocus();
        }catch (Exception e){
            Toast.makeText(this, "No hay productos!", Toast.LENGTH_LONG).show();
        }


    }
    public void btnEliminar_Click(View view) throws Exception {
        int txtElm;
        Producto proct;
        if (txtAeli.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese un codigo valido!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            txtElm = Integer.parseInt(txtAeli.getText().toString());
            dbProducto.modificarEstadoProducto(txtElm);


            Toast.makeText(this, "se elimino el producto!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "No hay productos", Toast.LENGTH_LONG).show();
        }


    }
    public void btnEliminarFisico_Click(View view) throws Exception {
        int txtElm;
        Producto proct;
        if (txtAeli.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese un codigo valido!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            txtElm = Integer.parseInt(txtAeli.getText().toString());
            dbProducto.BorrarProducto(txtElm);
            Toast.makeText(this, "se elimino el producto!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "No hay productos", Toast.LENGTH_LONG).show();
        }




    }
}