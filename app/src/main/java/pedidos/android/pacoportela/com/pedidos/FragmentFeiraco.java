package pedidos.android.pacoportela.com.pedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Paco on 06/10/2019.
 */

public class FragmentFeiraco extends Fragment {
    View view;
    EditText et;
    public FragmentFeiraco() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_feiraco_fragment, container, false);
        et = (EditText)view.findViewById(R.id.editTextObservaciones);
        return view;
    }

    public String recogerDatosFeiraco(){
        String datos = "";
        Spinner spinnerDesna = (Spinner)view.findViewById(R.id.spinnerDesna);
        String leche = (String)spinnerDesna.getSelectedItem();
        if(!leche.equalsIgnoreCase("nada")){
            datos = datos + "Desnatada brick 1 litro: " + (String)spinnerDesna.getSelectedItem();
        }

        Spinner spinnerEntera = (Spinner)view.findViewById(R.id.spinnerEntera);
        leche = (String)spinnerEntera.getSelectedItem();
        if(!leche.equalsIgnoreCase("nada")){
            datos = datos + "\nEntera brick 1 litro: " + (String)spinnerDesna.getSelectedItem();
        }

        Spinner spinnerSemi = (Spinner)view.findViewById(R.id.spinnerSemi);
        leche = (String)spinnerSemi.getSelectedItem();
        if(!leche.equalsIgnoreCase("nada")){
            datos = datos + "\nSemidesnatada brick 1 litro: " + (String)spinnerDesna.getSelectedItem();
        }

        EditText editTextUnicla = (EditText)view.findViewById(R.id.editTextUnicla);
        if(editTextUnicla.getText().length() > 0){
            datos = datos + "\nUnicla semi: " + editTextUnicla.getText() + " cajas";
        }
        EditText editTextLactosa = (EditText)view.findViewById(R.id.editTextLactosa);
        if(editTextLactosa.getText().length() > 0){
            datos = datos + "\nSin lactosa semi: " + editTextLactosa.getText() + " cajas";
        }
        CheckBox checkBoxQueso = (CheckBox)view.findViewById(R.id.checkBoxQueso);
        if(checkBoxQueso.isChecked()){
            datos = datos + "\nUna caja de queso en lonchas";
        }
        CheckBox checkBoxNataGde = (CheckBox)view.findViewById(R.id.checkBoxNata_grande);
        if(checkBoxNataGde.isChecked()){
            datos = datos + "\nUna caja de Nata azul de 1 litro";
        }
        CheckBox checkBoxNataPq = (CheckBox)view.findViewById(R.id.checkBoxNata_peque);
        if(checkBoxNataPq.isChecked()){
            datos = datos + "\nUna caja de Nata azul de 200 ml.";
        }
        EditText editTextObservaciones = (EditText)view.findViewById(R.id.editTextObservaciones);
        if(editTextObservaciones.getText().length() > 0){
            datos = datos + "\n" + editTextObservaciones.getText();
        }
        datos = datos + "\n\nMuchas gracias y un saludo.";
        return datos;
    }



    public EditText getObservaciones(){
        return et;
    }
}

