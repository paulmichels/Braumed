package workout.leagueofworkout.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import workout.leagueofworkout.ApiRequest;
import workout.leagueofworkout.MySingleton;
import workout.leagueofworkout.R;
import workout.leagueofworkout.entity.GameEntity;
import workout.leagueofworkout.entity.ParticipantEntity;
import workout.leagueofworkout.entity.ParticipantIdentityEntity;
import workout.leagueofworkout.entity.TeamEntity;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private Context context;
    private List<GameEntity> gameEntityList;
    private long summonerId;
    private ApiRequest apiRequest;
    private RequestQueue requestQueue;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout rlMainContent, rlInfos, rlItems, rlInfosDate;
        private View vWinOrLose;
        private ImageView ivPortrait, ivKda, ivGold, ivCs, ivItem1, ivItem2, ivItem3, ivItem4, ivItem5, ivItem6, ivItem7;
        private TextView tvTypeMatch, tvKda, tvGold, tvCs, tvDuration, tvDate;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            //Cardview
            cardView = itemView.findViewById(R.id.cardview);
            //RelativeLayout
            rlMainContent = itemView.findViewById(R.id.rl_main_content);
            rlInfos = itemView.findViewById(R.id.rl_infos);
            rlItems = itemView.findViewById(R.id.rl_items);
            rlInfosDate = itemView.findViewById(R.id.rl_infos_date);
            //View
            vWinOrLose = itemView.findViewById(R.id.win_or_lose);
            //ImageView
            ivPortrait = itemView.findViewById(R.id.iv_portrait);
            ivKda = itemView.findViewById(R.id.iv_kda);
            ivGold = itemView.findViewById(R.id.iv_gold);
            ivCs = itemView.findViewById(R.id.iv_cs);
            ivItem1 = itemView.findViewById(R.id.iv_item1);
            ivItem2 = itemView.findViewById(R.id.iv_item2);
            ivItem3 = itemView.findViewById(R.id.iv_item3);
            ivItem4 = itemView.findViewById(R.id.iv_item4);
            ivItem5 = itemView.findViewById(R.id.iv_item5);
            ivItem6 = itemView.findViewById(R.id.iv_item6);
            ivItem7 = itemView.findViewById(R.id.iv_item7);
            //TextView
            tvTypeMatch = itemView.findViewById(R.id.tv_type_match);
            tvKda = itemView.findViewById(R.id.tv_kda);
            tvGold = itemView.findViewById(R.id.tv_gold);
            tvCs = itemView.findViewById(R.id.tv_cs);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

    public GameAdapter(Context context, List<GameEntity> gameEntityList, long summonerId){
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
        long participantId;

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
                            holder.vWinOrLose.setBackgroundColor(Color.GREEN);
                            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.win_row_bg));
                        } else {
                            holder.vWinOrLose.setBackgroundColor(Color.RED);
                            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lose_row_bg));
                        }

                        holder.tvTypeMatch.setText(gameEntity.getGameMode());
                        holder.tvKda.setText(participantEntity.getKills()+"/"+participantEntity.getDeaths()+"/"+participantEntity.getAssists());
                        holder.tvGold.setText(String.valueOf(participantEntity.getGoldEarned()));
                        holder.tvCs.setText(String.valueOf(participantEntity.getTotalMinionsKilled()));

                        requestQueue = MySingleton.getInstance(context).getRequestQueue();
                        apiRequest = new ApiRequest(requestQueue, context);
                        try {
                            String champion = apiRequest.getChampionName(participantEntity.getChampionId());
                            Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/8.11.1/img/champion/"+champion+".png").into(holder.ivPortrait);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        holder.ivKda.setImageResource(R.drawable.score);
                        holder.ivGold.setImageResource(R.drawable.gold);
                        holder.ivCs.setImageResource(R.drawable.minion);
                        holder.ivItem1.setImageResource(R.drawable.items);
                        holder.ivItem2.setImageResource(R.drawable.items);
                        holder.ivItem3.setImageResource(R.drawable.items);
                        holder.ivItem4.setImageResource(R.drawable.items);
                        holder.ivItem5.setImageResource(R.drawable.items);
                        holder.ivItem6.setImageResource(R.drawable.items);
                        holder.ivItem7.setImageResource(R.drawable.items);

                        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");
                        holder.tvDuration.setText(String.valueOf(gameEntity.getGameDuration()));
                        holder.tvDate.setText(String.valueOf(gameEntity.getGameCreation()));

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
}