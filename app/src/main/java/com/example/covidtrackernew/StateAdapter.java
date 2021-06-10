package com.example.covidtrackernew;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateRVViewHolder> {

    private final List<StateModel> stateList;
    public StateAdapter(List<StateModel> stateList){
        this.stateList = stateList;
    }

    @NonNull
    @Override
    public StateRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
        return new StateRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateRVViewHolder holder, int position) {

        StateModel stateDate = stateList.get(position);

        holder.stateNameTV.setText(stateDate.getmStateName());
        holder.deathsTV.setText(String.valueOf(stateDate.getmDeaths()));
        holder.recoveredTV.setText(String.valueOf(stateDate.getmRecovered()));
        holder.casesTV.setText(String.valueOf(stateDate.getmCases()));
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public static class StateRVViewHolder extends RecyclerView.ViewHolder {

        private final TextView stateNameTV;
        private final TextView casesTV;
        private final TextView deathsTV;
        private final TextView recoveredTV;

        public StateRVViewHolder(@NonNull View itemView) {
            super(itemView);

            stateNameTV = itemView.findViewById(R.id.stateName);
            casesTV = itemView.findViewById(R.id.totalCases);
            deathsTV = itemView.findViewById(R.id.deathCases);
            recoveredTV = itemView.findViewById(R.id.recoveredCases);

        }

    }

}
