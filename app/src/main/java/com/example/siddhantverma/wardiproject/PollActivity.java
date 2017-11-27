package com.example.siddhantverma.wardiproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PollActivity extends AppCompatActivity  {


    private LineChart lineChart;
    Button potholes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        lineChart = (LineChart) findViewById(R.id.linechart);
        potholes=(Button)findViewById(R.id.Potholes);
        potholes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(getApplicationContext(),"click hua",Toast.LENGTH_SHORT).show();
                String s="bhaggg";
                //Log.v(s);
                Intent i = new Intent(PollActivity.this, AccelActivity.class);
                startActivity(i);

            }
        });

        //lineChart.setDrawBarShadow(true);
        //lineChart.setDrawValueAboveBar(true);
      //  lineChart.setOnChartGestureListener(PollActivity.this);
        //lineChart.setOnChartValueSelectedListener(PollActivity.this);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

//        lineChart.setMaxVisibleValueCount(100);
        lineChart.setPinchZoom(true);

        LimitLine upperLimit=new LimitLine(65f, "Danger");
        upperLimit.setLineWidth(4f);
        upperLimit.enableDashedLine(10f,10f,0f);
        upperLimit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upperLimit.setTextSize(15f);

        YAxis leftAxis=lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upperLimit);
        leftAxis.enableGridDashedLine(10f,10f,0);
        leftAxis.setDrawLimitLinesBehindData(true);


        ArrayList<Entry> Entries=new ArrayList<>();



        Entries.add(new BarEntry(0,0));
        Entries.add(new BarEntry(1,0));
        Entries.add(new BarEntry(2,0));
        Entries.add(new BarEntry(3,0));
        Entries.add(new BarEntry(4,0));
        Entries.add(new BarEntry(5,56));
        Entries.add(new BarEntry(6,34));
        Entries.add(new BarEntry(7,94));
        Entries.add(new BarEntry(8,65));
        Entries.add(new BarEntry(9,80));
        Entries.add(new BarEntry(10,24));
        Entries.add(new BarEntry(11,40));

        LineDataSet lineDataSet=new LineDataSet(Entries, "Data Set 1");
        lineDataSet.setFillAlpha(110);

        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(3f);
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data= new LineData(dataSets);
        lineChart.setData(data);
        String[] months=new String [] {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        XAxis xAxis=lineChart.getXAxis();
        xAxis.setValueFormatter(new MyAxis(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        LineData data= new LineData(barDataSet);
//        data.setBarWidth(0.8f);
//        lineChart.setData(data);
//
//        String[] months=new String [] {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
//        XAxis xAxis=barChart.getXAxis();
//        xAxis.setValueFormatter(new AccelActivity.MyAxis(months));
//        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
    }
    public class MyAxis implements IAxisValueFormatter
    {
        private String[] mval;
        public MyAxis(String[] values)

        {
            this.mval=values;
        }
        @Override
        public String getFormattedValue(float value, AxisBase axisBase)
        {
            return mval[(int)value];

        }
    }
}
