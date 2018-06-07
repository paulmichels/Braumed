package workout.leagueofworkout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.io.InputStream;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

//Boite de dialogue qui s'affiche lorsque l'on selectionne "Ajouter un compte"
//L'utilisateur entre son nom d'invocateur et sélectionne son serveur
//Quand il confirme, on vérifie lance la méthode checkPlayerName de ApiRequest

public class ConnectFragment extends DialogFragment {
    private EditText editText;
    private Spinner spinner;
    private ApiRequest apiRequest;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private NavigationView navigationView;
    private Menu menu;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        navigationView = getActivity().findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        final ImageView imageView = getActivity().findViewById(R.id.profile_icon);
        final TextView textView = getActivity().findViewById(R.id.summoner_name);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_add_account, null));
        builder.setTitle(R.string.add_account);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editText = getDialog().findViewById(R.id.summonername);
                        spinner = getDialog().findViewById(R.id.region);
                        if(editText.getText().toString().trim().length() > 0){
                            requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
                            apiRequest = new ApiRequest(requestQueue, getActivity());
                            apiRequest.checkPlayerName(editText.getText().toString().trim(), spinner.getSelectedItemPosition(), new ApiRequest.checkPlayerCallBack() {
                                @Override
                                public void onSuccess(int profileIconId, String name, long summonerLevel, long accountId, long id, long revisionDate, Context context) {
                                    Toast.makeText(context, "Connecté à "+name, Toast.LENGTH_SHORT).show();
                                    sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                                    editor = sharedPreferences.edit();
                                    editor.putInt("profileIconId", profileIconId);
                                    editor.putString("name", name);
                                    editor.putLong("summonerLevel", summonerLevel);
                                    editor.putLong("accountId", accountId);
                                    editor.putLong("id", id);
                                    editor.putLong("revisionDate", revisionDate);
                                    editor.apply();

                                    if(sharedPreferences.getString("name", null)==null){
                                        menu.findItem(R.id.nav_add_account).setVisible(true);
                                        menu.findItem(R.id.nav_workout).setVisible(false);
                                        menu.findItem(R.id.nav_deconnect).setVisible(false);
                                        textView.setVisibility(View.GONE);
                                        imageView.setVisibility(View.GONE);
                                    }else{
                                        menu.findItem(R.id.nav_add_account).setVisible(false);
                                        menu.findItem(R.id.nav_workout).setVisible(true);
                                        menu.findItem(R.id.nav_deconnect).setVisible(true);
                                        imageView.setVisibility(View.VISIBLE);
                                        textView.setVisibility(View.VISIBLE);
                                        textView.setText(name);
                                        //TODO : ICI AUSSI https://ddragon.leagueoflegends.com/api/versions.json
                                        final String imgURL  = "http://ddragon.leagueoflegends.com/cdn/8.11.1/img/profileicon/"+profileIconId+".png";
                                        new DownLoadImageTask(imageView).execute(imgURL);
                                    }
                                }

                                @Override
                                public void dontExist(String message, Context context) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(String message, Context context) {
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

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}