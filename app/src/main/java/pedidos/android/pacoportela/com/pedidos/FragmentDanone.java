package pedidos.android.pacoportela.com.pedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Creada por Francisco Portela Henche el 10/02/2020.
 * Esta clase presenta una parte de la interfaz que se va a visualizar en la app. Concretamente
 * la parte donde se visualiza lo que hay que pedirle a Danone.
 */

public class FragmentDanone extends android.support.v4.app.Fragment{
    View view;

    /**
     * Constructor.
     */
    public FragmentDanone() {
    }

    /**
     * Método que se ejecuta cuando creamos la vista por primera vez.
     * @param inflater el inflater que nos permite inflar la vista desde un recurso XML.
     * @param container el container donde se visualizará.
     * @param savedInstanceState un Bundle que contiene información.
     * @return la vista que hemos creado.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_danone_fragment, container, false);

        ListView listViewDanone = (ListView)view.findViewById(R.id.listViewDanone);
        listViewDanone.setAdapter(ActividadPrincipal.adaptadorListaYogures);
        listViewDanone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ActividadPrincipal actividadPrincipal = (ActividadPrincipal)getActivity();
                boolean b = actividadPrincipal.modificarOborrarNombre(position);
                return b;
            }
        });
        return view;
    }

    /**
     * Este método recorre la lista de datos con los yogures de Danone y va añadiendo a un String
     * los que contienen alguna información.
     * @return un String con los datos del pedido a Danone.
     */
    public String recogerDatosDanone(){
        String datos = "";
        String pedido = "PEDIDO\n\n";
        String devolucion = "\nDEVOLUCIÓN\n\n";
        String cod = "";
        String nom= "";
        String ped = "";
        String dev = "";
        // recorremos la lista de Datos.
        for(Dato d : ActividadPrincipal.listaDatos){
            // el Dato tiene este formato 123456-3-Nombre del producto.
            // separamos el código del producto. Las seis primeras posiciones del nombre.
            cod = d.getNombre().substring(0,6);
            // separamos el nombre del producto. A partir de la octava posición.
            nom = d.getNombre().substring(8);
            // obtenemos la cantidad pedida.
            ped = d.getCtaPedida();
            // obtenemos la cantidad de devolución.
            dev = d.getCtaDevolucion();
            // si la longitud de la cantidad pedida es mayor que cero añadimos un fila al pedido.
            if(ped.length() > 0){
                pedido += cod + nom + ": " + ped + "\n";
            }
            // si la longitud de la devolución es mayor que cero añadimos un fila a la devolución.
            if(dev.length() > 0){
                devolucion += cod + nom + ": " + dev + "\n";
            }
        }
        // Comprobamos si la longitud del String 'pedido' es superior a ocho. Esto es así porque
        // la cabecera que pusimos PEDIDO\n\n tiene ocho caracteres. Si es así es que contiene
        // alguna fila con pedido. Añadimos los pedidos a los datos a recoger.
        if(pedido.length() > 8){
            datos += pedido;
        }
        // Comprobamos si la longitud del String 'devolucion' es superior a trece. Esto es así porque
        // la cabecera que pusimos DEVOLUCION\n\n tiene trece caracteres. Si es así es que contiene
        // alguna fila con devolución. Añadimos la devolución a los datos a recoger.
        if(devolucion.length() > 13){
            datos += devolucion;
        }
        // Comprobamos si no hay datos para enviar
        if(datos.length() == 0){
            // Añadimos un mensaje de aviso de que no hay pedido esta semana.
            datos += "Esta semana no tenemos nada que pedir.";
        }
        // Añadimos un saludo de despedida.
        datos += "\n\nMuchas gracias y un saludo.";
        // Devolvemos los datos.
        return datos;
    }
}
