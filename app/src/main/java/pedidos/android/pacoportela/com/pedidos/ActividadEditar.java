package pedidos.android.pacoportela.com.pedidos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Creada por Francisco Portela Henche el 10/02/2020.
 * Esta clase presenta una Activity que nos permitirá editar un producto de la lista de yogures
 * de Danone ó crear uno nuevo. También la usaremos para poder modificar tanto la dirección de
 * correo electrónico como el asunto del correo de cualquiera de los dos proveedores.
 */
public class ActividadEditar extends AppCompatActivity implements View.OnClickListener{
    EditText etNombre;
    TextView tvTextoAyuda;
    int posicion;
    String origen;
    String proveedor;
    String direccionCorreo;
    String asunto;
    String texto_ayuda_modificar = "<b>- Formato para la introducción de un nuevo producto: " +
            "<br><br>123456-4-Nombre.</b><br><br>Primero se escribe el código de proveedor " +
            "del producto, seguido de un guión, la cantidad que tiene que haber en la tienda" +
            ", otro guión y finalmente el nombre del producto. Hay que tener en cuenta que " +
            "el código del proveedor debe de tener seis dígitos. En el caso de que sea menor" +
            " se rellenarán los primeros dígitos con espacios en blanco. Por ejemplo, si " +
            "el código es 2594 escribiremos espacio, espacio, 2594. Esto se hace así porque " +
            "el programa separa posteriormente la cadena que estamos introduciendo ahora " +
            "para mostrar los datos de forma correcta en la aplicación y también a la hora " +
            "de enviar el pedido al proveedor.";

    /**
     * Método que se ejecuta cuando creamos la Activity por primera vez.
     * @param savedInstanceState un Bundle con datos guardados.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_editar);
        etNombre = (EditText)findViewById(R.id.etNombreYogur);
        tvTextoAyuda = (TextView) findViewById(R.id.tvTextoAyuda);
        Button botonAceptar = (Button)findViewById(R.id.botonAceptar);
        botonAceptar.setOnClickListener(this);
        Button botonCancelar = (Button)findViewById(R.id.botonCancelar);
        botonCancelar.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        posicion = bundle.getInt("posicion");
        origen = bundle.getString("origen");
        proveedor = bundle.getString("proveedor");
        direccionCorreo = bundle.getString("correo");
        asunto = bundle.getString("asunto");

        // comprobamos quien ha levantado la activity y actuamos en consecuencia.
        if(origen.equalsIgnoreCase("modificar")){
            setTitle("Modificar ó Borrar yogur");
            etNombre.setText(nombre);
            etNombre.selectAll();
            tvTextoAyuda.setText(Html.fromHtml(texto_ayuda_modificar));
        }
        else if(origen.equalsIgnoreCase("anadir")){
            setTitle("Añadir producto");
            etNombre.setText("");
            etNombre.requestFocus();
            tvTextoAyuda.setText(Html.fromHtml(texto_ayuda_modificar));
        }
        else if(origen.equalsIgnoreCase("correo")){
            setTitle("Correo electrónico de " + proveedor);
            etNombre.setText(direccionCorreo);
            etNombre.selectAll();
            String texto_ayuda_correo =
                    "<b>- Modificar correo electrónico " + proveedor + ": </b><br><br>" +
                    "Introduzca la nueva dirección de correo y pulse <b>ACEPTAR.</b>";
            tvTextoAyuda.setText(Html.fromHtml(texto_ayuda_correo));
        }
        else if(origen.equalsIgnoreCase("asunto")){
            setTitle("Asunto del proveedor " + proveedor);
            etNombre.setText(asunto);
            etNombre.selectAll();
            String texto_ayuda_asunto =
                    "<b>- Modificar asunto del correo de " + proveedor + ": </b><br><br>" +
                            "Introduzca el nuevo asunto y pulse <b>ACEPTAR.</b>";
            tvTextoAyuda.setText(Html.fromHtml(texto_ayuda_asunto));
        }
    }

    /**
     * En este método crearemos un nuevo producto Danone.
     */
    private void anadirYogur(){
        String nombre = etNombre.getText().toString();
        if(nombre.length() == 0){
            Toast toast = Toast.makeText
                    (getApplicationContext(), "Escriba un nombre por favor", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(nombre.length() < 9 || !nombre.contains("-")){
            Toast toast = Toast.makeText
                    (getApplicationContext(), "Compruebe el formato del nombre.",
                            Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        List<Dato> lista = ActividadPrincipal.listaDatos;
        Dato d = new Dato(nombre, "", "");
        lista.add(d);
        Collections.sort(lista, new Comparator(){
            @Override
            public int compare(Object a, Object b) {
                Dato d1 = (Dato)a;
                Dato d2 = (Dato)b;
                return d1.getNombre().substring(9).compareTo(d2.getNombre().substring(9));
            }
        });
        ActividadPrincipal.adaptadorListaYogures.notifyDataSetChanged();
        finalizar();
    }

    /**
     * En este método modificamos un producto Danone.
     */
    private void modificarYogur(){
        String nombre = etNombre.getText().toString();
        final List<Dato> lista = ActividadPrincipal.listaDatos;
        final Dato d = lista.get(posicion);
        if(nombre.length() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea borrar este artículo?")
                    .setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lista.remove(d);
                    Collections.sort(lista, new Comparator(){
                        @Override
                        public int compare(Object a, Object b) {
                            Dato d1 = (Dato)a;
                            Dato d2 = (Dato)b;
                            return d1.getNombre().substring(9).compareTo(d2.getNombre().substring(9));
                        }
                    });
                    ActividadPrincipal.adaptadorListaYogures.notifyDataSetChanged();
                    finalizar();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else{
            d.setNombre(nombre);
            ActividadPrincipal.adaptadorListaYogures.notifyDataSetChanged();
            finalizar();
        }
    }

    /**
     * En este método modificamos la dirección de correo electrónico de un proveedor.
     * @param proveedor el nombre del proveedor.
     */
    private void modificarCorreo(String proveedor){
        String nuevoCorreo = etNombre.getText().toString();
        if(proveedor.equalsIgnoreCase("feiraco")){
            ActividadPrincipal.CORREO_FEIRACO = nuevoCorreo;
        }
        else {
            ActividadPrincipal.CORREO_DANONE = nuevoCorreo;
        }
        finalizar();
    }

    /**
     * En este método modificamos el asunto del correo electrónico a un proveedor.
     * @param proveedor el nombre del proveedor.
     */
    private void modificarAsunto(String proveedor){
        String nuevoAsunto = etNombre.getText().toString();
        if(proveedor.equalsIgnoreCase("danone")){
            ActividadPrincipal.ASUNTO_DANONE = nuevoAsunto;
        }
        else{
            ActividadPrincipal.ASUNTO_FEIRACO = nuevoAsunto;
        }
        finalizar();
    }

    /**
     * Método para finalizar la Activity.
     */
    private void finalizar(){
        finish();
    }

    /**
     * Método que se ejecuta cuando pulsamos en uno de los botones.
     * @param v la vista donde se produce el evento de click.
     */
    @Override
    public void onClick(View v) {
        Button boton = (Button)v;
        // si se pulsa el botón aceptar comprobamos de donde procede el evento y lo dirigimos al
        // método adecuado.
        if(boton.getText().toString().equalsIgnoreCase("aceptar")){
            if(origen.equalsIgnoreCase("modificar")){
                modificarYogur();
            }
            else if(origen.equalsIgnoreCase("anadir")){
                anadirYogur();
            }
            else if(origen.equalsIgnoreCase("correo")){
                modificarCorreo(proveedor);
            }
            else if(origen.equalsIgnoreCase("asunto")){
                modificarAsunto(proveedor);
            }
        }
        // si se pulsa el botón cancelar salimos de la activity.
        else if(boton.getText().toString().equalsIgnoreCase("cancelar")){
            finalizar();
        }
    }
}
