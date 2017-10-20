package es.upm.miw.SolitarioCelta;


        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.content.DialogInterface;
        import android.os.Bundle;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;


public class ExitDialog extends DialogFragment {

    public ExitDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.ExitAlert))
                .setMessage(getString(R.string.ExitText))
                .setPositiveButton(
                        getString(R.string.ExitSi),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    BufferedReader fin = new BufferedReader(new InputStreamReader(main.openFileInput("saved.txt")));
                                    String linea = fin.readLine();

                                    main.juego.deserializaTablero(linea);
                                    main.mostrarTablero();

                                    fin.close();
                                }
                                catch (Exception e){

                                }                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.ExitNo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Acción opción No
                            }
                        }
                );

        return builder.create();
    }
}
