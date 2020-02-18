package pedidos.android.pacoportela.com.pedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActividadAyuda extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_ayuda);
        TextView textView = (TextView)findViewById(R.id.tvAyuda);
        Button boton = (Button)findViewById(R.id.botonAyuda);
        boton.setOnClickListener(this);
        String textoAyuda = "<br><b> - INTRODUCIR CANTIDADES: </b>" +
                "Pulse sobre cualquier fila de la lista de yogures para introducir las cantidades " +
                "a pedir y de devolución.<br><br><b> - MODIFICAR O BORRAR UN YOGUR: </b>" +
                "Pulse prolongadamente sobre la cantidad a pedir en una fila de la lista para poder " +
                "modificar un yogur. Para borrarlo, borre el nombre y pulse <b>ACEPTAR</b>.<br><br>" +
                "<b> - AÑADIR UN YOGUR: </b>Pulse en el menú de la esquina superior derecha " +
                "de la pantalla y seleccione <b>Añadir yogur</b>. Escriba el nombre y pulse " +
                "<b>ACEPTAR.</b><br><br><b> - BORRAR LAS CANTIDADES DEL PEDIDO: </b>Pulse el menú y " +
                "seleccione <b>Borrar pedido</b>.<br><br><b> - ENVIAR EL PEDIDO POR CORREO ELECTRONICO:" +
                " </b>Vaya al menú y seleccione <b>Enviar correo.</b> Se enviará un correo al proveedor" +
                " en el que se encuentre posicionado con los datos que haya introducido.<br><br><b>" +
                " - CAMBIAR LA DIRECCION DE CORREO ELECTRONICO DE UN PROVEEDOR:</b> Pulse en el menú" +
                " <b>Cambiar dirección correo</b>, introduzca la nueva dirección y pulse <b>ACEPTAR</b>." +
                "<br><br><b> - CAMBIAR EL ASUNTO DEL CORREO ELECTRONICO: </b>Pulse <b>Cambiar asunto " +
                "correo</b> en el menú, introduzca el nuevo asunto a incluir en el correo y pulse " +
                "<b>ACEPTAR</b>.";
        textView.setText(Html.fromHtml(textoAyuda));
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}