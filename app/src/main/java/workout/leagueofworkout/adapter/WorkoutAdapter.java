package workout.leagueofworkout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import workout.leagueofworkout.R;
import workout.leagueofworkout.entity.WorkoutEntity;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.MyViewHolder>{
    private WorkoutEntity workoutEntity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView squatSeries, squatNumber, pushUpSeries, pushUpNumber, plankSeries, plankNumbers, bicepsSeries, bicepsNumbers, epaulesSeries, epaulesNumbers, abdosSeries, abdosNumbers;

        public MyViewHolder(View view) {
            super(view);
            squatNumber = (TextView) view.findViewById(R.id.squat_number);
            squatSeries = (TextView) view.findViewById(R.id.squat_series);
            pushUpNumber = (TextView) view.findViewById(R.id.pushup_number);
            pushUpSeries = (TextView) view.findViewById(R.id.pushup_series);
            plankNumbers = (TextView) view.findViewById(R.id.plank_number);
            plankSeries = (TextView) view.findViewById(R.id.plank_series);
            bicepsNumbers = (TextView) view.findViewById(R.id.biceps_number);
            bicepsSeries = (TextView) view.findViewById(R.id.biceps_series);
            epaulesNumbers = (TextView) view.findViewById(R.id.bucheron_number);
            epaulesSeries = (TextView) view.findViewById(R.id.bucheron_series);
            abdosNumbers = (TextView) view.findViewById(R.id.abdos_number);
            abdosSeries = (TextView) view.findViewById(R.id.abdos_series);
        }
    }

    public WorkoutAdapter (WorkoutEntity workoutEntity) {
        this.workoutEntity = workoutEntity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercice_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.squatNumber.setText(String.valueOf(workoutEntity.getSquat().getNumber())+" répétitions");
        holder.squatSeries.setText(String.valueOf(workoutEntity.getSquat().getSeries())+" séries");
        holder.pushUpNumber.setText(String.valueOf(workoutEntity.getPompes().getNumber())+" répétitions");
        holder.pushUpSeries.setText(String.valueOf(workoutEntity.getPompes().getSeries())+" séries");
        holder.plankNumbers.setText(String.valueOf(workoutEntity.getGainage().getNumber())+" secondes");
        holder.plankSeries.setText(String.valueOf(workoutEntity.getGainage().getSeries())+" séries");
        holder.bicepsNumbers.setText(String.valueOf(workoutEntity.getBiceps().getNumber())+" répétitions");
        holder.bicepsSeries.setText(String.valueOf(workoutEntity.getBiceps().getSeries())+" séries");
        holder.epaulesNumbers.setText(String.valueOf(workoutEntity.getEpaules().getNumber())+" répétitions");
        holder.epaulesSeries.setText(String.valueOf(workoutEntity.getEpaules().getSeries())+" séries");
        holder.abdosNumbers.setText(String.valueOf(workoutEntity.getAbdos().getNumber())+" répétitions");
        holder.abdosSeries.setText(String.valueOf(workoutEntity.getAbdos().getSeries())+" séries");
    }

    @Override
    public int getItemCount() {
        if (workoutEntity == null)
            return 0;
        else
            return 1;
    }
}
