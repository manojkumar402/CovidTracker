package com.example.covidtrackernew;

import java.util.Collections;
import java.util.Comparator;

public class StateModel implements Comparable<StateModel> {
    private String mStateName;
    private int mRecovered;
    private int mDeaths;
    private int mCases;

    StateModel(String stateName, int NumRecovered, int NumDeaths, int NumCases){
        this.mStateName = stateName;
        this.mRecovered = NumRecovered;
        this.mDeaths = NumDeaths;
        this.mCases = NumCases;
    }



    public String getmStateName() {
        return mStateName;
    }

    public int getmRecovered() {
        return mRecovered;
    }

    public int getmDeaths() {
        return mDeaths;
    }

    public int getmCases() {
        return mCases;
    }


    @Override
    public int compareTo(StateModel state) {
        return state.getmCases() - this.mCases;
    }


}
