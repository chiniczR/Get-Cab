package com.example.rchinicz.getcab2.controller.fragments;
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
import com.example.rchinicz.getcab2.R;
import com.example.rchinicz.getcab2.model.model.backend.Globals;
import com.example.rchinicz.getcab2.model.model.backend.IAction;
import com.example.rchinicz.getcab2.model.model.entities.Trip;
import com.example.rchinicz.getcab2.model.model.utils.Dialogs;

public class MyTrips extends Fragment {

    protected ArrayList<Trip> travels;
    public MyTrips() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);
        final ListView listMyTravels = (ListView) view.findViewById(R.id.listMyTravels);
        Globals.backEnd.myTrips(Globals.cabbie, new IAction<ArrayList<Trip>>() {
            @Override
            public void onSuccess(ArrayList<Trip> obj) {
                travels = obj;
                ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>( getActivity(), R.layout.travel, travels) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        if (convertView == null)
                            convertView = View.inflate(getActivity(),R.layout.travel,null);

                        EditText name = (EditText) convertView.findViewById(R.id.name);
                        EditText tel = (EditText) convertView.findViewById(R.id.tel);
                        EditText source = (EditText) convertView.findViewById(R.id.source);
                        EditText target = (EditText) convertView.findViewById(R.id.target);
                        EditText dist = (EditText) convertView.findViewById(R.id.dist);
                        Button claim = (Button) convertView.findViewById(R.id.claim);
                        Button unclaim = (Button) convertView.findViewById(R.id.unclaim);

                        unclaim.setTag(travels.get(position).getKey());
                        unclaim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String key = (String)((Button) view).getTag();
                                Globals.backEnd.endTrip(key, new IAction<Boolean>() {
                                    @Override
                                    public void onSuccess(Boolean obj) {
                                        Dialogs.Dialog(getActivity(),getString(R.string.FIREBASE),getString(R.string.unclaimed),getString(R.string.BUTTON_CLOSE));
                                        return;
                                    }

                                    @Override
                                    public void onFailure(Exception ex) {
                                        Dialogs.Dialog(getActivity(),getString(R.string.FIREBASE),ex.getMessage(),getString(R.string.BUTTON_CLOSE));
                                        return;
                                    }
                                });

                            }
                        });
                        claim.setVisibility(View.GONE);

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

                listMyTravels.setAdapter(adapter);
            }
            @Override
            public void onFailure(Exception exception) {   }
        });

        return  view;
    }
}
