package workout.leagueofworkout.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import workout.leagueofworkout.ApiRequest;
import workout.leagueofworkout.MySingleton;
import workout.leagueofworkout.R;
import workout.leagueofworkout.adapter.GameAdapter;
import workout.leagueofworkout.adapter.WorkoutAdapter;
import workout.leagueofworkout.entity.GameEntity;
import workout.leagueofworkout.entity.SummonerEntity;
import workout.leagueofworkout.entity.WorkoutEntity;

import static android.content.Context.MODE_PRIVATE;
import static java.util.Collections.sort;

public class WorkoutFragment extends Fragment {

    private ApiRequest apiRequest;
    private RequestQueue requestQueue;
    private RecyclerView rvLastMatch;
    private GameAdapter gameAdapter;
    private List<GameEntity> gameEntityList = new ArrayList<>();
    private SummonerEntity summonerEntity;
    private String version, apiKey;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private lastMatchCallBack mListener;

    public WorkoutFragment(){

    }

    public static WorkoutFragment newInstance(SummonerEntity summonerEntity, String version, String apiKey) {
        WorkoutFragment workoutFragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putParcelable("summoner", summonerEntity);
        args.putString("version", version);
        args.putString("apikey", apiKey);
        workoutFragment.setArguments(args);
        return workoutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            summonerEntity = getArguments().getParcelable("summoner");
            version = getArguments().getString("version");
            apiKey = getArguments().getString("apikey");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
        apiRequest = new ApiRequest(requestQueue, getActivity());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.workout_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gameEntityList.clear();
                gameAdapter.notifyDataSetChanged();
                getLastMatch();
            }
        });
        getLastMatch();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof lastMatchCallBack) {
            mListener = (lastMatchCallBack) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //TODO : LIMITER LES REQUETES (FAUX REFRESH?)
    public void getLastMatch(){
        //Requête sur API RIOT pour la dernière partie jouée
        mSwipeRefreshLayout.setRefreshing(true);
        apiRequest.getLastMatch(summonerEntity.getAccountId(), apiKey, new ApiRequest.lastMatchCallBack() {
            @Override
            public void onSuccess(Context context, long matchId) {
                apiRequest.getMatch(matchId, apiKey, new ApiRequest.matchCallBack() {
                    @Override
                    public void onSuccess(Context context, GameEntity gameEntity) {
                        gameEntityList.add(gameEntity);
                        rvLastMatch = (RecyclerView) getActivity().findViewById(R.id.workout_recyclerview);
                        gameAdapter = new GameAdapter(getActivity(), gameEntityList, summonerEntity.getId(), version);
                        rvLastMatch.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvLastMatch.setAdapter(gameAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (mListener != null) {
                            mListener.onSuccess(context, gameEntity.getGameId());
                        }
                    }

                    @Override
                    public void noMatch(Context context, String message) {

                    }

                    @Override
                    public void onError(Context context, String message) {

                    }

                    @Override
                    public void onUnexpectedResponse(String message, Context context){
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                });
            }

            @Override
            public void noMatch(Context context, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Context context, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnexpectedResponse(String message, Context context) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface lastMatchCallBack{
        void onSuccess(Context context, long matchId);
    }
}
