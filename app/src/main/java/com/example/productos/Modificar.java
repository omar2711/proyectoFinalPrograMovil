package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.example.productos.estructural.Categoria;
import com.example.productos.estructural.Producto;
import com.example.productos.logica.ServicioDBProducto;

public class Modificar extends AppCompatActivity {
    private TextView txtAmod;

    private TextView txtCod;
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
        setContentView(R.layout.activity_modificar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(toolbarr);
        txtAmod = (TextView) findViewById(R.id.txtAmod);
        txtnom = (TextView) findViewById(R.id.txtCambnom);
        txtCat = (Spinner) findViewById(R.id.txtCambCat);
        txtPrecC = (TextView) findViewById(R.id.txtCambPrecC);
        txtIva = (TextView) findViewById(R.id.txtCambIva);
        txtPrecV = (TextView) findViewById(R.id.txtCambprecV);
        txtFecha = (TextView) findViewById(R.id.txtCambFecha);
        txtCant = (TextView) findViewById(R.id.txtCambcant);
        dbProducto = new  ServicioDBProducto(this);
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


    public void fechaOnClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "-" + month + "-" + year;
                txtFecha.setText(fecha);
            }
        }, anio, mes, dia);
        pickerDialog.show();
    }

    public void btnBuscarOnclick(View view) {
        int codIn;


        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtAmod.getText().toString().isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            String strCodigo = txtAmod.getText().toString();
            codIn = Integer.parseInt(strCodigo);
            Producto producto = dbProducto.ObtenerProducto(codIn);
            String codigo, precioCompra, iva, precioVenta, fechaV, cantida;
            codigo = String.valueOf(producto.getCodigo());
            precioCompra = String.valueOf(producto.getPrecioCompra());
            iva = String.valueOf(producto.getIva());
            precioVenta = String.valueOf(producto.getPrecioVenta());
            fechaV = dateFormat.format(producto.getFechaVencimiento());
            cantida = String.valueOf(producto.getCantidad());

            txtnom.setText(producto.getNombre());
            txtCat.setSelection(producto.getCategoria()-1);
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


    public void actualizar(View view) {
        int codIn;
        String nombre;
        double precioCompra, iva, precioVenta;
        int codigoN, cantidad, categoria;
        Date fecha;
        String codigo = txtAmod.getText().toString();
        codIn = Integer.parseInt(codigo);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        nombre = txtnom.getText().toString();
        categoria = txtCat.getSelectedItemPosition()+1;
        precioCompra = Double.parseDouble(txtPrecC.getText().toString());
        iva = Double.parseDouble(txtIva.getText().toString());
        precioVenta = Double.parseDouble(txtPrecV.getText().toString());
        cantidad = Integer.parseInt(txtCant.getText().toString());

        try {
            fecha = dateFormat.parse(txtFecha.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(this, "La fehca NO es válida!", Toast.LENGTH_LONG).show();
            return;
        }

        Producto producto = new Producto(codIn, nombre, categoria, precioCompra, iva, precioVenta, fecha, cantidad,Producto.STATUS_AC);
        try {

            dbProducto.ModificarProducto(producto);
            Toast.makeText(this, "Se ha actualizado el Producto", Toast.LENGTH_LONG).show();
            txtCod.setText("");
            txtnom.setText("");
            txtCat.setTransitionName("");
            txtPrecC.setText("");
            txtIva.setText("");
            txtPrecV.setText("");
            txtFecha.setText("");
            txtCant.setText("");
            txtAmod.requestFocus();

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo actualizar!", Toast.LENGTH_LONG).show();
            return;
        }


    }
}