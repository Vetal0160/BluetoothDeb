package com.vetal.bluetoothdeb.util;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.vetal.bluetoothdeb.GraphView;
import com.vetal.bluetoothdeb.MainActivity;
import com.vetal.bluetoothdeb.R;

import static com.vetal.bluetoothdeb.GraphView.getDataSize;
import static com.vetal.bluetoothdeb.GraphView.setDataSize;

public class Setup extends MainActivity implements View.OnClickListener {
    public float y = 0;
    public int x = 0;
    public static boolean Checked = false;

    CheckBox dinamic_mode;
    EditText et_y, et_max_y, et_max_bal, et_max_view, et_ytmp, et_max_ytmp;
    Button Back, Set_Min_Y, Get_Min_Y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        et_y = (EditText) findViewById(R.id.et_Set_Min_Y);
        et_max_y = (EditText) findViewById(R.id.et_Set_Max_Y);
        et_ytmp = (EditText) findViewById(R.id.et_Set_Min_Y_Tmp);
        et_max_ytmp = (EditText) findViewById(R.id.et_Set_Max_Y_Tmp);
        et_max_bal = (EditText) findViewById(R.id.et_Max_Bal);
        et_max_view = (EditText) findViewById(R.id.et_Max_View);
        dinamic_mode = (CheckBox) findViewById(R.id.dinamic_mode);
        Back = (Button) findViewById(R.id.back);
        Set_Min_Y = (Button) findViewById(R.id.set_min_y);
        Get_Min_Y = (Button) findViewById(R.id.get_min_y);

        Back.setOnClickListener(this);
        Set_Min_Y.setOnClickListener(this);
        Get_Min_Y.setOnClickListener(this);

        if (Checked) dinamic_mode.setChecked(true);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, GraphView.class);
                startActivity(intent);
                Checked = dinamic_mode.isChecked();
                break;
            case R.id.set_min_y:
                GraphView.setAxisMinValue(SeT_Min_Y());
                GraphView.setAxisMaxValue(SeT_Max_Y());
                setDataSize(SeT_Data());
                GraphView.setViewSize(SeT_View());
                GraphView.setAxisMinValueTmp(SeT_Min_YTmp());
                GraphView.setAxisMaxValueTmp(SeT_Max_YTmp());
                Toast.makeText(getApplicationContext(), "set", Toast.LENGTH_LONG).show();
                break;
            case R.id.get_min_y:
                et_y.setText(String.valueOf(GraphView.getAxisMinValue()));
                et_max_y.setText(String.valueOf(GraphView.getAxisMaxValue()));
                et_max_bal.setText(String.valueOf(getDataSize()));
                et_max_view.setText(String.valueOf(GraphView.getViewSize()));
                et_ytmp.setText(String.valueOf(GraphView.getAxisMinValueTmp()));
                et_max_ytmp.setText(String.valueOf(GraphView.getAxisMaxValueTmp()));
                break;

            default:
                break;
        }

    }

    float SeT_Min_Y() {
        if (TextUtils.isEmpty(et_y.getText().toString())
                || TextUtils.isEmpty(et_y.getText().toString())) {
            return 3;
        }
        y = Float.parseFloat(et_y.getText().toString());
        return y;
    }

    float SeT_Max_Y() {
        if (TextUtils.isEmpty(et_max_y.getText().toString())
                || TextUtils.isEmpty(et_max_y.getText().toString())) {
            return 4;
        }
        y = Float.parseFloat(et_max_y.getText().toString());
        return y;
    }

    float SeT_Min_YTmp() {
        if (TextUtils.isEmpty(et_ytmp.getText().toString())
                || TextUtils.isEmpty(et_ytmp.getText().toString())) {
            return 0;
        }
        y = Float.parseFloat(et_ytmp.getText().toString());
        return y;
    }

    float SeT_Max_YTmp() {
        if (TextUtils.isEmpty(et_max_ytmp.getText().toString())
                || TextUtils.isEmpty(et_max_ytmp.getText().toString())) {
            return 35;
        }
        y = Float.parseFloat(et_max_ytmp.getText().toString());
        return y;
    }

    int SeT_View() {
        if (TextUtils.isEmpty(et_max_view.getText().toString())
                || TextUtils.isEmpty(et_max_view.getText().toString())) {
            return 160;
        }
        x = Integer.parseInt(et_max_view.getText().toString());
        return x;
    }

    int SeT_Data() {
        if (TextUtils.isEmpty(et_max_bal.getText().toString())
                || TextUtils.isEmpty(et_max_bal.getText().toString())) {
            return 160;
        }
        x = Integer.parseInt(et_max_bal.getText().toString());
        return x;
    }

}
