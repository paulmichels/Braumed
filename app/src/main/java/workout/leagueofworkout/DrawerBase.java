package workout.leagueofworkout;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.io.InputStream;
import java.net.URL;

public class DrawerBase extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String summoner;
    private int profileIconId;
    private long summonerLevel, accountId, id, revisionDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        refreshDrawer();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new NestedFragment());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_add_account) {
            addAccount();
        } else if (id == R.id.nav_workout){

        }  else if (id == R.id.nav_stats){

        } else if (id == R.id.nav_param){

        } else if (id == R.id.nav_deconnect){
            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
            sharedPreferences.edit().remove("profileIconId").apply();
            sharedPreferences.edit().remove("name").apply();
            sharedPreferences.edit().remove("summonerLevel").apply();
            sharedPreferences.edit().remove("accountId").apply();
            sharedPreferences.edit().remove("id").apply();
            sharedPreferences.edit().remove("revisionDate").apply();
            refreshDrawer();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addAccount(){
        DialogFragment dialogFragment = new ConnectFragment();
        dialogFragment.show(getSupportFragmentManager(), "addAccount");
    }

    public void refreshDrawer(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        final View headerLayout = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        TextView textView = headerLayout.findViewById(R.id.summoner_name);

        sharedPreferences = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        summoner = sharedPreferences.getString("name", null);
        profileIconId = sharedPreferences.getInt("profileIconId", 0);
        editor.apply();

        ImageView imageView = headerLayout.findViewById(R.id.profile_icon);

        if(summoner==null){
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
            textView.setText(summoner);
            //TODO : CHANGER LE 8.11.1 EN VERSION ACTUELLE : https://ddragon.leagueoflegends.com/api/versions.json
            final String imgURL  = "http://ddragon.leagueoflegends.com/cdn/8.11.1/img/profileicon/"+profileIconId+".png";
            new DownLoadImageTask(imageView).execute(imgURL);
        }
        navigationView.setNavigationItemSelectedListener(this);
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
