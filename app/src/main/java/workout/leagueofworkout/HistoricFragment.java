package workout.leagueofworkout;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import workout.leagueofworkout.adapter.GameAdapter;
import workout.leagueofworkout.entity.GameEntity;

public class HistoricFragment extends Fragment {

    private ApiRequest apiRequest;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private GameAdapter myAdapter;
    private List<Long> matchIdList = new ArrayList<>();
    private List<GameEntity> gameEntityList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_historic, container, false);

        requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
        apiRequest = new ApiRequest(requestQueue, getActivity());
        String json = apiRequest.getJsonFile(getContext(), "champion.json");
        apiRequest.getHistoryMatches(233162649L, new ApiRequest.historyCallBack() {
            @Override
            public void onSuccess(Context context, List<Long> matches) {
                Log.d("APP", "MATCH = " + matches.toString());
                matchIdList = matches;
                for(int i=0; i<matchIdList.size(); i++){
                    apiRequest.getMatch(matchIdList.get(i), new ApiRequest.matchCallBack() {
                        @Override
                        public void onSuccess(Context context, GameEntity gameEntity) {
                            gameEntityList.add(gameEntity);
                            recyclerView = (RecyclerView) getActivity().findViewById(R.id.historic_recyclerview);
                            myAdapter = new GameAdapter(context, gameEntityList, 100446925L);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(myAdapter);
                        }

                        @Override
                        public void noMatch(Context context, String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Context context, String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void noMatch(Context context, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Context context, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
