package workout.leagueofworkout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;

import workout.leagueofworkout.adapter.GameAdapter;
import workout.leagueofworkout.entity.GameEntity;

public class WorkoutFragment extends Fragment {

    private ApiRequest apiRequest;
    private RequestQueue requestQueue;
    private GameEntity mGameEntity;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
        apiRequest = new ApiRequest(requestQueue, getActivity());
        apiRequest.getMatch(3659591476L, new ApiRequest.matchCallBack() {
            @Override
            public void onSuccess(Context context, GameEntity gameEntity) {
                mGameEntity = gameEntity;
            }

            @Override
            public void noMatch(Context context, String message) {

            }

            @Override
            public void onError(Context context, String message) {

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.workout_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //gameAdapter = new GameAdapter(mGameEntity);


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
