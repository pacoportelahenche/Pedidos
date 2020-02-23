package pedidos.android.pacoportela.com.pedidos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Creada por Francisco Portela Henche el 10/02/2020.
 * Esta clase es la actividad principal de la app.
 */

public class ActividadPrincipal extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentDanone fragmentDanone;
    private FragmentFeiraco fragmentFeiraco;
    private ViewPagerAdapter adapter;
    public static List<Dato> listaDatos;
    public static AdaptadorListaYogures adaptadorListaYogures;

    public static String CORREO_FEIRACO = "carlosdemiguel@mafcar.com";
    public static String CORREO_DANONE = "pedidosdan@pedidosdan.com";
    public static String ASUNTO_FEIRACO = "Pedido economato ELCO Coruña";
    public static String ASUNTO_DANONE = "ELCO 350053637";

    /**
     * Método que es llamado cuando se lanza la activity. Es donde creamos los objetos que vamos a
     * utilizar.
     * @param savedInstanceState Bundle: si la activity está siendo reinicializada después de
     *        haber sido ocultada entonces este Bundle contiene los datos que se hubiesen guardado
     *        en onSaveInstanceState(Bundle). En cualquier otro caso su valor es null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_principal);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentDanone = new FragmentDanone();
        fragmentFeiraco = new FragmentFeiraco();
        adapter.addFragment(fragmentDanone, "Danone");
        adapter.addFragment(fragmentFeiraco, "Feiraco");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getDatos();
    }

    /**
     * Método que es llamado cuando el usuario deja de interactuar con la activity.
     * Aquí guardamos los datos de la aplicación.
     */
    @Override
    protected void onPause(){
        super.onPause();
        guardarDatos();
    }

    /**
     * Método que devuelve el nombre del fragment en el que estamos.
     * @return String: el nombre del fragment activo.
     */
    public String getNombreFragmentActivo(){
        return String.valueOf(adapter.getPageTitle(viewPager.getCurrentItem()));
    }

    /**
     * Método que nos informa si estamos en el fragment de Feiraco.
     * @return boolean: true si estamos en el fragment Feiraco.
     */
    public boolean estoyEnFeiraco(){
        return getNombreFragmentActivo().equalsIgnoreCase("feiraco");
    }

    /**
     * Método en el que obtenemos todos los datos guardados en las SharedPreferences de nuestra app.
     */
    private void getDatos() {
        /* si aún existe en memoria la lista de datos salimos del método.
        Si no lo hacemos así duplica los datos que se muestran en el ListView.
         */
        if(listaDatos != null) return;
        // obtenemos las SharedPreferences guardadas de esta Activity donde están los datos guardados.
        SharedPreferences datos = getDatosGuardados();

        // extraemos los datos de los yogures del Set de datos.
        Set<String> nombres = getSetNombresYogures(datos);

        // cargamos los datos de los yogures en el arrayList listaDatos.
        rellenarListaDatos(nombres);

        // extraemos los datos de las direcciones de correo de los proveedores.
        String correoDanone = datos.getString("correo_danone", null);
        if(correoDanone != null) CORREO_DANONE = correoDanone;
        String correoFeiraco = datos.getString("correo_feiraco", null);
        if(correoFeiraco != null) CORREO_FEIRACO = correoFeiraco;
        // extraemos los datos de los asuntos
        String asuntoDanone = datos.getString("asunto_danone", null);
        if(asuntoDanone != null)ASUNTO_DANONE  = asuntoDanone;
        String asuntoFeiraco = datos.getString("asunto_feiraco", null);
        if(asuntoFeiraco != null) ASUNTO_FEIRACO = asuntoFeiraco;
    }

    /**
     * Método con el que obtenemos las SharedPreferences de nuestra app.
     * @return SharedPreferences: las SharedPreferences guardadas.
     */
    private SharedPreferences getDatosGuardados(){
        SharedPreferences datosGuardados = getPreferences(Context.MODE_PRIVATE);
        return datosGuardados;
    }

    /**
     * Método con el que obtenemos los datos de los yogures que están guardados en un Set de
     * Strings  con el nombre 'nombres' en las SharedPreferences.
     * @param datos SharedPreferences: las SharedPreferences donde están los datos guardados.
     * @return un Set de Strings con todos los nombres de los yogures de Danone.
     */
    private Set<String> getSetNombresYogures(SharedPreferences datos){
        Set<String> nombres = datos.getStringSet("nombres", null);
        return nombres;
    }

    /**
     * Método con el que rellenamos la ArrayList 'listaDatos' con los nombres de los yogures.
     * @param nombres Set: el Set de Strings que contiene todos los nombres de los yogures.
     */
    private void rellenarListaDatos(Set<String> nombres){
        if(listaDatos == null){
            listaDatos = new ArrayList<Dato>();
        }
        /* en el caso de que fuese la primera vez que ejecutamos el programa, no habría ningunas
        SharedPreferences guardadas, por lo que no existirían datos guardados en el movil. En este
        caso cargaríamos la lista de datos con un objeto DatosDanone que contiene los datos
        de los yogures. Aún cuando esta información puede estar obsoleta, desde la app es posible
        modificarlos y una vez hecho, se guardarán actualizados en las SharedPreferences, que son
        lasque se utilizarán para cargar los datos cada vez que iniciemos la app después de su
        primer uso.
         */
        if (nombres == null) {
            listaDatos = new DatosDanone().getListaDatos();
            //adaptadorListaYogures = new AdaptadorListaYogures(this, listaDatos);
        /* en caso de que los datos no sean null iteramos sobre ellos y vamos extrayendo cada
        nombre y creando los objetos Dato que le pasaremos a nuestra lista de datos.
         */
        } else {
            Iterator<String> i = nombres.iterator();

            String nom = "";
            while (i.hasNext()) {
                Dato d = new Dato();
                nom = i.next();
                d.setNombre(nom);
                d.setCtaPedida("");
                d.setCtaDevolucion("");
                listaDatos.add(d);
            }
        }
        // ordenamos alfabéticamente la lista
        Collections.sort(listaDatos, new Comparator(){
            @Override
            public int compare(Object a, Object b) {
                Dato d1 = (Dato)a;
                Dato d2 = (Dato)b;
                return d1.getNombre().substring(9).compareTo(d2.getNombre().substring(9));
            }
        });
        // añadimos la lista con los datos al adaptador de la ListView que los va a mostrar.
        adaptadorListaYogures = new AdaptadorListaYogures(this, listaDatos);
    }

    /**
     * Método para guardar los datos de la aplicación en un objeto SharedPreferences.
     */
    private void guardarDatos(){
        // guardamos los datos de los yogures que están en la listaDatos en un HashSet
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  preferences.edit();
        Set set = new HashSet<String>();
        for(int i = 0; i < listaDatos.size(); i++){
            set.add(listaDatos.get(i).getNombre());
        }
        editor.putStringSet("nombres", set);

        // guardamos las direcciones de correo de los proveedores
        editor.putString("correo_danone", CORREO_DANONE);
        editor.putString("correo_feiraco", CORREO_FEIRACO);

        // guardamos los asuntos para el correo
        editor.putString("asunto_danone", ASUNTO_DANONE);
        editor.putString("asunto_feiraco", ASUNTO_FEIRACO);

        // actualizammos los datos en las SharedPreferences
        editor.commit();
    }

    /**
     * Método que se ejecuta cuando pulsamos prolongadamente en una fila de la lista de yogures.
     * Levanta la activity ActividadEditar y le pasa los datos del elemento de dicha fila para poder
     * editarlo ó borrarlo.
     * @param position int: la posicion del elemento en la listaDatos.
     */
    public boolean modificarOborrarNombre(int position){
        Intent intent = new Intent(getBaseContext(), ActividadEditar.class);
        intent.putExtra("nombre", listaDatos.get(position).getNombre());
        intent.putExtra("posicion", position);
        intent.putExtra("origen", "modificar");
        startActivity(intent);
        return false;
    }

    /**
     * Método utilizado para crear el menú de la app
     * @param menu el menú de la app
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú; esto añade items a la barra de acción si es que los hay.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    /**
     * Método que se ejecuta cuando seleccionamos un item del menú
     * @param item el MenuItem que hemos pulsado.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case(R.id.urkel):
                mostrarToastUrkel();
                break;
            case(R.id.enviar):
                if(estoyEnFeiraco()){
                    enviarCorreo
                            (CORREO_FEIRACO, ASUNTO_FEIRACO, fragmentFeiraco.recogerDatosFeiraco());
                }
                else{
                    enviarCorreo(CORREO_DANONE, ASUNTO_DANONE, fragmentDanone.recogerDatosDanone());
                };
                break;
            case(R.id.anadir):
                Intent intentAnadir = new Intent(getBaseContext(), ActividadEditar.class);
                intentAnadir.putExtra("origen", "anadir");
                startActivity(intentAnadir);
                break;
            case(R.id.correo):
                cambiarDireccionCorreo();
                break;
            case(R.id.ayuda):
                mostrarAyuda();
                break;
            case(R.id.borrarPedido):
                borrarPedido();
                break;
            case(R.id.asunto):
                cambiarAsuntoCorreo();
                break;
            case(R.id.salir):
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método que envía un correo electrónico. Se crea un Intent que levantará una app de correo
     * como Gmail ó Yahoo Mail. En caso de que no hubiese ninguna instalada en el dispositivo,
     * avisa al usuario para que instale una.
     * @param direccion La dirección a donde enviar el correo.
     * @param asunto El asunto del correo.
     * @param cuerpo El cuerpo del correo (su contenido).
     */
    public void enviarCorreo(String direccion, String asunto, String cuerpo){
        Intent intentEnviarCorreo =
                new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto",direccion, null));

        intentEnviarCorreo.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intentEnviarCorreo.putExtra(Intent.EXTRA_TEXT, cuerpo);

        // intentamos levartar una app que envíe correos electrónicos
        // si la hay la arrancamos
        if (intentEnviarCorreo.resolveActivity(getPackageManager()) != null) {
            startActivity(intentEnviarCorreo);
        }
        // si no la hay avisamos al usuario para que instale una
        else{
            Toast toast = Toast.makeText(this, "Instale una app de correo " +
                    "electrónico como GMAIL ó YAHOO MAIL", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Método que arranca la ActividadEditar para que el usuario modifique la dirección del correo.
     */
    public void cambiarDireccionCorreo(){
        Intent intentCambiarCorreo =
                new Intent(getBaseContext(), ActividadEditar.class);
        intentCambiarCorreo.putExtra("origen", "correo");
        intentCambiarCorreo.putExtra("proveedor", getNombreFragmentActivo());
        String direccionCorreo = "";
        //comprobamos en que pestaña estamos
        if(estoyEnFeiraco()){
            direccionCorreo = CORREO_FEIRACO;
        }
        else{
            direccionCorreo = CORREO_DANONE;
        }
        intentCambiarCorreo.putExtra("correo", direccionCorreo);
        startActivity(intentCambiarCorreo);
    }

    /**
     * Método que arranca la ActividadEditar para que el usuario modifique el asunto del correo.
     */
    public void cambiarAsuntoCorreo(){
        Intent intentCambiarAsunto =
                new Intent(getBaseContext(), ActividadEditar.class);
        intentCambiarAsunto.putExtra("origen", "asunto");
        intentCambiarAsunto.putExtra("proveedor", getNombreFragmentActivo());
        String asunto = "";
        if(estoyEnFeiraco()){
            asunto = ASUNTO_FEIRACO;
        }
        else{
            asunto = ASUNTO_DANONE;
        }
        intentCambiarAsunto.putExtra("asunto", asunto);
        startActivity(intentCambiarAsunto);
    }

    /**
     * Método que nos enseña a Steve Urkel :)))
     */
    public void mostrarToastUrkel(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.steve_urkel_he_sido_yo);
        mp.start();
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.layout_urkel,null);
        Toast t = new Toast(this);
        t.setView(layout);
        t.setDuration(Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        t.show();
    }

    /**
     * Método que arranca la ActividadAyuda.
     */
    public void mostrarAyuda(){
        Intent intentAyuda = new Intent(getBaseContext(), ActividadAyuda.class);
        startActivity(intentAyuda);
    }

    /**
     * Método usado para borrar los datos mostrados en la ListView de Danone. Recorre la lista de
     * datos y borra las cantidades Pedida y Devolución de cada fila.
     */
    protected void borrarPedido() {
        Dato d = null;
        for (int i = 0; i < listaDatos.size(); i++) {
            d = listaDatos.get(i);
            d.setCtaPedida("");
            d.setCtaDevolucion("");
            adaptadorListaYogures.notifyDataSetChanged();
        }
    }
}