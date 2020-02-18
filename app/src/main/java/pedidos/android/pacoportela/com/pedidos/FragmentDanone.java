package pedidos.android.pacoportela.com.pedidos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paco on 06/10/2019.
 */

public class FragmentDanone extends android.support.v4.app.Fragment{
    View view;
    public FragmentDanone() {
    }

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

    public String recogerDatosDanone(){
        String datos = "";
        String pedido = "PEDIDO\n\n";
        String devolucion = "\nDEVOLUCIÓN\n\n";
        String cod = "";
        String nom= "";
        String ped = "";
        String dev = "";
        for(Dato d : ActividadPrincipal.listaDatos){
            cod = d.getNombre().substring(0,6);
            nom = d.getNombre().substring(8);
            ped = d.getCtaPedida();
            dev = d.getCtaDevolucion();
            if(ped.length() > 0){
                pedido += cod + nom + ": " + ped + "\n";
            }
            if(dev.length() > 0){
                devolucion += cod + nom + ": " + dev + "\n";
            }
        }
        if(pedido.length() > 8){
            datos += pedido;
        }
        if(devolucion.length() > 13){
            datos += devolucion;
        }
        return datos;
    }
}
