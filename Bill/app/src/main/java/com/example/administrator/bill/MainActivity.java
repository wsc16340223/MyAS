package com.example.administrator.bill;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.bill.Fragment.Info;
import com.example.administrator.bill.Fragment.Lists;
import com.example.administrator.bill.Fragment.Record;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    BottomNavigationBar bottomnavigationbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Bottom_bar();
    }

    protected void init() {
        bottomnavigationbar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transactions  = fm.beginTransaction();
        transactions .add(R.id.fragment, new Record());
        transactions .commit();

    }

    public void Bottom_bar() {
        bottomnavigationbar
                .setActiveColor("#000000")
                .setInActiveColor("#c0c0c0")
                .setBarBackgroundColor("#FFFFFF")
                .addItem(new BottomNavigationItem(R.drawable.pen, "记录"))
                .addItem(new BottomNavigationItem(R.drawable.list, "账单"))
                .addItem(new BottomNavigationItem(R.drawable.user, "账户"))
                .setFirstSelectedPosition(0)//设置默认选择item
                .initialise();
        setBottomNavigationItem(bottomnavigationbar, 5, 40, 15);
        bottomnavigationbar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                switch(position) {
                    case 0:
                        FragmentManager fm1 = getSupportFragmentManager();
                        FragmentTransaction transactions1  = fm1.beginTransaction();
                        transactions1 .replace(R.id.fragment, new Record());
                        transactions1 .commit();
                        break;
                    case 1:
                        FragmentManager fm2 = getSupportFragmentManager();
                        FragmentTransaction transactions2  = fm2.beginTransaction();
                        transactions2 .replace(R.id.fragment, new Lists());
                        transactions2 .commit();
                        break;
                    case 2:
                        FragmentManager fm3 = getSupportFragmentManager();
                        FragmentTransaction transactions3  = fm3.beginTransaction();
                        transactions3 .replace(R.id.fragment, new Info());
                        transactions3 .commit();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, int space, int imgLen, int textSize){
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.getName().equals("mTabContainer")){
                try{
                    //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                    for(int j = 0; j < mTabContainer.getChildCount(); j++){
                        //获取到容器内的各个Tab
                        View view = mTabContainer.getChildAt(j);
                        //获取到Tab内的各个显示控件
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(56));
                        FrameLayout container = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_container);
                        container.setLayoutParams(params);
                        container.setPadding(dip2px(12), dip2px(0), dip2px(12), dip2px(0));

                        //获取到Tab内的文字控件
                        TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                        labelView.setIncludeFontPadding(false);
                        labelView.setPadding(0,0,0,dip2px(20-textSize - space/2));

                        //获取到Tab内的图像控件
                        ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = new FrameLayout.LayoutParams(dip2px(imgLen), dip2px(imgLen));
                        params.setMargins(0,0,0,space/2);
                        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        iconView.setLayoutParams(params);
                    }
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}