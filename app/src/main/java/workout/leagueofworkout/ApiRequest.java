package workout.leagueofworkout;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import workout.leagueofworkout.entity.BanEntity;
import workout.leagueofworkout.entity.GameEntity;
import workout.leagueofworkout.entity.ParticipantEntity;
import workout.leagueofworkout.entity.ParticipantIdentityEntity;
import workout.leagueofworkout.entity.TeamEntity;

import static android.content.Context.MODE_PRIVATE;

public class ApiRequest {
    private RequestQueue requestQueue;
    private Context context;
    private String region;

    public ApiRequest(RequestQueue requestQueue, Context context){
        this.requestQueue = requestQueue;
        this.context = context;
        this.region = getRegion();
    }

    //TODO : BLACKLISTER LES PSEUDO INEXISTANTS, RESET TOUS LES JOURS?
    public void checkPlayerName (final String name, final int position, final String apiKey, final checkPlayerCallBack checkPlayerCallBack){
        region = convertRegion(position);
        saveRegion(region);
        Log.i("REGION", region);
        String url = "https://"+ region +".api.riotgames.com/lol/summoner/v3/summoners/by-name/"+name+"?api_key="+apiKey;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("APP", response.toString());
                try {
                    int profileIconId = response.getInt("profileIconId");
                    String name = response.getString("name");
                    long summonerLevel = response.getLong("summonerLevel");
                    long accountId = response.getLong("accountId");
                    long id = response.getLong("id");
                    long revisionDate = response.getLong("revisionDate");
                    checkPlayerCallBack.onSuccess(profileIconId, name, summonerLevel, accountId, id, revisionDate, context);
                } catch (JSONException e) {
                    Log.i("APP", "EXCEPTION =" + e);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    checkPlayerCallBack.onError("Impossible de se connecter", context);
                    Log.i("APP", "NetworkError");
                } else if (error instanceof ServerError){
                    checkPlayerCallBack.dontExist("Ce joueur n'existe pas", context);
                    Log.i("APP", "ServerError");
                }
                checkPlayerCallBack.onUnexpectedResponse(handleNetworkCode(error), context);
                Log.i("APP", "ERROR = " + error);
            }
        });

        requestQueue.add(request);
    }

    public void getLastMatch(long accountId, final String apiKey, final lastMatchCallBack callback){
        String url = "https://"+region+".api.riotgames.com/lol/match/v3/matchlists/by-account/"+accountId+"?api_key="+apiKey;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.length() > 0 ){
                    try {
                        JSONArray matches = response.getJSONArray("matches");
                        JSONObject oneMatch = matches.getJSONObject(0);
                        long matchId = oneMatch.getLong("gameId");
                        callback.onSuccess(context, matchId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    callback.noMatch(context, "Aucun match trouvé");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onError(context, "Impossible de se connecter, veuillez réessayer");
                }
                callback.onUnexpectedResponse(handleNetworkCode(error), context);

            }
        });

        requestQueue.add(request);

    }

    public void getHistoryMatches(long accountId, final String apiKey, final historyCallBack callback){
        String url = "https://"+region+".api.riotgames.com/lol/match/v3/matchlists/by-account/"+accountId+"?api_key="+apiKey;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                List<Long> historyMatches = new ArrayList<>();

                if(response.length() > 0 ){
                    try {
                        JSONArray matches = response.getJSONArray("matches");
                        for(int i = 0; i < 10; i++){
                            JSONObject oneMatch = matches.getJSONObject(i);
                            long matchId = oneMatch.getLong("gameId");
                            historyMatches.add(matchId);
                        }
                        callback.onSuccess(context, historyMatches);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    callback.noMatch(context, "Aucun match trouvé");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onError(context, "Impossible de se connecter, veuillez réessayer");
                }
                callback.onUnexpectedResponse(handleNetworkCode(error), context);
            }
        });

        requestQueue.add(request);

    }

    public void getMatch(long matchId, final String apiKey, final matchCallBack callBack){
        String url = "https://"+region+".api.riotgames.com/lol/match/v3/matches/"+matchId+"?api_key="+apiKey;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                GameEntity gameEntity;
                List<TeamEntity> teamList = new ArrayList<>();
                List<ParticipantEntity> participantList = new ArrayList<>();
                List<ParticipantIdentityEntity> participantIdentityList = new ArrayList<>();
                List<BanEntity> banList = new ArrayList<>();
                TeamEntity teamEntity;
                ParticipantEntity participantEntity;
                ParticipantIdentityEntity participantIdentityEntity;
                BanEntity banEntity;

                if(response.length() > 0 ){
                    try {
                        //Variables générales du match
                        long gameId = response.getLong("gameId");
                        String platformId = response.getString("platformId");
                        long gameCreation = response.getLong("gameCreation");
                        long gameDuration = response.getLong("gameDuration");
                        int queueId = response.getInt("queueId");
                        int mapId = response.getInt("mapId");
                        int seasonId = response.getInt("seasonId");
                        String gameVersion = response.getString("gameVersion");
                        String gameMode = response.getString("gameMode");
                        String gameType = response.getString("gameType");

                        //Variables relatives aux compositions des équipes et aux performances
                        JSONArray teams = response.getJSONArray("teams");
                        for(int i = 0; i < teams.length(); i++){
                            JSONObject team = teams.getJSONObject(i);
                            int teamId = team.getInt("teamId");
                            String win = team.getString("win");
                            boolean firstBlood = team.getBoolean("firstBlood");
                            boolean firstInhibitor = team.getBoolean("firstInhibitor");
                            boolean firstBaron = team.getBoolean("firstBaron");
                            boolean firstDragon = team.getBoolean("firstDragon");
                            boolean firstRiftHerald = team.getBoolean("firstRiftHerald");
                            int towerKills = team.getInt("towerKills");
                            int inhibitorKills = team.getInt("inhibitorKills");
                            int baronKills = team.getInt("baronKills");
                            int dragonKills = team.getInt("dragonKills");
                            int vilemawKills = team.getInt("vilemawKills");
                            int riftHeraldKills = team.getInt("riftHeraldKills");
                            int dominionVictoryScore = team.getInt("dominionVictoryScore");

                            //Variables relatives aux bans
                            JSONArray bans = team.getJSONArray("bans");
                            for(int j = 0; j < bans.length(); j++){
                                JSONObject ban = bans.getJSONObject(j);
                                int championId = ban.getInt("championId");
                                int pickTurn = ban.getInt("pickTurn");
                                banEntity = new BanEntity(championId, pickTurn);
                                banList.add(banEntity);
                            }
                            teamEntity = new TeamEntity(teamId, towerKills, inhibitorKills, baronKills, dragonKills, vilemawKills, riftHeraldKills, dominionVictoryScore, win, firstBlood, firstInhibitor, firstBaron, firstDragon, firstRiftHerald, banList);
                            teamList.add(teamEntity);
                        }

                        //Variables relatives aux performances de jeu des joueurs
                        JSONArray participants = response.getJSONArray("participants");
                        for(int i = 0; i < participants.length(); i++){
                            JSONObject participant = participants.getJSONObject(i);
                            int participantId = participant.getInt("participantId");
                            int teamId = participant.getInt("teamId");
                            int championId = participant.getInt("championId");
                            int spell1Id = participant.getInt("spell1Id");
                            int spell2Id = participant.getInt("spell2Id");
                            String highestAchievedSeasonTier;
                            if(participant.has("neutralMinionsKilledTeamJungle")){
                                highestAchievedSeasonTier = participant.getString("highestAchievedSeasonTier");
                            } else {
                                highestAchievedSeasonTier = null;
                            }

                            JSONObject stats = participant.getJSONObject("stats");
                            long physicalDamageDealt = stats.getLong("physicalDamageDealt");
                            long magicDamageDealt = stats.getLong("magicDamageDealt");
                            long totalDamageDealt = stats.getLong("totalDamageDealt");
                            long magicDamageDealtToChampions = stats.getLong("magicDamageDealtToChampions");
                            long damageDealtToObjectives = stats.getLong("damageDealtToObjectives");
                            long visionScore = stats.getLong("visionScore");
                            long damageSelfMitigated = stats.getLong("damageSelfMitigated");
                            long magicalDamageTaken = stats.getLong("magicalDamageTaken");
                            long trueDamageTaken = stats.getLong("trueDamageTaken");
                            long damageDealtToTurrets = stats.getLong("damageDealtToTurrets");
                            long physicalDamageDealtToChampions = stats.getLong("physicalDamageDealtToChampions");
                            long trueDamageDealt = stats.getLong("trueDamageDealt");
                            long trueDamageDealtToChampions = stats.getLong("trueDamageDealtToChampions");
                            long totalHeal = stats.getLong("totalHeal");
                            long totalDamageDealtToChampions = stats.getLong("totalDamageDealtToChampions");
                            long totalDamageTaken = stats.getLong("totalDamageTaken");
                            long timeCCingOthers = stats.getLong("timeCCingOthers");
                            long physicalDamageTaken = stats.getLong("physicalDamageTaken");
                            int neutralMinionsKilledTeamJungle;
                            if(stats.has("neutralMinionsKilledTeamJungle")){
                                neutralMinionsKilledTeamJungle = stats.getInt("neutralMinionsKilledTeamJungle");
                            } else {
                                neutralMinionsKilledTeamJungle = 0;
                            }
                            int totalPlayerScore = stats.getInt("totalPlayerScore");
                            int deaths = stats.getInt("deaths");
                            int neutralMinionsKilledEnemyJungle;
                            if(stats.has("neutralMinionsKilledEnemyJungle")){
                                neutralMinionsKilledEnemyJungle = stats.getInt("neutralMinionsKilledEnemyJungle");
                            } else {
                                neutralMinionsKilledEnemyJungle = 0;
                            }
                            //int altarsCaptured = stats.getInt("altarsCaptured");
                            int largestCriticalStrike = stats.getInt("largestCriticalStrike");
                            int visionWardsBoughtInGame = stats.getInt("visionWardsBoughtInGame");
                            int largestKillingSpree = stats.getInt("largestKillingSpree");
                            int item1 = stats.getInt("item1");
                            int quadraKills = stats.getInt("quadraKills");
                            //int teamObjective = stats.getInt("teamObjective");
                            int totalTimeCrowdControlDealt = stats.getInt("totalTimeCrowdControlDealt");
                            int longestTimeSpentLiving = stats.getInt("longestTimeSpentLiving");
                            int wardsKilled;
                            if(stats.has("wardsKilled")){
                                wardsKilled = stats.getInt("wardsKilled");
                            } else {
                                wardsKilled = 0;
                            }
                            int item2 = stats.getInt("item2");
                            int item3 = stats.getInt("item3");
                            int item0 = stats.getInt("item0");
                            int wardsPlaced;
                            if(stats.has("wardsPlaced")){
                                wardsPlaced = stats.getInt("wardsPlaced");
                            } else {
                                wardsPlaced = 0;
                            }
                            int item4 = stats.getInt("item4");
                            int item5 = stats.getInt("item5");
                            int item6 = stats.getInt("item6");
                            int turretKills = stats.getInt("turretKills");
                            int tripleKills = stats.getInt("tripleKills");
                            int champLevel = stats.getInt("champLevel");
                            //int nodeNeutralizeAssist = stats.getInt("nodeNeutralizeAssist");
                            int goldEarned = stats.getInt("goldEarned");
                            int kills = stats.getInt("kills");
                            int doubleKills = stats.getInt("doubleKills");
                            //int nodeCaptureAssist = stats.getInt("nodeCaptureAssist");
                            //int nodeNeutralize = stats.getInt("nodeNeutralize");
                            int assists = stats.getInt("assists");
                            int unrealKills = stats.getInt("unrealKills");
                            int neutralMinionsKilled = stats.getInt("neutralMinionsKilled");
                            int objectivePlayerScore = stats.getInt("objectivePlayerScore");
                            int combatPlayerScore = stats.getInt("combatPlayerScore");
                            //int altarsNeutralized = stats.getInt("altarsNeutralized");
                            int goldSpent = stats.getInt("goldSpent");
                            int pentaKills = stats.getInt("pentaKills");
                            int totalMinionsKilled = stats.getInt("totalMinionsKilled");
                            //int nodeCapture = stats.getInt("nodeCapture");
                            int largestMultiKill = stats.getInt("largestMultiKill");
                            int sightWardsBoughtInGame = stats.getInt("sightWardsBoughtInGame");
                            int totalUnitsHealed = stats.getInt("totalUnitsHealed");
                            int inhibitorKills = stats.getInt("inhibitorKills");
                            int totalScoreRank = stats.getInt("totalScoreRank");
                            int killingSprees = stats.getInt("killingSprees");
                            boolean win = stats.getBoolean("win");
                            boolean firstTowerAssist;
                            if(stats.has("firstTowerAssist")){
                                firstTowerAssist = stats.getBoolean("firstTowerAssist");
                            } else {
                                firstTowerAssist = false;
                            }
                            boolean firstTowerKill;
                            if(stats.has("firstTowerKill")){
                                firstTowerKill = stats.getBoolean("firstTowerKill");
                            } else {
                                firstTowerKill = false;
                            }
                            boolean firstBloodAssist;
                            if(stats.has("firstBloodAssist")){
                                firstBloodAssist = stats.getBoolean("firstBloodAssist");
                            } else {
                                firstBloodAssist = false;
                            }
                            boolean firstInhibitorKill;
                            if(stats.has("firstInhibitorKill")){
                                firstInhibitorKill = stats.getBoolean("firstInhibitorKill");
                            } else {
                                firstInhibitorKill = false;
                            }
                            boolean firstInhibitorAssist;
                            if(stats.has("firstInhibitorAssist")){
                                firstInhibitorAssist = stats.getBoolean("firstInhibitorAssist");
                            } else {
                                firstInhibitorAssist = false;
                            }
                            boolean firstBloodKill;
                            if(stats.has("firstBloodKill")){
                                firstBloodKill = stats.getBoolean("firstBloodKill");
                            } else {
                                firstBloodKill = false;
                            }

                            JSONObject timeline = participant.getJSONObject("timeline");
                            String role = timeline.getString("role");
                            String lane = timeline.getString("lane");

                            participantEntity = new ParticipantEntity( physicalDamageDealt,  magicDamageDealt,  totalDamageDealt,  magicDamageDealtToChampions, damageDealtToObjectives, visionScore, damageSelfMitigated, magicalDamageTaken, trueDamageTaken, damageDealtToTurrets, physicalDamageDealtToChampions, trueDamageDealt, trueDamageDealtToChampions, totalHeal, totalDamageDealtToChampions, totalDamageTaken, timeCCingOthers, physicalDamageTaken, teamId, championId, spell1Id, spell2Id, neutralMinionsKilledTeamJungle, totalPlayerScore, deaths, neutralMinionsKilledEnemyJungle, largestCriticalStrike, visionWardsBoughtInGame, largestKillingSpree, item1, quadraKills, totalTimeCrowdControlDealt, longestTimeSpentLiving, wardsKilled, item2, item3, item0, wardsPlaced, item4, item5, item6, turretKills, tripleKills, champLevel, goldEarned, kills, doubleKills, assists, unrealKills, neutralMinionsKilled, objectivePlayerScore, combatPlayerScore, goldSpent, participantId, pentaKills, totalMinionsKilled, largestMultiKill,  sightWardsBoughtInGame,  totalUnitsHealed,  inhibitorKills,  totalScoreRank,  killingSprees,  win,  firstTowerAssist,  firstTowerKill,  firstBloodAssist,  firstInhibitorKill, firstInhibitorAssist, firstBloodKill,  highestAchievedSeasonTier,  role,  lane);
                            participantList.add(participantEntity);
                        }

                        //Variables relatives au joueurs
                        JSONArray participantIdentities = response.getJSONArray("participantIdentities");
                        for(int i = 0; i < participantIdentities.length(); i++){
                            JSONObject participantIdentity = participantIdentities.getJSONObject(i);
                            int participantId = participantIdentity.getInt("participantId");

                            JSONObject player = participantIdentity.getJSONObject("player");
                            String currentPlatformId = player.getString("currentPlatformId");
                            String summonerName = player.getString("summonerName");
                            String matchHistoryUri = player.getString("matchHistoryUri");
                            long currentAccountId = player.getLong("currentAccountId");
                            long summonerId;
                            if(player.has("summonerId")){
                                summonerId = player.getLong("summonerId");
                            } else {
                                summonerId = 0;
                            }
                            long accountId = player.getLong("accountId");
                            int profileIcon = player.getInt("profileIcon");
                            participantIdentityEntity = new ParticipantIdentityEntity( accountId,  summonerId,  currentAccountId,  participantId,  profileIcon,  matchHistoryUri,  summonerName,  currentPlatformId);
                            participantIdentityList.add(participantIdentityEntity);
                        }
                        gameEntity = new GameEntity( platformId,  gameVersion,  gameMode,  gameType,  gameId,  gameCreation,  gameDuration,  queueId,  mapId,  seasonId, teamList, participantList, participantIdentityList);
                        callBack.onSuccess(context ,gameEntity);
                    } catch (JSONException e) {
                        Log.d("APP", "EXEPTION HISTORY = " + e);
                        e.printStackTrace();
                    }
                }else{
                    callBack.noMatch(context, "Aucun match trouvé");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callBack.onError(context, "Impossible de se connecter");
                }
                callBack.onUnexpectedResponse(handleNetworkCode(error), context);
            }
        });

        requestQueue.add(request);


    }

    public String getJsonFile(Context context, String filename){

        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    public String getChampionName(int champId) throws JSONException{
        String json = getJsonFile(context, "champion.json");
        String champName = null;
        JSONObject champ = new JSONObject(json);
        JSONArray data = champ.getJSONArray("data");
        for (int i=0; i<data.length(); i++){
            JSONObject champion = data.getJSONObject(i);
            if(champion.getInt("id")==champId){
                champName = champion.getString("key");
            }
        }
        return champName;
    }

    public String getSummonerSpell(int spellId) throws JSONException{
        String json = getJsonFile(context, "summonerspells.json");
        String spellName = null;
        JSONObject spells = new JSONObject(json);
        JSONArray data = spells.getJSONArray("data");
        for (int i=0; i<data.length(); i++){
            JSONObject spell = data.getJSONObject(i);
            if(spell.getInt("id")==spellId){
                spellName = spell.getString("full");
            }
        }
        return spellName;
    }

    public String convertRegion(int position){
        String region = null;
        switch (position){
            case 0 :
                region = "euw1";
                break;
            case 1 :
                region = "br1";
                break;
            case 2 :
                region = "eun1";
                break;
            case 3 :
                region = "kr";
                break;
            case 4 :
                region = "la1";
                break;
            case 5 :
                region = "la2";
                break;
            case 6 :
                region = "na1";
                break;
            case 7 :
                region = "oc1";
                break;
            case 8 :
                region = "ru";
                break;
            case 9 :
                region = "tr1";
                break;
            case 10 :
                region = "jp1";
                break;
        }
        return region;
    }

    public String handleNetworkCode(VolleyError error){
        NetworkResponse networkResponse = error.networkResponse;
        String message = null;
        if (networkResponse != null && networkResponse.statusCode == 403) {
            message = "L'API key a expiré, veuillez en entrer une nouvelle";
        } else if (networkResponse != null && networkResponse.statusCode == 422) {
            message = "Le joueur existe mais aucun match recent trouvé";
        } else if (networkResponse != null && networkResponse.statusCode == 429) {
            message = "Trop de requêtes, veuillez réessayer dans une minute";
        } else if (networkResponse != null && networkResponse.statusCode == 500) {
            message = "Problème des serveurs RIOT GAMES veuillez réessayer plus tard";
        } else if (networkResponse != null && networkResponse.statusCode == 503) {
            message = "Service non disponible veuillez réessayer plus tard";
        }
        return message;
    }

    public interface checkPlayerCallBack{
        void onSuccess(int profileIconId, String name, long summonerLevel, long accountId, long id, long revisionDate, Context context);
        void dontExist(String message, Context context);
        void onError(String message, Context context);
        void onUnexpectedResponse(String message, Context context);
    }

    public interface historyCallBack {
        void onSuccess(Context context, List<Long> matches);
        void noMatch(Context context, String message);
        void onError(Context context, String message);
        void onUnexpectedResponse(String message, Context context);
    }

    public interface matchCallBack{
        void onSuccess(Context context, GameEntity gameEntity);
        void noMatch(Context context, String message);
        void onError(Context context, String message);
        void onUnexpectedResponse(String message, Context context);
    }

    public interface lastMatchCallBack{
        void onSuccess(Context context, long matchId);
        void noMatch(Context context, String message);
        void onError(Context context, String message);
        void onUnexpectedResponse(String message, Context context);
    }

    public String getRegion(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("region",MODE_PRIVATE);
        String region = sharedPreferences.getString("region", "");
        return region;
    }

    public void saveRegion(String region){
        SharedPreferences sharedPreferences = context.getSharedPreferences("region", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("region", region);
        prefsEditor.apply();
    }
}
