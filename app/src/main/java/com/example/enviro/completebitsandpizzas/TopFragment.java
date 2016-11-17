package com.example.enviro.completebitsandpizzas;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    private  int iD = 0;

    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view != null) {
            TextView shift = (TextView) view.findViewById(R.id.shift);
            int size = Shift.shifts.length;
            Date now = new Date();
            for (int i = 0; i < size; i++) {
                Shift item = Shift.shifts[i];
                if (item.getCurrentWorker() == iD && item.getDate().after(now)) {
                    shift.setText(item.dateToString());
                    break;
                }
            }
        }

    }

}
