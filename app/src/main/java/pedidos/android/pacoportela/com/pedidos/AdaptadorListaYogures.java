package pedidos.android.pacoportela.com.pedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Creada por Francisco Portela Henche el 10/02/2020.
 * Esta clase es un adaptador para una clase ListView. Define como es cada fila de las lista
 * que se visualiza en dicha ListView.
 */
public class AdaptadorListaYogures extends ArrayAdapter<Dato> implements Serializable{

    /**
     * Constructor de la clase.
     * @param context el contexto donde se visualiza la ListView. No puede ser null.
     * @param objetos Los objetos a representar en el ListView. No puede ser null.
     */
    public AdaptadorListaYogures(Context context, List<Dato> objetos){
        super(context, 0, objetos);
    }

    /**
     * Obtenga una vista que muestre los datos en la posición especificada en el conjunto de datos.
     * Puede crear una Vista manualmente o inflarla desde un archivo de diseño XML. Cuando la Vista
     * está inflada, la Vista principal (GridView, ListView ...) aplicará los parámetros de diseño
     * predeterminados a menos que use LayoutInflater.inflate (int, android.view.ViewGroup, boolean)
     * para especificar una vista raíz y para evitar la conexión al raíz.
     *
     * @param posicion La posición del elemento dentro del conjunto de datos del adaptador
     * del elemento cuya vista deseamos.
     * @param convertView puede ser null.
     * @param parent nunca puede ser null.
     * @return la vista que se mostrará en el ListView. No puede ser null.
     */
    public View getView(int posicion, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemLista = convertView;
        if(convertView == null){
            itemLista = inflater.inflate(R.layout.layout_fila_tabla, parent, false);
        }

        final Dato yogur = (Dato)getItem(posicion);

        TextView nombreYogur = (TextView)itemLista.findViewById((R.id.tvNombreYogur));
        final EditText ctaPedida = (EditText)itemLista.findViewById((R.id.etCtaPedida));

        EditText ctaDevol = (EditText)itemLista.findViewById(R.id.etCtaDevolucion);

        nombreYogur.setText(yogur.getNombre().substring(7));
        ctaPedida.setText(yogur.getCtaPedida());
        ctaDevol.setText(yogur.getCtaDevolucion());
        ctaPedida.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et = (EditText) v;
                yogur.setCtaPedida(et.getText().toString());
            }
        });
        ctaDevol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et = (EditText)v;
                yogur.setCtaDevolucion(et.getText().toString());
            }
        });
        return itemLista;
    }
}
