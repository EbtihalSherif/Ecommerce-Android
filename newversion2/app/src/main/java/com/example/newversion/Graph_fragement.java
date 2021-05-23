package com.example.newversion;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Graph_fragement extends Fragment {
    float jan = 0, feb = 0, march = 0, april = 0, may = 0, june, july = 0, august = 0, sep = 0, oct = 0, nov = 0, dec = 0;
    ArrayList<Float> Order_months = new ArrayList<>(Arrays.asList(new Float[12]));

    LineGraphSeries<DataPoint> series;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        BarChart Chart = (BarChart) view.findViewById(R.id.graphbarChart);

try {
    EcommerceDBHelper database = new EcommerceDBHelper(getActivity());
    Cursor c = database.get_customer_orders(Integer.valueOf(MainActivity.currrent_user_ID));
    Collections.fill(Order_months, 0f);
    while (!c.isAfterLast()) {
        String date = c.getString(1);
        //  Toast.makeText(getActivity().getApplicationContext(), date, Toast.LENGTH_LONG).show();
        if (date.contains("Jan")) {
            Order_months.set(0, Order_months.get(0) + 1f);
            jan++;
        } else if (date.contains("Feb")) {
            Order_months.set(1, Order_months.get(1) + 1f);

            feb++;
        } else if (date.contains("Mar")) {
            Order_months.set(2, Order_months.get(2) + 1f);

            march++;
        } else if (date.contains("Apr")) {
            Order_months.set(3, Order_months.get(3) + 1f);

            april++;
        } else if (date.contains("May")) {
            Order_months.set(4, Order_months.get(4) + 1f);
            may++;
        } else if (date.contains("Jun")) {
            Order_months.set(5, Order_months.get(5) + 1f);
            june++;
        } else if (date.contains("July")) {
            Order_months.set(6, Order_months.get(6) + 1f);
            july++;
        } else if (date.contains("Aug")) {
            Order_months.set(7, Order_months.get(7) + 1f);
            august++;
        } else if (date.contains("Sep")) {
            Order_months.set(8, Order_months.get(8) + 1f);
            sep++;
        } else if (date.contains("Oct")) {
            Order_months.set(9, Order_months.get(9) + 1f);
            oct++;
        } else if (date.contains("Nov")) {
            Order_months.set(10, Order_months.get(10) + 1f);
            nov++;
        } else if (date.contains("Dec")) {
            Order_months.set(11, Order_months.get(11) + 1f);
            dec++;
        }
        c.moveToNext();
    }
    ArrayList<String> month = new ArrayList<>();

    month.add("Jan");
    month.add("Feb");
    month.add("Mar");
    month.add("Apr");
    month.add("May");
    month.add("Jun");
    month.add("July");
    month.add("Aug");
    month.add("Sep");
    month.add("Oct");
    month.add("Nov");
    month.add("Dec");
    ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 10));
//        entries.add(new BarEntry(2, 10));
//        entries.add(new BarEntry(3, 10));

    for (int i = 0; i < 12; i++) {
        // if( Order_months.get(i) !=0) {
        entries.add(new BarEntry(i, Order_months.get(i)));
        //  Order_months.get(i)
        //   }
    }

    BarDataSet dataset = new BarDataSet(entries, "No of orders");
    dataset.setColors(ColorTemplate.MATERIAL_COLORS);
    dataset.setValueTextColor(Color.BLACK);
    dataset.setValueTextSize(16f);

    Chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(month));


    BarData thedata = new BarData(dataset);
    //  barChart.setFitBars(true);
    Chart.setData(thedata);
    Chart.setTouchEnabled(true);
    Chart.setDragEnabled(true);
    Chart.setScaleEnabled(true);
    Chart.animateY(2000);

}
catch (Exception e)
{
    Toast.makeText(getActivity().getApplicationContext(), "No orders yet", Toast.LENGTH_LONG).show();
}
        return view;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
