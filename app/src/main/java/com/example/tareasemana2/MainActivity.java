package com.example.tareasemana2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
//se debe importar la libreria para escribir crear una variable de tipo edittext
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//se crea la clase automaticamente al crear la actividad
public class MainActivity extends AppCompatActivity {
//se crea tres varaibles de tipo EditText
    private EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //aqui recogemos los datos que el usuario ingrese en los edittext
        /*lo que quiere decir que todo lo que se ingrese en los edittext
        * se almacenara en las variables que creamos*/
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
    }
/*creamos un evento para el boton de alta o guardar*/
    public void alta(View v) {
        //se crea la instanciacion de la base de datos y se le da la configuracion
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        //le decimos el tipo de escritira que tendra la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        //creamos las variables de tipo cadena, porque son caracteres los que recibira
        //y recogemos lo que el usuario ingresa en los edittext
        String cod = et1.getText().toString();
        String descri = et2.getText().toString();
        String pre = et3.getText().toString();

        //instanciamos la clase contenvalues con el nombre registro el cual se utilizara
        //para referirnos a esta clase
        ContentValues registro = new ContentValues();
        //mediante el metodo put tomara los datos de las variables y lo almacenara en la columna que le
        //corresponde segun se ha especificado
        registro.put("codigo", cod);
        registro.put("descripcion", descri);
        registro.put("precio", pre);
        //mediante el evento insert vamos a insertar en la tabla articulos los datos contenidos en la clase
        //registro
        bd.insert("articulos", null, registro);
        //cerramos la bd
        bd.close();
        //y por ultimo vacia los EditText
        et1.setText("");
        et2.setText("");
        et3.setText("");

        //y al final le envia un mensaje al usuario para decirle que sus registros han sido agregados
        Toast.makeText(this, "Se cargaron los datos del artículo",
                Toast.LENGTH_SHORT).show();
    }
//aqui se crea un evento para el boton consultar por codigo, el cual nos permitira hacer una consulta
    //ingresando el codigo del registro
    public void consultaporcodigo(View v) {
        //abrimos la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        //le especificamos el metodo de lectura
        SQLiteDatabase bd = admin.getWritableDatabase();

        //aqui creamos una variable para recoger los datos del edittext del codigo, debido a que la consulta
        //es por el codigo ingresado
        String cod = et1.getText().toString();
        //realizamos una busqueda en la base de datos
        Cursor fila = bd.rawQuery(
                //seleccionando el campo descripcion y precio de la tabla articulos y comparandolo
                //con lo que el usuario ingreso en el codigo
                "select descripcion,precio from articulos where codigo=" + cod, null);
        //si existe una conincidencia tomara los datosde la columna 0 y la columna 1 y los mostrara en
        //los edittext 2 y 3
        if (fila.moveToFirst()) {
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
        } else
            //sino le dira al usuario que el registro no existe
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        //y cerrara la base de datos
        bd.close();
    }
//al igual que en el anterior se realizara una consulta solo que esta vez se hara a traves
    //de la descripcion del dato
    public void consultapordescripcion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descri = et2.getText().toString();
        Cursor fila = bd.rawQuery(
                "select codigo,precio from articulos where descripcion='" + descri + "'", null);
        if (fila.moveToFirst()) {
            et1.setText(fila.getString(0));
            et3.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un artículo con dicha descripción",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }
//en este public void se crea el evento para eliminar un registro
    public void bajaporcodigo(View v) {
        //abrimos la base de datos de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        //le especificamos el tipo de lectura
        SQLiteDatabase bd = admin.getWritableDatabase();
        //creamos una variable de tipo string y almacenamos en ella el dato ingresado en la
        //variable codigo
        String cod= et1.getText().toString();

        //se crea un evento int con el nombre cant, este nos permitira eliminar de la base de datos
        //un registro que coincida con el codigo en la tabla articulos
        int cant = bd.delete("articulos", "codigo=" + cod, null);
        //cerramos la base de datos
        bd.close();
        //vaciamos los edittext
        et1.setText("");
        et2.setText("");
        et3.setText("");
        //si en el int cant hay un registro que coincide se eliminara el registro y se le enviara un
        //mensaje al usuario confirmando la eliminacion del registro
        if (cant == 1)
            Toast.makeText(this, "Se borró el artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        else
            //sino se le enviara otro mensaje diciendo que no hay coincidencia con el registro
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
    }
//en este evento se realizara una modificacion o actualizacion a la base de datos
    public void modificacion(View v) {
        //abrimos la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        //espeficamos el tipo de lectura
        SQLiteDatabase bd = admin.getWritableDatabase();
        //creamos las variables para recoger los datos que ingresa el usuario
        String cod = et1.getText().toString();
        String descri = et2.getText().toString();
        String pre = et3.getText().toString();

        //instanciamos la clase contentvalues mediante el nombre resgistro
        ContentValues registro = new ContentValues();
        //agregamos los datos de la variables a los campos en la clase registro
        registro.put("codigo", cod);
        registro.put("descripcion", descri);
        registro.put("precio", pre);
        //mediante el int cant actualizarmos los registros de la tabla articulos en la bd
        int cant = bd.update("articulos", registro, "codigo=" + cod, null);
        //cerramos la base de datos
        bd.close();
        //si se actualiza un registro le diremos al usuario que el registro fue modificado
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            //sino que no hay un registro que coincida con los datos ingresados
            Toast.makeText(this, "no existe un artículo con el código ingresado",
                    Toast.LENGTH_SHORT).show();
    }
}