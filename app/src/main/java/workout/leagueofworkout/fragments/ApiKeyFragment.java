package workout.leagueofworkout.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import workout.leagueofworkout.ApiRequest;
import workout.leagueofworkout.R;

//Boite de dialogue qui s'affiche lorsque l'on selectionne "Ajouter un compte"
//L'utilisateur entre son nom d'invocateur et sélectionne son serveur
//Quand il confirme, on vérifie lance la méthode checkPlayerName de ApiRequest

public class ApiKeyFragment extends DialogFragment {
    private apiKeyFragmentCallBack mListener;
    private EditText editText;
    private Spinner spinner;
    private ApiRequest apiRequest;
    private RequestQueue requestQueue;
    private NavigationView navigationView;
    private Menu menu;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        navigationView = getActivity().findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_apikey, null));
        builder.setTitle(R.string.api_key);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editText = getDialog().findViewById(R.id.apikey);
                if(editText.getText().toString().trim().length() > 0){
                    mListener = (apiKeyFragmentCallBack) getActivity();
                    mListener.onSaveKey(editText.getText().toString().trim());
                } else {
                    Toast.makeText(getActivity(), R.string.isempty, Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ApiKeyFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public interface apiKeyFragmentCallBack {
        void onSaveKey(String key);
    }
}