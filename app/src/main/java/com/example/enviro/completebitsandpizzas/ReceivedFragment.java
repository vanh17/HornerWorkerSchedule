package com.example.enviro.completebitsandpizzas;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReceivedFragment extends ListFragment {

    private int iD = 0;
    private Shift item;
    private ArrayList<Shift> receivedShifts = new ArrayList<Shift>();

    public ReceivedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Shift[] shifts = Shift.shifts;
        int size = Shift.shifts.length;
        for (int i = 0; i < size; i++) {
            Shift item = shifts[i];
            if (item.getOriginalWorker() != iD && item.getCurrentWorker() == -1) {
                receivedShifts.add(item);
            }
        }
        ArrayAdapter<Shift> adapter = new ArrayAdapter<Shift>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                receivedShifts);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        item = receivedShifts.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_shift)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick (DialogInterface dialog, int id) {
                        pickUpShift(item);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                    }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void pickUpShift(Shift item) {
        item.setCurrentWorker(iD);
        Toast.makeText(getActivity(), "Successfully Take Shift", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}