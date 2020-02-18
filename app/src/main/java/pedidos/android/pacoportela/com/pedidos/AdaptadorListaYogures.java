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
 * Created by Francisco Portela Henche on 10/02/2020.
 */
public class AdaptadorListaYogures extends ArrayAdapter<Dato> implements Serializable{

    public AdaptadorListaYogures(Context context, List<Dato> objetos){
        super(context, 0, objetos);
    }

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
