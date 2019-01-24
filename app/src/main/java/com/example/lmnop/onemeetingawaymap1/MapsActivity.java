package com.example.lmnop.onemeetingawaymap1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lmnop.onemeetingawaymap1.DataBase.DataSource;
import com.example.lmnop.onemeetingawaymap1.model.DataItemMeetings;
import com.example.lmnop.onemeetingawaymap1.sample.SampleDataProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.InfoWindowAdapter {

    //Map of Meetings
    HashMap<LatLng, List<DataItemMeetings>> locationMap = new HashMap<>();

    private static final String TAG = "maps activity";
    List<DataItemMeetings> dataItemMeetingsList = SampleDataProvider.dataItemMeetingsList;
    DataSource mDataSource;

    private GoogleMap mMap;
    private int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

    ToggleButton sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mDataSource = new DataSource(this);
        mDataSource.open();
        mDataSource.seedDataBase(dataItemMeetingsList);

        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sunday.isChecked()) {
                    onSunday();
                } else {
                    mMap.clear();
                    locationMap.clear();
                    dayIsChecked();
                }
            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monday.isChecked()) {
                    onMonday();
                } else {
                    mMap.clear();
                    locationMap.clear();
                    dayIsChecked();
                }
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuesday.isChecked()) {
                    onTuesday();
                } else {
                    mMap.clear();
                    locationMap.clear();
                    dayIsChecked();
                }
            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wednesday.isChecked()) {
                    onWednesday();
                } else {
                    mMap.clear();
                    locationMap.clear();
                    dayIsChecked();
                }
            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thursday.isChecked()) {
                    onThursday();
                } else {
                    mMap.clear();
                    locationMap.clear();
                    dayIsChecked();
                }
            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friday.isChecked()) {
                    onFriday();
                } else {
                    mMap.clear();
                    locationMap.clear();
                    dayIsChecked();
                }
            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saturday.isChecked()) {
                    onSaturday();
                } else {
                    mMap.clear();
                    locationMap.clear();
                    dayIsChecked();
                }
            }
        });
    }

    public void dayIsChecked() {
        sunday.setChecked(false);
        monday.setChecked(false);
        tuesday.setChecked(false);
        wednesday.setChecked(false);
        thursday.setChecked(false);
        friday.setChecked(false);
        saturday.setChecked(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(this);

        LatLng seattle = new LatLng(47.608013, -122.335167);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seattle, 13));

        if (today == 2) {
            onMonday();
            monday.setChecked(true);
        }
        if (today == 3) {
            onTuesday();
            tuesday.setChecked(true);
        }
        if (today == 4) {
            onWednesday();
            wednesday.setChecked(true);
        }
        if (today == 5) {
            onThursday();
            thursday.setChecked(true);
        }
        if (today == 6) {
            onFriday();
            friday.setChecked(true);
        }
        if (today == 1) {
            onSunday();
            sunday.setChecked(true);
        }
        if (today == 7) {
            onSaturday();
            saturday.setChecked(true);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        DataItemMeetings meetings = (DataItemMeetings) marker.getTag();
        ArrayList<DataItemMeetings> list;

        list = (ArrayList<DataItemMeetings>) locationMap.get(marker.getPosition());
        Log.d(TAG, "getInfoContents: " + meetings.getMeetingName());
        View view = getLayoutInflater().inflate(R.layout.info_window, null, true);
        TextView mn = view.findViewById(R.id.meetingName);
        TextView location = view.findViewById(R.id.location);
        TextView oc = view.findViewById(R.id.oc);
        TextView day = view.findViewById(R.id.day);
        TextView time = view.findViewById(R.id.time);
        TextView address = view.findViewById(R.id.address);
        TextView codes = view.findViewById(R.id.codes);

        mn.setText(meetings.getMeetingName());
        location.setText(meetings.getLocation());
        oc.setText(meetings.getOc());
        day.setText(meetings.getDay());
        time.setText(meetings.getStartTime() + " - " + meetings.getEndTime());
        address.setText(meetings.getAddress());
        codes.setText(meetings.getCodes());
        return view;
    }

    public void onSunday() {
        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {

            String day = meet.get(i).getDay();
            String tod = meet.get(i).getTod();
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            String des = meet.get(i).getDay() + " " + meet.get(i).getStartTime() + "-" + meet.get(i).getEndTime();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);

            // IF there is a meeting in HashMap at LatLng
            // THEN replace marker at LatLng with new dynamically numbered icon
            LatLng ll = new LatLng(lat, lng);
            if (day.equals("Wednesday")) {
                if (locationMap.get(ll) == null) {
                    List<DataItemMeetings> nl = new ArrayList<>();
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    if (tod.equals("Morning")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_morning)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Mid-Day")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_mid_day)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Evening")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_evening)))
                                .setTag(meet.get(i));
                    }
                } else if (locationMap.get(ll) != null) {
                    List<DataItemMeetings> nl = locationMap.get(ll);
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    DataItemMeetings nm = meet.get(i);
                    nm.setMeetingName(nl.size() + "");
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                            .title(meet.get(i).getMeetingName())
                            .snippet(des)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                            .setTag(meet.get(i));
                }
            }
        }
    }

    public void onMonday() {
        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {

            String day = meet.get(i).getDay();
            String tod = meet.get(i).getTod();
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            String des = meet.get(i).getDay() + " " + meet.get(i).getStartTime() + "-" + meet.get(i).getEndTime();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);

            // IF there is a meeting in HashMap at LatLng
            // THEN replace marker at LatLng with new dynamically numbered icon
            LatLng ll = new LatLng(lat, lng);
            if (day.equals("Wednesday")) {
                if (locationMap.get(ll) == null) {
                    List<DataItemMeetings> nl = new ArrayList<>();
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    if (tod.equals("Morning")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_morning)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Mid-Day")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_mid_day)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Evening")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_evening)))
                                .setTag(meet.get(i));
                    }
                } else if (locationMap.get(ll) != null) {
                    List<DataItemMeetings> nl = locationMap.get(ll);
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    DataItemMeetings nm = new DataItemMeetings();
                    nm = meet.get(i);
                    nm.setMeetingName(nl.size() + "");
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                            .title(meet.get(i).getMeetingName())
                            .snippet(des)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                            .setTag(meet.get(i));
                }
            }
        }
    }

    public void onTuesday() {
        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {

            String day = meet.get(i).getDay();
            String tod = meet.get(i).getTod();
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            String des = meet.get(i).getDay() + " " + meet.get(i).getStartTime() + "-" + meet.get(i).getEndTime();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);

            // IF there is a meeting in HashMap at LatLng
            // THEN replace marker at LatLng with new dynamically numbered icon
            LatLng ll = new LatLng(lat, lng);
            if (day.equals("Wednesday")) {
                if (locationMap.get(ll) == null) {
                    List<DataItemMeetings> nl = new ArrayList<>();
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    if (tod.equals("Morning")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_morning)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Mid-Day")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_mid_day)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Evening")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_evening)))
                                .setTag(meet.get(i));
                    }
                } else if (locationMap.get(ll) != null) {
                    List<DataItemMeetings> nl = locationMap.get(ll);
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    DataItemMeetings nm = new DataItemMeetings();
                    nm = meet.get(i);
                    nm.setMeetingName(nl.size() + "");
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                            .title(meet.get(i).getMeetingName())
                            .snippet(des)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                            .setTag(meet.get(i));
                }
            }
        }
    }

    public void onWednesday() {
        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {

            String day = meet.get(i).getDay();
            String tod = meet.get(i).getTod();
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            String des = meet.get(i).getDay() + " " + meet.get(i).getStartTime() + "-" + meet.get(i).getEndTime();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);

            // IF there is a meeting in HashMap at LatLng
            // THEN replace marker at LatLng with new dynamically numbered icon
            LatLng ll = new LatLng(lat, lng);
            if (day.equals("Wednesday")) {
                if (locationMap.get(ll) == null) {
                    List<DataItemMeetings> nl = new ArrayList<>();
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    if (tod.equals("Morning")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_morning)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Mid-Day")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_mid_day)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Evening")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wednesday_evening)))
                                .setTag(meet.get(i));
                    }
                } else if (locationMap.get(ll) != null) {
                    List<DataItemMeetings> nl = locationMap.get(ll);
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    DataItemMeetings nm = new DataItemMeetings();
                    nm = meet.get(i);
                    nm.setMeetingName(nl.size() + "");
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                            .title(meet.get(i).getMeetingName())
                            .snippet(des)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                            .setTag(meet.get(i));
                }
            }
        }
    }

    public void onThursday() {
        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {

            String day = meet.get(i).getDay();
            String tod = meet.get(i).getTod();
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            String des = meet.get(i).getDay() + " " + meet.get(i).getStartTime() + "-" + meet.get(i).getEndTime();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);

            // IF there is a meeting in HashMap at LatLng
            // THEN replace marker at LatLng with new dynamically numbered icon
            LatLng ll = new LatLng(lat, lng);
            if (day.equals("Thursday")) {
                if (locationMap.get(ll) == null) {
                    List<DataItemMeetings> nl = new ArrayList<>();
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    if (tod.equals("Morning")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_thursday_morning)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Mid-Day")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_thursday_mid_day)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Evening")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_thursday_evening)))
                                .setTag(meet.get(i));
                    }
                } else if (locationMap.get(ll) != null) {
                    List<DataItemMeetings> nl = locationMap.get(ll);
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    DataItemMeetings nm = new DataItemMeetings();
                    nm = meet.get(i);
                    nm.setMeetingName(nl.size() + "");
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                            .title(meet.get(i).getMeetingName())
                            .snippet(des)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                            .setTag(meet.get(i));
                }
            }
        }
    }

    public void onFriday() {
        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {

            String day = meet.get(i).getDay();
            String tod = meet.get(i).getTod();
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            String des = meet.get(i).getDay() + " " + meet.get(i).getStartTime() + "-" + meet.get(i).getEndTime();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);

            // IF there is a meeting in HashMap at LatLng
            // THEN replace marker at LatLng with new dynamically numbered icon
            LatLng ll = new LatLng(lat, lng);
            if (day.equals("Friday")) {
                if (locationMap.get(ll) == null) {
                    List<DataItemMeetings> nl = new ArrayList<>();
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    if (tod.equals("Morning")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_morning)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Mid-Day")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_mid_day)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Evening")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                                .setTag(meet.get(i));
                    }
                } else if (locationMap.get(ll) != null) {
                    List<DataItemMeetings> nl = locationMap.get(ll);
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    DataItemMeetings nm = new DataItemMeetings();
                    nm = meet.get(i);
                    nm.setMeetingName(nl.size() + "");
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                            .title(meet.get(i).getMeetingName())
                            .snippet(des)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                            .setTag(meet.get(i));
                }
            }
        }
    }

    public void onSaturday() {
        List<DataItemMeetings> meet = mDataSource.getPins();
        for (int i = 0; i < meet.size(); i++) {
            String day = meet.get(i).getDay();
            String tod = meet.get(i).getTod();
            String slat = meet.get(i).getLat();
            String slng = meet.get(i).getLng();
            String des = meet.get(i).getDay() + " " + meet.get(i).getStartTime() + "-" + meet.get(i).getEndTime();
            double lat = Double.parseDouble(slat);
            double lng = Double.parseDouble(slng);
            // IF there is a meeting in HashMap at LatLng
            // THEN replace marker at LatLng with new dynamically numbered icon
            LatLng ll = new LatLng(lat, lng);
            if (day.equals("Saturday")) {
                if (locationMap.get(ll) == null) {
                    List<DataItemMeetings> nl = new ArrayList<>();
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    if (tod.equals("Morning")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_saturday_morning)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Mid-Day")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_saturday_mid_day)))
                                .setTag(meet.get(i));
                    } else if (tod.equals("Evening")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                                .title(meet.get(i).getMeetingName())
                                .snippet(des)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_saturday_evening)))
                                .setTag(meet.get(i));
                    }
                } else if (locationMap.get(ll) != null) {
                    List<DataItemMeetings> nl = locationMap.get(ll);
                    nl.add(meet.get(i));
                    locationMap.put(ll, nl);
                    DataItemMeetings nm = new DataItemMeetings();
                    nm = meet.get(i);
                    nm.setMeetingName(nl.size() + "");
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat))
                            .title(meet.get(i).getMeetingName())
                            .snippet(des)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_friday_evening)))
                            .setTag(meet.get(i));
                }
            }
            //else create normal meeting tag and add it to HashMap



        }
    }

}


