package workout.leagueofworkout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import workout.leagueofworkout.HistoricFragment;
import workout.leagueofworkout.WorkoutFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                WorkoutFragment workoutFragment = new WorkoutFragment();
                return workoutFragment;
            case 1:
                HistoricFragment gamesFragment = new HistoricFragment();
                return gamesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}