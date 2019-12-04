package com.example.rchinicz.getcab2.controller.fragments;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.rchinicz.getcab2.R;
import com.example.rchinicz.getcab2.controller.activities.Main;
import com.example.rchinicz.getcab2.model.model.backend.Globals;
import com.example.rchinicz.getcab2.model.model.backend.IAction;
import com.example.rchinicz.getcab2.model.model.entities.Trip;
import com.example.rchinicz.getcab2.model.model.utils.Dialogs;

public class FreeTrips extends Fragment {

    protected ArrayList<Trip> travels;

    public FreeTrips() {    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_free_trips, container, false);
        final ListView listOpenTravels = (ListView) view.findViewById(R.id.listOpenTravels);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Globals.backEnd.openTrips(new IAction<ArrayList<Trip>>() {
                        @Override
                        public void onSuccess(ArrayList<Trip> obj) {
                            travels = obj;
                            try {
                                ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(getActivity(), R.layout.travel, travels) {
                                    @NonNull
                                    @Override
                                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                        if (convertView == null)
                                            convertView = View.inflate(getActivity(), R.layout.travel, null);

                                        EditText name = (EditText) convertView.findViewById(R.id.name);
                                        EditText tel = (EditText) convertView.findViewById(R.id.tel);
                                        EditText source = (EditText) convertView.findViewById(R.id.source);
                                        EditText target = (EditText) convertView.findViewById(R.id.target);
                                        EditText dist = (EditText)convertView.findViewById(R.id.dist);
                                        Button claim = (Button) convertView.findViewById(R.id.claim);
                                        Button unclaim = (Button) convertView.findViewById(R.id.unclaim);

                                        claim.setTag(travels.get(position).getKey());
                                        claim.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String key = (String) ((Button) view).getTag();
                                                Globals.backEnd.startTrip(key, Globals.cabbie.getEmail(), new IAction<Boolean>() {
                                                    @Override
                                                    public void onSuccess(Boolean obj) {
                                                        Dialogs.Dialog(getActivity(), getString(R.string.FIREBASE), getString(R.string.claimed), getString(R.string.BUTTON_CLOSE));
                                                        return;
                                                    }

                                                    @Override
                                                    public void onFailure(Exception ex) {
                                                        Dialogs.Dialog(getActivity(), getString(R.string.FIREBASE), ex.getMessage(), getString(R.string.BUTTON_CLOSE));
                                                        return;
                                                    }
                                                });

                                            }
                                        });
                                        unclaim.setVisibility(View.GONE);

                                        name.setText(travels.get(position).getPassenger().getName());
                                        tel.setText(travels.get(position).getPassenger().getPhone());
                                        source.setText(travels.get(position).getOrigin());
                                        target.setText(travels.get(position).getDestination());
                                        dist.setText("Trip distance: " +
                                                Double.toString(travels.get(position).getTripDistance())
                                                + " Km");

                                        return convertView;
                                    }
                                };
                                listOpenTravels.setAdapter(adapter);
                            }catch (Exception e)
                            {

                            }
                        }
                        @Override
                        public void onFailure(Exception exception) {    }
                    });
                }catch(Exception e)
                {

                }
                return null;
            }
        }.execute();
        return  view;
    }
}