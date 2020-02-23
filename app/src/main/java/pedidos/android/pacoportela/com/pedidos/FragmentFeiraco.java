package pedidos.android.pacoportela.com.pedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Creada por Francisco Portela Henche el 10/02/2020.
 * Esta clase presenta una parte de la interfaz que se va a visualizar en la app. Concretamente
 * la parte donde se visualiza lo que hay que pedirle a Feiraco.
 */

public class FragmentFeiraco extends Fragment {
    View view;
    EditText et;

    /**
     * Contructor.
     */
    public FragmentFeiraco() {
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
        view = inflater.inflate(R.layout.layout_feiraco_fragment, container, false);
        et = (EditText)view.findViewById(R.id.editTextObservaciones);
        return view;
    }

    /**
     * Este método recoge los datos de los distintos elementos de la interfaz y los devuelve en
     * un String.
     * @return un String con los datos recogidos del formulario.
     */
    public String recogerDatosFeiraco(){
        String datos = "";
        // recogemos los datos del Spinner de la leche desnatada.
        Spinner spinnerDesna = (Spinner)view.findViewById(R.id.spinnerDesna);
        String leche = (String)spinnerDesna.getSelectedItem();
        // si está seleccionado algo diferente de 'nada' lo añadimos a los datos a devolver.
        if(!leche.equalsIgnoreCase("nada")){
            datos = datos + "Desnatada brick 1 litro: " + (String)spinnerDesna.getSelectedItem();
        }
        // recogemos los datos del Spinner de la leche entera.
        Spinner spinnerEntera = (Spinner)view.findViewById(R.id.spinnerEntera);
        leche = (String)spinnerEntera.getSelectedItem();
        // si está seleccionado algo diferente de 'nada' lo añadimos a los datos a devolver.
        if(!leche.equalsIgnoreCase("nada")){
            datos = datos + "\nEntera brick 1 litro: " + (String)spinnerDesna.getSelectedItem();
        }
        // recogemos los datos del Spinner de la leche semidesnatada.
        Spinner spinnerSemi = (Spinner)view.findViewById(R.id.spinnerSemi);
        leche = (String)spinnerSemi.getSelectedItem();
        // si está seleccionado algo diferente de 'nada' lo añadimos a los datos a devolver.
        if(!leche.equalsIgnoreCase("nada")){
            datos = datos + "\nSemidesnatada brick 1 litro: " + (String)spinnerDesna.getSelectedItem();
        }
        // recogemos los datos del EditText 'unicla'
        EditText editTextUnicla = (EditText)view.findViewById(R.id.editTextUnicla);
        // Si contiene algo lo añadimos a los datos a devolver.
        if(editTextUnicla.getText().length() > 0){
            datos = datos + "\nUnicla semi: " + editTextUnicla.getText() + " cajas";
        }
        // recogemos los datos del EditText 'sin lactosa'
        EditText editTextLactosa = (EditText)view.findViewById(R.id.editTextLactosa);
        // Si contiene algo lo añadimos a los datos a devolver.
        if(editTextLactosa.getText().length() > 0){
            datos = datos + "\nSin lactosa semi: " + editTextLactosa.getText() + " cajas";
        }
        // comprobamos el CheckBox 'queso'
        CheckBox checkBoxQueso = (CheckBox)view.findViewById(R.id.checkBoxQueso);
        // si está marcado añadimos un caja de queso a los datos a devolver.
        if(checkBoxQueso.isChecked()){
            datos = datos + "\nUna caja de queso en lonchas";
        }
        // comprobamos el CheckBox 'nata grande'
        CheckBox checkBoxNataGde = (CheckBox)view.findViewById(R.id.checkBoxNata_grande);
        // si está marcado añadimos un caja de nata de un litro a los datos a devolver.
        if(checkBoxNataGde.isChecked()){
            datos = datos + "\nUna caja de Nata azul de 1 litro";
        }
        // comprobamos el CheckBox 'nata pequeña'
        CheckBox checkBoxNataPq = (CheckBox)view.findViewById(R.id.checkBoxNata_peque);
        // si está marcado añadimos un caja de nata de 200 ml. a los datos a devolver.
        if(checkBoxNataPq.isChecked()){
            datos = datos + "\nUna caja de Nata azul de 200 ml.";
        }
        // obtenemos los escrito en el EditText 'observaciones'
        EditText editTextObservaciones = (EditText)view.findViewById(R.id.editTextObservaciones);
        // si contiene algo lo añadimos a los datos a devolver.
        if(editTextObservaciones.getText().length() > 0){
            datos = datos + "\n" + editTextObservaciones.getText();
        }
        // Añadimos un saludo de despedida.
        datos = datos + "\n\nMuchas gracias y un saludo.";
        // devolvemos los datos.
        return datos;
    }


    /**
     * Este método devuelve una referencia al EditText 'observaciones' del formulario.
     * @return una referencia al EditText.
     */
    public EditText getObservaciones(){
        return et;
    }
}

