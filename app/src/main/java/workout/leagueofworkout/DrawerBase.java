package workout.leagueofworkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import workout.leagueofworkout.entity.SummonerEntity;
import workout.leagueofworkout.fragments.ApiKeyFragment;
import workout.leagueofworkout.fragments.ConnectFragment;
import workout.leagueofworkout.fragments.NoAccountFragment;
import workout.leagueofworkout.fragments.WorkoutFragment;
import workout.leagueofworkout.service.NotificationService;

public class DrawerBase extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ConnectFragment.connectFragmentCallback,
        ApiKeyFragment.apiKeyFragmentCallBack,
        WorkoutFragment.lastMatchCallBack{

    private SummonerEntity summonerEntity;
    private String version = "8.14.1", apiKey=""; //TODO : You need to put your Api Key here
    private static final String CHANNEL_ID = "gameNotification";
    private long lastMatch;

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

        saveKey(apiKey);

        //this.apiKey = getKey();
        this.lastMatch = getMatch();
        this.summonerEntity = getSummoner();

        refreshDrawer(summonerEntity);
        if (summonerEntity==null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new NoAccountFragment());
            ft.commit();
        } else {
            startService(new Intent(this, NotificationService.class));
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, WorkoutFragment.newInstance(summonerEntity, version, apiKey));
            ft.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, NotificationService.class));
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
        Fragment fragment = null;
        if (id == R.id.nav_add_account) {
            DialogFragment dialogFragment = ConnectFragment.newInstance(apiKey);
            dialogFragment.show(getSupportFragmentManager(), "addAccount");
        } else if (id == R.id.nav_workout){

        } else if (id == R.id.nav_stats){

        } else if (id == R.id.nav_param){

        } else if (id == R.id.nav_api_key){
            DialogFragment dialogFragment = new ApiKeyFragment();
            dialogFragment.show(getSupportFragmentManager(), "apikey");
        } else if (id == R.id.nav_deconnect){
            stopService(new Intent(this, NotificationService.class));
            this.summonerEntity = null;
            removeSummoner();
            removeMatch();
            refreshDrawer(summonerEntity);
            fragment = new NoAccountFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("summoner", summonerEntity);
        savedInstanceState.putString("apikey", apiKey);
        savedInstanceState.putLong("lastmatch", lastMatch);

        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String summoner = gson.toJson(summonerEntity);
        prefsEditor.putString("summoner", summoner);
        prefsEditor.putString("apikey", apiKey);
        prefsEditor.putLong("lastmatch", lastMatch);
        prefsEditor.apply();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.summonerEntity = savedInstanceState.getParcelable("summoner");
        this.apiKey = savedInstanceState.getString("apikey");
        this.lastMatch = savedInstanceState.getLong("lastmatch");
    }

    @Override
    public void onSuccess(SummonerEntity summonerEntity) {
        this.summonerEntity = summonerEntity;
        saveSummoner(summonerEntity);
        startService(new Intent(this, NotificationService.class));
        refreshDrawer(summonerEntity);
        Fragment fragment = new WorkoutFragment().newInstance(summonerEntity, version, apiKey);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();
    }

    @Override
    public void onSaveKey(String key) {
        this.apiKey = key;
        saveKey(key);
    }

    @Override
    public void onSuccess(Context context, long matchId) {
        this.lastMatch = matchId;
        saveMatch(matchId);
    }

    public void saveSummoner(SummonerEntity summonerEntity){
        SharedPreferences  mPrefs = getSharedPreferences("summoner", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String summoner = gson.toJson(summonerEntity);
        prefsEditor.putString("summoner", summoner);
        prefsEditor.apply();
    }

    public SummonerEntity getSummoner() {
        SharedPreferences  mPrefs = getSharedPreferences("summoner", MODE_PRIVATE);
        Gson gson = new Gson();
        String summoner = mPrefs.getString("summoner", "");
        return gson.fromJson(summoner, SummonerEntity.class);
    }

    public void removeSummoner(){
        SharedPreferences  mPrefs = getSharedPreferences("summoner", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

    public void saveKey(String key){
        SharedPreferences  mPrefs = getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("apikey", key);
        prefsEditor.apply();
    }

    public String getKey(){
        SharedPreferences  mPrefs = getSharedPreferences("key", MODE_PRIVATE);
        return mPrefs.getString("apikey", "");
    }

    public void removeKey(){
        SharedPreferences  mPrefs = getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

    public void saveMatch(long matchId){
        SharedPreferences  mPrefs = getSharedPreferences("match", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putLong("match", matchId);
        prefsEditor.apply();
    }

    public long getMatch(){
        SharedPreferences  mPrefs = getSharedPreferences("match", MODE_PRIVATE);
        return mPrefs.getLong("match", 0);
    }

    public void removeMatch(){
        SharedPreferences  mPrefs = getSharedPreferences("match", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

    public void setBorderIcon(ImageView imageView, long level){
        if (level<30){
            imageView.setImageResource(R.drawable.border_lvl_1);
        } else if (level<50){
            imageView.setImageResource(R.drawable.border_lvl_30);
        } else if (level<75){
            imageView.setImageResource(R.drawable.border_lvl_50);
        } else if (level<100){
            imageView.setImageResource(R.drawable.border_lvl_75);
        } else if (level<125){
            imageView.setImageResource(R.drawable.border_lvl_100);
        } else if (level<150){
            imageView.setImageResource(R.drawable.border_lvl_125);
        } else if (level<175){
            imageView.setImageResource(R.drawable.border_lvl_150);
        } else if (level<200){
            imageView.setImageResource(R.drawable.border_lvl_175);
        } else if (level<225){
            imageView.setImageResource(R.drawable.border_lvl_200);
        } else if (level<250){
            imageView.setImageResource(R.drawable.border_lvl_225);
        } else if (level<275){
            imageView.setImageResource(R.drawable.border_lvl_250);
        } else if (level<300){
            imageView.setImageResource(R.drawable.border_lvl_275);
        } else if (level<325){
            imageView.setImageResource(R.drawable.border_lvl_300);
        } else if (level<350){
            imageView.setImageResource(R.drawable.border_lvl_325);
        } else if (level<375){
            imageView.setImageResource(R.drawable.border_lvl_350);
        } else if (level<400){
            imageView.setImageResource(R.drawable.border_lvl_375);
        } else if (level<425){
            imageView.setImageResource(R.drawable.border_lvl_400);
        } else if (level<450){
            imageView.setImageResource(R.drawable.border_lvl_425);
        } else if (level<475){
            imageView.setImageResource(R.drawable.border_lvl_450);
        } else if (level<500){
            imageView.setImageResource(R.drawable.border_lvl_475);
        } else {
            imageView.setImageResource(R.drawable.border_lvl_500);
        }
    }

    public void refreshDrawer(SummonerEntity summonerEntity){
        NavigationView navigationView = findViewById(R.id.nav_view);
        final View headerLayout = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        TextView summonerName = headerLayout.findViewById(R.id.summoner_name);
        TextView level = headerLayout.findViewById(R.id.level);
        ImageView profileIcon = headerLayout.findViewById(R.id.profile_icon);
        ImageView borderIcon = headerLayout.findViewById(R.id.border_icon);
        ImageView background = headerLayout.findViewById(R.id.background);

        if(summonerEntity==null){
            menu.findItem(R.id.nav_add_account).setVisible(true);
            menu.findItem(R.id.nav_workout).setVisible(false);
            menu.findItem(R.id.nav_deconnect).setVisible(false);
            summonerName.setVisibility(View.GONE);
            level.setVisibility(View.GONE);
            borderIcon.setVisibility(View.GONE);
            profileIcon.setVisibility(View.GONE);
            background.setVisibility(View.GONE);
        }else{
            menu.findItem(R.id.nav_add_account).setVisible(false);
            menu.findItem(R.id.nav_workout).setVisible(true);
            menu.findItem(R.id.nav_deconnect).setVisible(true);
            summonerName.setVisibility(View.VISIBLE);
            level.setVisibility(View.VISIBLE);
            borderIcon.setVisibility(View.VISIBLE);
            profileIcon.setVisibility(View.VISIBLE);
            background.setVisibility(View.VISIBLE);

            summonerName.setText(summonerEntity.getName());
            level.setText(String.valueOf(summonerEntity.getSummonerLevel()));
            Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/profileicon/"+summonerEntity.getProfileIconId()+".png").into(profileIcon);
            setBorderIcon(borderIcon, summonerEntity.getSummonerLevel());
            Typeface font = Typeface.createFromAsset(getAssets(), "friz_quadrata.otf");
            summonerName.setTypeface(font);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }
}
