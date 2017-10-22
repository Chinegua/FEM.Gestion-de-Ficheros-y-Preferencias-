package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.upm.miw.SolitarioCelta.models.EntityRepository;


public class MainActivity extends Activity {

	JuegoCelta juego;
    Chronometer chronometer;
    SharedPreferences preferencias;

    private final String GRID_KEY = "GRID_KEY";
    private final String TIME = "TIME";
    private EntityRepository db;



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        juego = new JuegoCelta();
        db = new EntityRepository(getApplicationContext());
        Log.i("MiW",Long.toString(db.count()));


        if (savedInstanceState != null) {
            this.juego.deserializaTablero(savedInstanceState.getString(GRID_KEY));
            this.activateChronoDefined(savedInstanceState.getLong(TIME));

        }
        else{
            activateChrono();
        }
        //MediaPlayer mp = new MediaPlayer();
        //mp = MediaPlayer.create(this, R.raw.reloj);
        //mp.setLooping(true);
        //mp.start();
        mostrarTablero();
    }

    public void activateChrono(){
        chronometer = (Chronometer) findViewById(R.id.chrono);
        chronometer.start();
    }

    public void activateChronoDefined(Long time){
        chronometer = (Chronometer) findViewById(R.id.chrono);
        chronometer.setBase(time);
        chronometer.start();

    }
    public void desactivateChrono(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        long chronoValue = chronometer.getBase();

        Log.i("MiW",Long.toString(chronoValue));
    }

    public void resetChrono(){
        desactivateChrono();
        activateChrono();
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        juego.jugar(i, j);

        mostrarTablero();
        if (juego.juegoTerminado()) {
            desactivateChrono();
            preferencias = PreferenceManager.getDefaultSharedPreferences(this);
            Calendar cal = Calendar.getInstance();
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            db.onSave(preferencias.getString("nombreJugador","null"),dayOfMonth,new SimpleDateFormat("MMM").format(cal.getTime()),sdf.format(cal.getTime()).toString(),juego.countFichas());
            // TODO guardar puntuación
            new AlertDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + Integer.toString(i) + Integer.toString(j);
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = (RadioButton) findViewById(idBoton);
                    button.setChecked(juego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }
    }

    /**
     * Guarda el estado del tablero (serializado)
     * @param outState Bundle para almacenar el estado del juego
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(GRID_KEY, juego.serializaTablero());
        outState.putLong(TIME,chronometer.getBase());

        super.onSaveInstanceState(outState);
    }

    /**
     * Recupera el estado del juego
     * @param savedInstanceState Bundle con el estado del juego almacenado
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString(GRID_KEY);
        juego.deserializaTablero(grid);
        mostrarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onRestart(Bundle savedInstanceState){
        super.onRestart();
        desactivateChrono();

        return true;
    }

    public void guardarPartida(){
        String value = juego.serializaTablero();
        try {
            FileOutputStream fos = openFileOutput("saved.txt", Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.close();
            Toast.makeText(this,"Partida Guardada", Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPartida(){

        DialogFragment dialogFragment = new ExitDialog();
        dialogFragment.show(getFragmentManager(), "exit");

    }

    public void resetPartida(){
        DialogFragment dialogFragment = new RestartDialog();
        dialogFragment.show(getFragmentManager(), "exit");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;

            case R.id.opcReiniciarPartida:
                resetPartida();
                return true;

            case R.id.opcGuardarPartida:
                guardarPartida();
                return true;
            case R.id.opcRecuperarPartida:
                try {
                    BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("saved.txt")));
                    String linea = fin.readLine();
                    if (!juego.serializaTablero().toString().equals(linea.toString())){
                        Log.i("MiW",juego.serializaTablero().toString());
                        Log.i("MiW",linea);

                        getPartida();
                    }

                }
                catch (Exception e){}
                return true;
            case R.id.opcMejoresResultados:
                Intent intent = new Intent(this, MejoresResultadosActivity.class);
                startActivity(intent);
                return true;


                // TODO!!! resto opciones

            default:
                Toast.makeText(
                        this,
                        getString(R.string.txtSinImplementar),
                        Toast.LENGTH_SHORT
                ).show();
        }
        return true;
    }
}
