package es.upm.miw.SolitarioCelta;

        import android.os.Bundle;
        import android.preference.PreferenceActivity;
        import android.support.annotation.Nullable;

public class Preferencias extends PreferenceActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

}
