package workout.leagueofworkout.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import workout.leagueofworkout.ApiRequest;
import workout.leagueofworkout.MySingleton;
import workout.leagueofworkout.R;
import workout.leagueofworkout.entity.GameEntity;
import workout.leagueofworkout.entity.ParticipantEntity;
import workout.leagueofworkout.entity.ParticipantIdentityEntity;
import workout.leagueofworkout.entity.TeamEntity;
import workout.leagueofworkout.entity.WorkoutEntity;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private Context context;
    private List<GameEntity> gameEntityList;
    private long summonerId;
    private ApiRequest apiRequest;
    private RequestQueue requestQueue;
    private String version;
    private long participantId = 0;

    //TODO : TELECHARGER LES IMAGES POUR REDUIRE CONSOMMATION RESEAU
    //TODO : CHANGER LES NOMS DES MODES DE JEU     https://developer.riotgames.com/game-constants.html
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPortrait, ivSpell1, ivSpell2, ivItem1, ivItem2, ivItem3, ivItem4, ivItem5, ivItem6, ivItem7;
        private TextView tvTypeMatch, tvKda, tvGold, tvCs, tvDuration, tvDate, tvLevel;
        private CardView cardView;
        private RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            //Cardview
            cardView = view.findViewById(R.id.cardview);
            //ImageView
            ivPortrait = view.findViewById(R.id.iv_portrait);
            ivSpell1 = view.findViewById(R.id.iv_sum1);
            ivSpell2= view.findViewById(R.id.iv_sum2);
            ivItem1 = view.findViewById(R.id.iv_item1);
            ivItem2 = view.findViewById(R.id.iv_item2);
            ivItem3 = view.findViewById(R.id.iv_item3);
            ivItem4 = view.findViewById(R.id.iv_item4);
            ivItem5 = view.findViewById(R.id.iv_item5);
            ivItem6 = view.findViewById(R.id.iv_item6);
            ivItem7 = view.findViewById(R.id.iv_item7);
            //TextView
            tvTypeMatch = view.findViewById(R.id.tv_type_match);
            tvLevel = view.findViewById(R.id.tv_level);
            tvKda = view.findViewById(R.id.tv_kda);
            tvGold = view.findViewById(R.id.tv_gold);
            tvCs = view.findViewById(R.id.tv_cs);
            tvDuration = view.findViewById(R.id.tv_duration);
            tvDate = view.findViewById(R.id.tv_date);
            //RecyclerView
            recyclerView = view.findViewById(R.id.exercice_recyclerview);
        }
    }

    public GameAdapter(Context context, List<GameEntity> gameEntityList, long summonerId, String version){
        this.version=version;
        this.context = context;
        this.gameEntityList = gameEntityList;
        this.summonerId = summonerId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GameEntity gameEntity = gameEntityList.get(position);
        List<TeamEntity> teamEntityList = gameEntity.getTeamList();
        List<ParticipantEntity> participantEntityList = gameEntity.getParticipantList();
        List<ParticipantIdentityEntity> participantIdentityEntityList = gameEntity.getParticipantIdentityList();
        int i=0, j=0;
        boolean foundParticipantId = false, foundParticipant = false;

        //holder.recyclerView.setVisibility(View.GONE);

        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        apiRequest = new ApiRequest(requestQueue, context);


        //ON CHERCHE LE PARTICIPANTID DU JOUEUR
        while (!foundParticipantId){
            ParticipantIdentityEntity participantIdentityEntity = participantIdentityEntityList.get(i);
            if(participantIdentityEntity.getSummonerId()==summonerId){
                foundParticipantId=true;
                participantId = participantIdentityEntity.getParticipantId();

                //QUAND TROUVE, ON VA CHERCHER LE PARTICIPANT CORRESPONDANT A L'ID
                while (!foundParticipant){
                    ParticipantEntity participantEntity = participantEntityList.get(j);
                    if(participantEntity.getParticipantId()==participantId){

                        //LE PARTICIPANT EST TROUVE, ON EDITE LE RECYLCERVIEW EN FONCTION
                        foundParticipant=true;

                        if(participantEntity.isWin()){
                            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.win));
                        } else {
                            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lose));
                        }

                        holder.tvTypeMatch.setText(gameEntity.getGameMode());
                        holder.tvKda.setText(participantEntity.getKills()+"/"+participantEntity.getDeaths()+"/"+participantEntity.getAssists());
                        holder.tvGold.setText(String.valueOf(participantEntity.getGoldEarned()));
                        holder.tvCs.setText(String.valueOf(participantEntity.getTotalMinionsKilled()));
                        holder.tvLevel.setText("Level "+String.valueOf(participantEntity.getChampLevel()));

                        try {
                            String champion = apiRequest.getChampionName(participantEntity.getChampionId());
                            Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/champion/"+champion+".png").into(holder.ivPortrait);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String spell1 = apiRequest.getSummonerSpell(participantEntity.getSpell1Id());
                            Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/spell/"+spell1+".png").into(holder.ivSpell1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String spell2 = apiRequest.getSummonerSpell(participantEntity.getSpell2Id());
                            Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/spell/"+spell2+".png").into(holder.ivSpell2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //TODO : REMPLIR LES CASES DANS L'ORDRE
                        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/item/"+participantEntity.getItem0()+".png").into(holder.ivItem1);
                        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/item/"+participantEntity.getItem1()+".png").into(holder.ivItem2);
                        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/item/"+participantEntity.getItem2()+".png").into(holder.ivItem3);
                        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/item/"+participantEntity.getItem3()+".png").into(holder.ivItem4);
                        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/item/"+participantEntity.getItem4()+".png").into(holder.ivItem5);
                        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/item/"+participantEntity.getItem5()+".png").into(holder.ivItem6);
                        Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/"+version+"/img/item/"+participantEntity.getItem6()+".png").into(holder.ivItem7);


                        holder.tvDuration.setText(convertDuration(gameEntity.getGameDuration()));
                        holder.tvDate.setText(convertDate(gameEntity.getGameCreation()));

                        WorkoutEntity workoutEntity = new WorkoutEntity(participantEntity, gameEntity);
                        holder.recyclerView.setVisibility(View.VISIBLE);
                        WorkoutAdapter workoutAdapter = new WorkoutAdapter(workoutEntity);
                        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        holder.recyclerView.setAdapter(workoutAdapter);

                        /*
                        ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(holder.recyclerView.getVisibility()==View.GONE){
                                    WorkoutEntity workoutEntity = new WorkoutEntity(participantEntity, gameEntity);
                                    holder.recyclerView.setVisibility(View.VISIBLE);
                                    WorkoutAdapter workoutAdapter = new WorkoutAdapter(workoutEntity);
                                    holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                    holder.recyclerView.setAdapter(workoutAdapter);

                                } else {
                                    holder.recyclerView.setVisibility(View.GONE);
                                }

                            }
                        });
                        */

                    } else {
                        j++;
                    }
                }
            } else {
                i++;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (gameEntityList == null)
            return 0;
        else
            return  gameEntityList.size();
    }

    public static String convertDate(long creation){
        Date date = new Date(creation);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String convertDuration(long duration) {


        double total = duration / 60f;
        String formattedDuration;

        //Faire une fonction
        if (total < 60) {
            double minute = total;
            int minuteInt = (int) minute;
            String finalMinute = String.valueOf(minuteInt);
            if (minuteInt < 10) {
                finalMinute = "0" + minuteInt;
            }
            double seconde = minute - Math.floor(minute);
            seconde = Math.round(seconde * 60f);
            int secondeInt = (int) seconde;
            String finalSeconde = String.valueOf(secondeInt);
            if (secondeInt < 10) {
                finalSeconde = "0" + secondeInt;
            }
            formattedDuration = finalMinute + ":" + finalSeconde;

        } else {
            double heure = total / 60f;
            int heureInt = (int) heure;
            double minute = total - (heureInt * 60f);
            int minuteInt = (int) minute;
            String finalMinute = String.valueOf(minuteInt);
            if (minuteInt < 10) {
                finalMinute = "0" + minuteInt;
            }
            double seconde = minute - Math.floor(minute);
            seconde = Math.round(seconde * 60f);
            int secondeInt = (int) seconde;
            String finalSeconde = String.valueOf(secondeInt);
            if (secondeInt < 10) {
                finalSeconde = "0" + secondeInt;
            }
            formattedDuration = String.valueOf(heureInt) + ":" + finalMinute + ":" + finalSeconde;
        }
        return formattedDuration;
    }

}