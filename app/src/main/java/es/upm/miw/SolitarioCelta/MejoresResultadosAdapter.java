package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import es.upm.miw.SolitarioCelta.models.PuntuacionesEntity;

/**
 * Created by chinegua on 22/10/17.
 */

public class MejoresResultadosAdapter extends ArrayAdapter {

    Context contexto;
    ArrayList<PuntuacionesEntity> datos;

    MejoresResultadosAdapter(Context context, ArrayList<PuntuacionesEntity> objects){
        super(context,R.layout.activity_mejores_resultados,objects);
        contexto = context;
        datos = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout vista;

        if (null!=convertView){
            vista = (LinearLayout) convertView;
        }
        else {
            LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (LinearLayout) inflador.inflate(R.layout.score_view, parent, false);
        }


        PuntuacionesEntity puntuaciones = datos.get(position);


        if (puntuaciones != null) {
            TextView tvnombre = (TextView) vista.findViewById(R.id.scoreNombre);
            tvnombre.setText(puntuaciones.getName());

            TextView tvDate = (TextView) vista.findViewById(R.id.scoreDate);
            tvDate.setText(puntuaciones.getDay()+"/"+puntuaciones.getMonth()+" "+puntuaciones.getTime());

            TextView tvScore = (TextView) vista.findViewById(R.id.scoreScore);
            tvScore.setText(Integer.toString(puntuaciones.getNumeroFichas()));




        }

        return vista;
    }
}