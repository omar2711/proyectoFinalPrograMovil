package com.example.productos.logica;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.productos.estructural.Categoria;
import com.example.productos.estructural.Producto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServicioDBProducto extends SQLiteOpenHelper {

    public static final String NOMBRE_BASE_DE_DATOS = "BDPRODUCTOS.db";
    public static final String NOMBRE_TABLA_PRODUCTOS = "PRODUCTOS";
    public static final String NOMBRE_TABLA_CATEGORIAS = "CATEGORIAS";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_CODIGO = "CODIGO";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_ESTADO = "ESTADO";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_NOMBRE = "NOMBRE";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOC = "PRECIO_COMPRA";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_IVA = "IVA";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOV = "PRECIO_VENTA";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_FECHAV = "FECHA_VENCIMIENTO";
    public static final String NOMBRE_ATRIBUTO_PRODUCTOS_CANTIDAD = "CANTIDAD";
    public static final String NOMBRE_LLAVE_FORANEA = "CATEGORIASCODIGO_CATEGORIA";
    public static final String NOMBRE_ATRIBUTO_CATEGORIAS_CATEGORIA = "CATEGORIA";
    public static final String NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO = "CODIGO_CATEGORIA";

    public ServicioDBProducto(Context context){
        super(context,NOMBRE_BASE_DE_DATOS,null,1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String cadSQL = "";
        String cadSQL2 = "";

        cadSQL = "CREATE TABLE " + NOMBRE_TABLA_CATEGORIAS + "("
                + NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO + " INTEGER NOT NULL PRIMARY KEY, "
                + NOMBRE_ATRIBUTO_CATEGORIAS_CATEGORIA + " TEXT NOT NULL"+");";

        cadSQL2 = "CREATE TABLE " + NOMBRE_TABLA_PRODUCTOS + "(\n"
                + NOMBRE_ATRIBUTO_PRODUCTOS_CODIGO + " INTEGER NOT NULL PRIMARY KEY, "
                + NOMBRE_ATRIBUTO_PRODUCTOS_ESTADO + " TEXT NOT NULL, "
                + NOMBRE_ATRIBUTO_PRODUCTOS_NOMBRE + " TEXT NOT NULL, "
                + NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOC + " REAL NOT NULL, "
                + NOMBRE_ATRIBUTO_PRODUCTOS_IVA + " REAL NOT NULL, "
                + NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOV + " REAL NOT NULL, "
                + NOMBRE_ATRIBUTO_PRODUCTOS_FECHAV + " DATE, "
                + NOMBRE_ATRIBUTO_PRODUCTOS_CANTIDAD + " INTEGER NOT NULL, "
                + NOMBRE_LLAVE_FORANEA + " INTEGER NOT NULL, "
                // Se agregar la llave foranea para hacer referencia a la tabla de CATEGORIAS
                + " CONSTRAINT " + "FK_" + NOMBRE_TABLA_CATEGORIAS
                + " FOREIGN KEY(" + NOMBRE_LLAVE_FORANEA + ")"
                + " REFERENCES " + NOMBRE_TABLA_CATEGORIAS +"("+NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO+")"+");";

        db.execSQL(cadSQL);
        db.execSQL(cadSQL2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String cadSQL = "";
        String cadSQL2 = "";


        cadSQL = "DROP TABLE IF EXISTS " + NOMBRE_TABLA_PRODUCTOS + ";";
        cadSQL2 = "DROP TABLE IF EXISTS "+ NOMBRE_TABLA_CATEGORIAS + ";";

        sqLiteDatabase.execSQL(cadSQL);
        sqLiteDatabase.execSQL(cadSQL2);

    }

    public boolean InsertarCategorias() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues;
        long resultado;
        contentValues = new ContentValues();

        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO, 1);
        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CATEGORIA, "Seleccionar una Categoría");
        resultado = db.insert(NOMBRE_TABLA_CATEGORIAS, null, contentValues);

        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO, 2);
        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CATEGORIA, "GTX SERIE 10");
        resultado = db.insert(NOMBRE_TABLA_CATEGORIAS, null, contentValues);

        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO, 3);
        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CATEGORIA, "RTX SERIE 20");
        resultado = db.insert(NOMBRE_TABLA_CATEGORIAS, null, contentValues);

        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO, 4);
        contentValues.put(NOMBRE_ATRIBUTO_CATEGORIAS_CATEGORIA, "GTX SERIE 30");
        resultado = db.insert(NOMBRE_TABLA_CATEGORIAS, null, contentValues);



        if(resultado == -1){
            return false;
        }

        return true;

    }
    public String getCategoria(int codigo_categoria){
        String categoriaDB = "";
        String[] argumentos = {String.valueOf(codigo_categoria)};
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "SELECT "+NOMBRE_ATRIBUTO_CATEGORIAS_CATEGORIA+" FROM CATEGORIAS WHERE "+NOMBRE_ATRIBUTO_CATEGORIAS_CODIGO+"= ?";
        Cursor reg = db.rawQuery(q,argumentos);
        if(reg.moveToFirst()){
            do{
                categoriaDB = reg.getString(0);
            }while(reg.moveToNext());
        }
        return categoriaDB;
    }
    public ArrayList<Categoria> getCategorias(){
        ArrayList<Categoria> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Categoria c;
        String q = "SELECT * FROM CATEGORIAS";
        Cursor reg = db.rawQuery(q,null);
        if(reg.moveToFirst()){
            do{
                c = new Categoria(reg.getInt(0),reg.getString(1));

                lista.add(c);
            }while(reg.moveToNext());
        }
        return lista;
    }
    public boolean AdicionarProducto(Producto producto){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues;
        long resultado;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        contentValues = new ContentValues();
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_CODIGO, producto.getCodigo() );
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_ESTADO, producto.getStatus());
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_NOMBRE, producto.getNombre());
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOC, producto.getPrecioCompra());
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_IVA, producto.getIva());
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOV, producto.getPrecioVenta());
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_FECHAV, dateFormat.format(producto.getFechaVencimiento()));
        contentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_CANTIDAD, producto.getCantidad());
        contentValues.put(NOMBRE_LLAVE_FORANEA,producto.getCategoria());
        resultado = db.insert(NOMBRE_TABLA_PRODUCTOS, null, contentValues);


        if(resultado == -1){
            return false;
        }

        return true;
    }


    public Producto ObtenerProducto(int codigo){
        Producto producto = null;
        Date date;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] argumentos = {String.valueOf(codigo)};
        String q  = "SELECT "+ NOMBRE_ATRIBUTO_PRODUCTOS_CODIGO + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_NOMBRE + ", "
                + NOMBRE_LLAVE_FORANEA + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOC + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_IVA + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOV + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_FECHAV + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_CANTIDAD + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_ESTADO
                +" FROM PRODUCTOS" + " where CODIGO = "+codigo+";";
        Cursor reg = db.rawQuery(q,null);
        if(reg.moveToFirst()){
            do{
                String fechar = reg.getString(6);
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                     date = format.parse(fechar);
                    producto = new Producto(reg.getInt(0),reg.getString(1),reg.getInt(2),reg.getDouble(3),reg.getDouble(4),reg.getDouble(5),date ,reg.getInt(7));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }while(reg.moveToNext());
        }
        return producto;

    }

    public Producto getProducto(int codigo){
        Producto producto = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] argumentos = {String.valueOf(codigo)};

        return producto;
    }
    public boolean BorrarProducto(int codigo) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] argumentos = {String.valueOf(codigo)};
        long resultado;

        resultado = db.delete(NOMBRE_TABLA_PRODUCTOS, "CODIGO = ?", argumentos);

        if(resultado == -1){
            return true;
        }

        else
        {
            return false;
        }
    }


    public int ModificarProducto(Producto productom){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newcontentValues = new ContentValues();

        newcontentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_ESTADO, productom.getNombre());
        newcontentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_NOMBRE, productom.getNombre());
        newcontentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOC, productom.getPrecioCompra());
        newcontentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_IVA, productom.getIva());
        newcontentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOV, productom.getPrecioVenta());
        newcontentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_CANTIDAD, productom.getCantidad());
        newcontentValues.put(NOMBRE_LLAVE_FORANEA,productom.getCategoria());

        String campoActualizar = "CODIGO = ?";
        String[] argumentosActualizar = {String.valueOf(productom.getCodigo())};

        return db.update(NOMBRE_TABLA_PRODUCTOS, newcontentValues, campoActualizar, argumentosActualizar);
    }

    public int modificarEstadoProducto(int codigo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newcontentValues = new ContentValues();

        newcontentValues.put(NOMBRE_ATRIBUTO_PRODUCTOS_ESTADO, Producto.STATUS_IN);

        String campoActualizar = "CODIGO = ?";
        String[] argumentosActualizar = {String.valueOf(codigo)};

        return db.update(NOMBRE_TABLA_PRODUCTOS, newcontentValues, campoActualizar, argumentosActualizar);
    }
    public ArrayList<Producto> Listar(){
        Producto producto = null;
        Date date;
        ArrayList<Producto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String q  = "SELECT "+ NOMBRE_ATRIBUTO_PRODUCTOS_CODIGO + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_NOMBRE + ", "
                + NOMBRE_LLAVE_FORANEA + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOC + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_IVA + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_PRECIOV + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_FECHAV + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_CANTIDAD + ", "
                + NOMBRE_ATRIBUTO_PRODUCTOS_ESTADO
                +" FROM PRODUCTOS" + ";";
        Cursor reg = db.rawQuery(q,null);
        if(reg.moveToFirst()){
            do{
                String fechar = reg.getString(6);
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    date = format.parse(fechar);
                    producto = new Producto(reg.getInt(0),reg.getString(1),reg.getInt(2),reg.getDouble(3),reg.getDouble(4),reg.getDouble(5),date ,reg.getInt(7),reg.getString(8));
                    lista.add(producto);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while(reg.moveToNext());
        }
        return lista;

    }
    public void generarCategorias(){
        if(getCategorias().size()==0){
           InsertarCategorias();
        }

    }
}


