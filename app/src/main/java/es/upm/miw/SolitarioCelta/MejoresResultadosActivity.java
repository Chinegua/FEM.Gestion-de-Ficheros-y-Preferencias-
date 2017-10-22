package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import es.upm.miw.SolitarioCelta.models.EntityRepository;
import es.upm.miw.SolitarioCelta.models.PuntuacionesEntity;

public class MejoresResultadosActivity extends Activity {
    ArrayList<PuntuacionesEntity> puntuaciones;
    EntityRepository db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        showScore();

    }

    public void showScore(){
        db = new EntityRepository(getApplicationContext());
        setContentView(R.layout.activity_mejores_resultados);

        puntuaciones = db.onGet();

        ListView lvPuntuaciones = (ListView) findViewById(R.id.vista);
        lvPuntuaciones.setAdapter(
                new MejoresResultadosAdapter(
                        getApplicationContext(),
                        puntuaciones
                ));
    }

    public void onDelete(MenuItem menu){
        db.onDelete();
        showScore();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mejores_resultados_menu, menu);
        return true;
    }
}
