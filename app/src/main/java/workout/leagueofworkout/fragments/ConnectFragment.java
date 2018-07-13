package workout.leagueofworkout.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import workout.leagueofworkout.MySingleton;
import workout.leagueofworkout.R;
import workout.leagueofworkout.entity.SummonerEntity;

//Boite de dialogue qui s'affiche lorsque l'on selectionne "Ajouter un compte"
//L'utilisateur entre son nom d'invocateur et sélectionne son serveur
//Quand il confirme, on vérifie lance la méthode checkPlayerName de ApiRequest

public class ConnectFragment extends DialogFragment {
    private connectFragmentCallback mListener;
    private EditText editText;
    private Spinner spinner;
    private ApiRequest apiRequest;
    private RequestQueue requestQueue;
    private NavigationView navigationView;
    private String apiKey;

    public ConnectFragment(){

    }

    public static ConnectFragment newInstance(String apiKey) {
        ConnectFragment connectFragment = new ConnectFragment();
        Bundle args = new Bundle();
        args.putString("apikey", apiKey);
        connectFragment.setArguments(args);
        return connectFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            apiKey = getArguments().getString("apikey");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialo_apikey, null));
        builder.setTitle(R.string.add_account);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editText = getDialog().findViewById(R.id.summonername);
                        spinner = getDialog().findViewById(R.id.region);
                        if(editText.getText().toString().trim().length() > 0){
                            requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
                            apiRequest = new ApiRequest(requestQueue, getActivity());
                            apiRequest.checkPlayerName(editText.getText().toString().trim(), spinner.getSelectedItemPosition(), apiKey, new ApiRequest.checkPlayerCallBack() {
                                @Override
                                public void onSuccess(int profileIconId, String name, long summonerLevel, long accountId, long id, long revisionDate, Context context) {
                                    Toast.makeText(context, "Bienvenue "+name, Toast.LENGTH_SHORT).show();
                                    SummonerEntity summonerEntity = new SummonerEntity(summonerLevel, revisionDate, id, accountId, profileIconId, name);
                                    mListener = (connectFragmentCallback) context;
                                    mListener.onSuccess(summonerEntity);
                                }

                                @Override
                                public void dontExist(String message, Context context) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(String message, Context context) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onUnexpectedResponse(String message, Context context){
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }

                            });
                        } else {
                            Toast.makeText(getActivity(), R.string.isempty, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConnectFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public interface connectFragmentCallback {
        void onSuccess(SummonerEntity summonerEntity);
    }
}