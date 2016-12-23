package com.rebty.taskmanager.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rebty.taskmanager.activities.MainActivity;
import com.rebty.taskmanager.R;

public class DeleteDialog extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_delete, null);

        ListView listView = (ListView) v.findViewById(R.id.listViewForDelTask);

        String[] bufferList = new String[MainActivity.listOfTasks.size()];
        for (int i = 0; i < MainActivity.listOfTasks.size(); i++) {
            bufferList[i] = MainActivity.listOfTasks.get(i).header;
        }

        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, bufferList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.listOfTasks.remove(position);
                MainActivity.initializeAdapter(MainActivity.listOfTasks);
                dismiss();
            }
        });

        return v;
    }
}
