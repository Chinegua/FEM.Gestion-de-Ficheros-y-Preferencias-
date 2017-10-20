package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;




public class RestartDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.RestarAlert))
                .setMessage(getString(R.string.RestartText))
                .setPositiveButton(
                        getString(R.string.ExitSi),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    main.juego.reiniciar();
                                    main.mostrarTablero();
                                    main.resetChrono();
                                } catch (Exception e) {

                                }
                            }
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