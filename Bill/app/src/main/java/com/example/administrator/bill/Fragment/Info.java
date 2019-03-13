package com.example.administrator.bill.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bill.R;
import com.example.administrator.bill.SQL.Bill;
import com.example.administrator.bill.SQL.MySQL;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Info extends Fragment {

    TextView balance, single;
    MySQL db;
    private float rec = 0;
    private PieChart mPieChart;
    SharedPreferences sharedPreferences;
    DecimalFormat fnum = new DecimalFormat("##0.00");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initView();
        click();
    }

    protected void init() {
        balance = (TextView)getView().findViewById(R.id.balance);
        single = (TextView)getView().findViewById(R.id.single);
        db = new MySQL(getActivity());

        sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        Float value = sharedPreferences.getFloat("total", 0);
        balance.setText(fnum.format(value));

        sharedPreferences = getActivity().getSharedPreferences("pie", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.clear().commit();

        Cursor c;
        if(db.id() != 0) {
            c = db.get();
            do {
                if(c.getInt(c.getColumnIndex("style")) == 1) {
                    Float value1 = sharedPreferences.getFloat(c.getString(c.getColumnIndex("type")), 0);
                    value1 += Float.parseFloat(c.getString(c.getColumnIndex("money")));
                    rec += Float.parseFloat(c.getString(c.getColumnIndex("money")));
                    editor.putFloat(c.getString(c.getColumnIndex("type")), value1);
                    editor.commit();
                }
            }while (c.moveToNext());
        }
    }

    //初始化View
    private void initView() {

        //饼状图
        mPieChart = (PieChart)getView().findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(0, 10, 50, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if(sharedPreferences.getFloat("食品", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("食品", 0) * 100, "食品"));
        if(sharedPreferences.getFloat("衣着", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("衣着", 0) * 100, "衣着"));
        if(sharedPreferences.getFloat("居住", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("居住", 0) * 100, "居住"));
        if(sharedPreferences.getFloat("家庭设备用品及服务", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("家庭设备用品及服务", 0) * 100, "家庭设备用品及服务"));
        if(sharedPreferences.getFloat("医疗保健", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("医疗保健", 0) * 100, "医疗保健"));
        if(sharedPreferences.getFloat("交通和通信", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("交通和通信", 0) * 100, "交通和通信"));
        if(sharedPreferences.getFloat("教育文化娱乐服务", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("教育文化娱乐服务", 0) * 100, "教育文化娱乐服务"));
        if(sharedPreferences.getFloat("其他商品和服务", 0) != 0)
            entries.add(new PieEntry(sharedPreferences.getFloat("其他商品和服务", 0) * 100, "其他商品和服务"));

        //设置数据
        setData(entries);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
    }

    //设置中间文字
    private SpannableString generateCenterSpannableText() {
        //原文：MPAndroidChart\ndeveloped by Philipp Jahoda
        SpannableString s = new SpannableString("最近60次记账中的支出总额:\n" + fnum.format(rec));
        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        // s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        // s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "支出类型");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    protected void click() {
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                single.setText(String.valueOf(e.getY()/ 100));
            }

            @Override
            public void onNothingSelected() {
                single.setText("0.0");
            }
        });
    }

}