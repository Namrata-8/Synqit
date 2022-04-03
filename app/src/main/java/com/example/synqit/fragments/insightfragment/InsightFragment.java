package com.example.synqit.fragments.insightfragment;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.adapters.LinkEngagementAdapter;
import com.example.synqit.databinding.FragmentInsightBinding;
import com.example.synqit.fragments.homefragment.HomeFragmentViewModel;
import com.example.synqit.fragments.insightfragment.model.GetProfileVisitResponse;
import com.example.synqit.fragments.insightfragment.model.ParamGetProfileVisit;
import com.example.synqit.ui.addlink.AddLinkActivity;
import com.example.synqit.ui.maps.MapsActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InsightFragment extends Fragment implements OnMapReadyCallback {

    private FragmentInsightBinding fragmentInsightBinding;
    private InsightFragmentViewModel insightFragmentViewModel;

    LineChart line_chart;
    private PieChart pieChart;
    String[] months;
    private RecyclerView rvLinkEngagement;
    FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentInsightBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_insight, container, false);
        fragmentInsightBinding.setViewModel(new InsightFragmentViewModel());
        fragmentInsightBinding.executePendingBindings();

        insightFragmentViewModel = new ViewModelProvider(this).get(InsightFragmentViewModel.class);
        //View view = inflater.inflate(R.layout.fragment_insight, container, false);

        line_chart = fragmentInsightBinding.getRoot().findViewById(R.id.line_chart);
        pieChart = fragmentInsightBinding.getRoot().findViewById(R.id.pieChart);
        rvLinkEngagement = fragmentInsightBinding.getRoot().findViewById(R.id.rvLinkEngagement);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = year + "-" + (month+1) + "-" + day;

        if (getSelectedCard(getActivity()) != null) {
            fragmentInsightBinding.tvName.setText(getSelectedCard(getActivity()).getProfileName());
            if (getSelectedCard(getActivity()).getPlan() > 0){
                fragmentInsightBinding.tvProBusiness.setVisibility(View.VISIBLE);
            }else {
                fragmentInsightBinding.tvProBusiness.setVisibility(View.GONE);
            }
            RequestOptions requestOptions2 = new RequestOptions();
            requestOptions2.placeholder(R.drawable.ic_add_profile);
            Glide.with(fragmentInsightBinding.ivImage.getContext())
                    .setDefaultRequestOptions(requestOptions2)
                    .load(getSelectedCard(getActivity()).getDisplayPicture()).into(fragmentInsightBinding.ivImage);
            ParamGetProfileVisit paramGetProfileVisit;
            if (getSelectedCard(getActivity()).getUserUID() != null) {
                paramGetProfileVisit = new ParamGetProfileVisit(getSelectedCard(getActivity()).getUserUID(), date, date);
            }else {
                paramGetProfileVisit = new ParamGetProfileVisit(getSelectedCard(getActivity()).getUserID(), date, date);
            }
            Log.e("paramGetProfileVisit", new Gson().toJson(paramGetProfileVisit));
            insightFragmentViewModel.getProfile(paramGetProfileVisit, getActivity());

            insightFragmentViewModel.getProfileVisit().observe(getViewLifecycleOwner(), new Observer<GetProfileVisitResponse>() {
                @Override
                public void onChanged(GetProfileVisitResponse getProfileVisitResponse) {
                    if (getProfileVisitResponse != null){
                        if (getProfileVisitResponse.isSuccess()){
                            fragmentInsightBinding.tvCount.setText(String.valueOf(getProfileVisitResponse.getConnections().getCurrentConnection()));
                            fragmentInsightBinding.tvConnectionsCount.setText(String.valueOf(getProfileVisitResponse.getConnections().getPreviousWeekConnection()));
                            fragmentInsightBinding.tvCountStreak.setText(String.valueOf(getProfileVisitResponse.getStreakCount().getCurrentStreak()));
                            fragmentInsightBinding.tvStreakCount.setText(String.valueOf(getProfileVisitResponse.getStreakCount().getPreviousStreak()));
                            fragmentInsightBinding.tvCountView.setText(String.valueOf(getProfileVisitResponse.getVisitCount().getCurrentVisit()));
                            fragmentInsightBinding.tvViewsCount.setText(String.valueOf(getProfileVisitResponse.getVisitCount().getPreviousVisit()));
                            rvLinkEngagement.setAdapter(new LinkEngagementAdapter(getActivity(), getProfileVisitResponse.getViewsApps()));

                            fragmentInsightBinding.btnExpandMap.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("UserDataMaps", new Gson().toJson(getProfileVisitResponse.getUserData()));
                                    startActivity(new Intent(getActivity(), MapsActivity.class).putExtra("UserDataMaps", new Gson().toJson(getProfileVisitResponse.getUserData())));
                                }
                            });
                        }
                    }
                }
            });
        }

        setLineChart();
        setupPieChart();
        loadPieChartData();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        fragmentInsightBinding.tvConnections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoBottomSheet();
            }
        });

        fragmentInsightBinding.tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoBottomSheet();
            }
        });

        fragmentInsightBinding.tvStreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoBottomSheet();
            }
        });

        fragmentInsightBinding.tvTopViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoBottomSheet();
            }
        });

        fragmentInsightBinding.tvTimePeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), fragmentInsightBinding.tvTimePeriod);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.insight_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();
            }
        });

        return fragmentInsightBinding.getRoot();
    }

    private void openInfoBottomSheet() {
        final Dialog dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(true);
        dialog2.setCanceledOnTouchOutside(true);
        dialog2.setContentView(R.layout.dialog_info_add_link);

        MaterialButton btnDone = dialog2.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        dialog2.show();
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setLineChart() {
        List entries = (List)(new ArrayList());
        entries.add(new Entry(0.0F, 80.0F));
        entries.add(new Entry(1.0F, 120.0F));
        entries.add(new Entry(2.0F, 110.0F));
        entries.add(new Entry(3.0F, 90.0F));
        entries.add(new Entry(4.0F, 140.0F));
        entries.add(new Entry(5.0F, 150.0F));
        entries.add(new Entry(6.0F, 200.0F));
        LineDataSet dataSet = new LineDataSet(entries,"");
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        dataSet.setDrawHighlightIndicators(false);
        dataSet.setValueFormatter(new DefaultValueFormatter(0));
        dataSet.setColor(getActivity().getResources().getColor(R.color.chart_line));
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(getActivity().getResources().getDrawable(R.drawable.bg_grapgh));
        XAxis xAxis = line_chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        months = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        xAxis.setTextColor(MaterialColors.getColor(line_chart, R.attr.text_color));
        xAxis.setGranularity(1.0F);
        xAxis.setValueFormatter(new MyAxisFormatter());
        YAxis yAxisRight = line_chart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = line_chart.getAxisLeft();
        yAxisLeft.setTextColor(MaterialColors.getColor(line_chart, R.attr.text_color));
        yAxisLeft.setGranularity(40.0F);
        LineData data = new LineData(new ILineDataSet[]{(ILineDataSet)dataSet});
        line_chart.getDescription().setEnabled(false);
        line_chart.getLegend().setEnabled(false);
        line_chart.getAxisRight().setDrawAxisLine(false);
        line_chart.getAxisLeft().setDrawAxisLine(false);
        line_chart.getXAxis().setDrawAxisLine(false);
        line_chart.getAxisRight().setDrawGridLines(false);
        line_chart.getAxisLeft().setDrawGridLines(false);
        line_chart.getXAxis().setDrawGridLines(false);
        line_chart.setTouchEnabled(false);
        line_chart.setData(data);
        line_chart.invalidate();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private class MyAxisFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return months[(int)value];
        }
    }

    private ArrayList<Entry> dataValue1(){
        ArrayList<Entry> dataValue = new ArrayList<>();
        dataValue.add(new Entry(0, 5));
        dataValue.add(new Entry(1, 2));
        dataValue.add(new Entry(2, 7));
        dataValue.add(new Entry(3, 3));
        dataValue.add(new Entry(4, 5));
        dataValue.add(new Entry(5, 1));
        dataValue.add(new Entry(6, 3));

        return dataValue;
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(MaterialColors.getColor(pieChart, R.attr.text_color));
        pieChart.setCenterText("%");
        pieChart.setCenterTextColor(getActivity().getResources().getColor(R.color.pir_center));
        pieChart.setCenterTextSize(50);
        pieChart.setHoleRadius(64f);//18f
        pieChart.setHoleColor(MaterialColors.getColor(pieChart, R.attr.btn_text_color));
        pieChart.setDrawSliceText(false); // To remove slice text
        pieChart.setDrawMarkers(false); // To remove markers when click
        pieChart.setDrawEntryLabels(false); // To remove labels from piece of pie
        pieChart.getDescription().setEnabled(false); // To remove description of pie

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextColor(MaterialColors.getColor(line_chart, R.attr.text_color));
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.65f, "Social Media"));
        entries.add(new PieEntry(0.12f, "Contact info"));
        entries.add(new PieEntry(0.05f, "Music"));
        entries.add(new PieEntry(0.10f, "Payments"));
        entries.add(new PieEntry(0.08f, "Others"));

        /*ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }*/

        final int[] MY_COLORS = {Color.rgb(111,140,177), Color.rgb(152,205,157), Color.rgb(240,225,100),Color.rgb(254,183,119), Color.rgb(249,152,66)};

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<android.location.Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }
}