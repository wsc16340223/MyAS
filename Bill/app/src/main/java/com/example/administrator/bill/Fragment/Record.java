package com.example.administrator.bill.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bill.R;
import com.example.administrator.bill.SQL.Bill;
import com.example.administrator.bill.SQL.MySQL;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;

public class Record extends Fragment {

    private static DecimalFormat df = new DecimalFormat("#0.00");

    private String selectedtext = "支出";
    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库

    private Uri imageUri;// 拍照时的图片uri

    TextView style;
    Spinner spinner;
    EditText moneyedit, tip;
    Button button1, button2;
    RadioGroup radiogroup;
    ImageView picture;
    MySQL db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        Radio_group();
        click_button1();
        click_button2();
        setListeners();
    }

    protected void init() {
        radiogroup = (RadioGroup) getView().findViewById(R.id.radiogroup);
        picture = (ImageView) getView().findViewById(R.id.picture);
        style = (TextView) getView().findViewById(R.id.style);
        spinner = (Spinner) getView().findViewById(R.id.spinner);
        button1 = (Button) getView().findViewById(R.id.button3);
        button2 = (Button) getView().findViewById(R.id.button4);
        moneyedit = (EditText) getView().findViewById(R.id.moneyedit);
        tip = (EditText) getView().findViewById(R.id.tip);
        db = new MySQL(this.getActivity());

        picture.setImageResource(R.drawable.add);

        moneyedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String money = s.toString();
                try {
                    if (TextUtils.isEmpty(money)) {
                        money = "0.00";
                    } else {
                        money = df.format(Double.valueOf(money));
                    }
                } catch (NumberFormatException e) {
                    //避免输入多余的小数点
                    moneyedit.setText(money.substring(0, money.length() - 1));
                    moneyedit.setSelection(moneyedit.length());
                }
                if (s.toString().contains(".")) {
                    if (s.toString().indexOf(".") > 9) {
                        s = s.toString().subSequence(0, 9) + s.toString().substring(s.toString()
                                .indexOf("."));
                        moneyedit.setText(s);
                        moneyedit.setSelection(9);
                    }
                } else {
                    if (s.toString().length() > 9) {
                        s = s.toString().subSequence(0, 9);
                        moneyedit.setText(s);
                        moneyedit.setSelection(9);
                    }
                }
                // 判断小数点后只能输入两位
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        moneyedit.setText(s);
                        moneyedit.setSelection(s.length());
                    }
                }
                //如果第一个数字为0，第二个不为点，就不允许输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        moneyedit.setText(s.subSequence(0, 1));
                        moneyedit.setSelection(1);
                    }
                }
                //如果第一个输入的为点，自动在前面加0 要不会闪退
                if (s.toString().startsWith(".")) {
                    moneyedit.setText("0.");
                    moneyedit.setSelection(2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!moneyedit.getText().toString().trim().equals("")) {
                    if (moneyedit.getText().toString().trim().substring(0, 1).equals(".")) {
                        moneyedit.setText(String.format("0%s", moneyedit.getText().toString().trim
                                ()));
                        moneyedit.setSelection(1);
                    }
                }
            }
        });
    }

    protected void Radio_group() {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) getView().findViewById(checkedId);
                selectedtext = radioButton.getText().toString();
                if (selectedtext.equals("支出")) {
                    picture.setImageResource(R.drawable.add);
                    style.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                } else {
                    picture.setImageResource(R.drawable.add);
                    style.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                }
            }
        });
    }

    protected void click_button1() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = moneyedit.getText().toString();
                if (!text.isEmpty()) {
                    if (Double.parseDouble(text) == 0) {
                        Toast.makeText(getActivity(), "支出金额不能为0.", Toast.LENGTH_SHORT).show();
                    } else {
                        picture.setDrawingCacheEnabled(true);
                        Bitmap mBitmap = Bitmap.createBitmap(picture.getDrawingCache());
                        picture.setDrawingCacheEnabled(false);
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
                        Bill one;
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                        Float value = sharedPreferences.getFloat("total", 0);
                        if(selectedtext.equals("支出")) {
                            value -= Float.parseFloat(moneyedit.getText().toString());
                            one = new Bill(db.id(), spinner.getSelectedItem().toString(), Float.parseFloat(moneyedit.getText().toString()), getTime(), tip.getText().toString(), 1, buffer.toByteArray());
                        }
                        else {
                            value += Float.parseFloat(moneyedit.getText().toString());
                            one = new Bill(db.id(), "收入", Float.parseFloat(moneyedit.getText().toString()), getTime(), tip.getText().toString(), 0, buffer.toByteArray());
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putFloat("total", value);
                        //切记最后要使用commit方法将数据写入文件
                        editor.commit();
                        db.add(one);

                        //记录成功提示，并清空输入
                        Toast.makeText(getContext(), "记录成功！", Toast.LENGTH_SHORT).show();
                        picture.setImageResource(R.drawable.add);
                        moneyedit.setText("");
                        tip.setText("");
                    }
                } else {
                    Toast.makeText(getActivity(), "金额栏不能为空.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void click_button2() {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture.setImageResource(R.drawable.add);
                moneyedit.setText("");
                tip.setText("");
            }
        });
    }

    private String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String hour;
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = String.valueOf(cal.get(Calendar.DATE));
        if (cal.get(Calendar.AM_PM) == 0)
            hour = String.valueOf(cal.get(Calendar.HOUR));
        else
            hour = String.valueOf(cal.get(Calendar.HOUR) + 12);
        String minute = String.valueOf(cal.get(Calendar.MINUTE));
        String second = String.valueOf(cal.get(Calendar.SECOND));
        String time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        return time;
    }

    private void setListeners() {

        // 展示图片按钮点击事件
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoOrSelectPicture();// 拍照或者调用图库
            }
        });

    }

    private void takePhotoOrSelectPicture() {
        CharSequence[] items = {"拍照","图库"};// 裁剪items选项

        // 弹出对话框提示用户拍照或者是通过本地图库选择图片
        new AlertDialog.Builder(getActivity())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            // 选择了拍照
                            case 0:
                                // 创建Intent，用于启动手机的照相机拍照
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                // 启动intent开始拍照
                                startActivityForResult(intent, TAKE_PHOTO);
                                break;
                            // 调用系统图库
                            case 1:

                                // 创建Intent，用于打开手机本地图库选择图片
                                Intent intent1 = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // 启动intent打开本地图库
                                startActivityForResult(intent1,LOCAL_CROP);
                                break;
                        }

                    }
                }).show();
    }


    /**
     * 调用startActivityForResult方法启动一个intent后，
     * 可以在该方法中拿到返回的数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case TAKE_PHOTO:// 拍照
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                    picture.setImageBitmap(bitmap);
                }
                break;
            case LOCAL_CROP:// 系统图库

                if(resultCode == RESULT_OK){
                    // 创建intent用于裁剪图片
                    Intent intent1 = new Intent("com.android.camera.action.CROP");
                    // 获取图库所选图片的uri
                    Uri uri = data.getData();
                    intent1.setDataAndType(uri,"image/*");
                    //  设置裁剪图片的宽高
                    intent1.putExtra("outputX", 300);
                    intent1.putExtra("outputY", 300);
                    // 裁剪后返回数据
                    intent1.putExtra("return-data", true);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent1, CROP_PHOTO);
                }

                break;
            case CROP_PHOTO:// 裁剪后展示图片
                if(resultCode == RESULT_OK){
                    try{
                        // 展示拍照后裁剪的图片
                        if(imageUri != null){
                            // 创建BitmapFactory.Options对象
                            BitmapFactory.Options option = new BitmapFactory.Options();
                            // 属性设置，用于压缩bitmap对象
                            option.inSampleSize = 2;
                            option.inPreferredConfig = Bitmap.Config.RGB_565;
                            // 根据文件流解析生成Bitmap对象
                            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri), null, option);
                            // 展示图片
                            picture.setImageBitmap(bitmap);
                        }

                        // 展示图库中选择裁剪后的图片
                        if(data != null){
                            // 根据返回的data，获取Bitmap对象
                            Bitmap bitmap = data.getExtras().getParcelable("data");
                            // 展示图片
                            picture.setImageBitmap(bitmap);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

}