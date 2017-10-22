package es.upm.miw.SolitarioCelta.models;

import android.provider.BaseColumns;

/**
 * Created by chinegua on 19/10/17.
 */

final public class PuntuacionesContract {

    private PuntuacionesContract() {}

    public static abstract class tablaPuntuaciones implements BaseColumns{
        static final String TABLE_NAME = "puntuaciones";
        static final String COL_ID = _ID;
        static final String COL_NAME = "name";
        static final String COL_DAY = "day";
        static final String COL_MONTH = "month";
        static final String COL_TIME = "time";
        static final String COL_FICHAS = "fichas";
    }

}