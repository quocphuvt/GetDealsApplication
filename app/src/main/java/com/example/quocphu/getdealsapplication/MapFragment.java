package com.example.quocphu.getdealsapplication;


import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap mMap;
    ArrayList<Marker> markers=new ArrayList<Marker>();
    Dialog dialog;
    Marker marker;
    Marker marker_b;
    MapView mapView;
    private BottomSheetBehavior mBottomSheetBehavior;
    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        View bottomSheet = view.findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Button btn_collapse = bottomSheet.findViewById(R.id.btn_collapse);
        btn_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        mapView = (MapView)view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState); //Khởi tạo mapView
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                UiSettings uisetting = mMap.getUiSettings(); //Lấy giao diện Map
                uisetting.setCompassEnabled(true); //La bàn
                uisetting.setZoomControlsEnabled(true);
                uisetting.setScrollGesturesEnabled(true);
                uisetting.setTiltGesturesEnabled(true);
                uisetting.setMyLocationButtonEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//bang M
                    if (getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        xuLyQuyen();
                        Toast.makeText(getContext(), "e", Toast.LENGTH_SHORT).show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        Toast.makeText(getContext(), "d", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    xuLyQuyen();
                    Toast.makeText(getContext(), "f", Toast.LENGTH_SHORT).show();
                }

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //đặt kiểu map
            }
        });
        return view;
    }
    //Hàm chạy khi vào fragment Map
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void xuLyQuyen() {
        mMap.setMyLocationEnabled(true);
        LocationManager service = (LocationManager)getContext().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, true);
        Location location = service.getLastKnownLocation(provider); //lấy vị trí hiện tại
        LatLng vitri = new LatLng(location.getLatitude(),location.getLongitude()); //Lấy tọa độ Latitude: kinh độ + Longitude: Vĩ độ
        //Tạo marker tại vị trí hiện tại
        marker = mMap.addMarker( //Tạo marker
                new MarkerOptions()
                        .position(vitri)
                        .title("Địa điểm")
                        .snippet("test")
                        .icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_ROSE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vitri, 15));//zoom tới vị trí marker với độ zoom 15
        //Hàm nhấn vào bảng đồ để tạo marker
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng arg0) {
//                // TODO Auto-generated method stub
//                Marker mym2 = mMap.addMarker(
//                        new MarkerOptions()
//                                .position(arg0)
//                                .title("dia diem")
//                                .snippet(arg0.latitude +","+arg0.longitude)
//                                .icon(BitmapDescriptorFactory.defaultMarker(
//                                        BitmapDescriptorFactory.HUE_ROSE)));
//                markers.add(mym2);
//            }
//        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub


                return false;
            }

        });
        //Nhấn vào Markert hiện info
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                // TODO Auto-generated method stub
                return null;
            }
            @Override
            public View getInfoContents(Marker arg0) {
                // TODO Auto-generated method stub
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                View info = getLayoutInflater().inflate(R.layout.markerinfo, null);
                TextView tv1 = ((TextView)info.findViewById(R.id.textView));
                tv1.setText(arg0.getTitle());
                TextView tv2 = ((TextView)info.findViewById(R.id.textView2));
                tv2.setText(arg0.getSnippet());
                Toast.makeText(getContext(), "INFO STORE", Toast.LENGTH_SHORT).show();

                return  info;
            }
        });
        //Nhấn vào info hiện detail info
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub
                marker_b=arg0; //gan truoc de can thi ve duong di
                dialog = new Dialog(getContext());
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
// layout to display
                dialog.setContentView(R.layout.markerdetail);
                Button b=(Button)dialog.findViewById(R.id.button1);

                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.cancel();

                        Toast.makeText(getActivity(), "nhan nut",Toast.LENGTH_SHORT).show();
                    }
                });
// set color transpartent
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });




    }
    //Hàm xử lý quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(getContext(), "xx", Toast.LENGTH_SHORT).show();
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(), "xin duoc quyen roi", Toast.LENGTH_SHORT).show();
            xuLyQuyen();
        }
    }

}
