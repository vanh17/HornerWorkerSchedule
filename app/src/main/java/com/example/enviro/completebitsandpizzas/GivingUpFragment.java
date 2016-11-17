package com.example.enviro.completebitsandpizzas;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class GivingUpFragment extends ListFragment {

    private int iD = 0;

    public GivingUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<Shift> givingUpShifts = new ArrayList<Shift>();
        Shift[] shifts = Shift.shifts;
        int size = Shift.shifts.length;
        for (int i = 0; i < size; i++) {
            Shift item = shifts[i];
            if (item.getCurrentWorker() == -1 && item.getOriginalWorker() == iD) {
                givingUpShifts.add(item);
            }
        }
        ArrayAdapter<Shift> adapter = new ArrayAdapter<Shift>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                givingUpShifts);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

}
