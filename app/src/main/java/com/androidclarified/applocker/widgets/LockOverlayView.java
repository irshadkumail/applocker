package com.androidclarified.applocker.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.androidclarified.applocker.R;

/**
 * Created by My Pc on 10/23/2016.
 */

public class LockOverlayView extends RelativeLayout implements View.OnClickListener {


    private EditText passwordText;
    private Button[] buttons;
    private LayoutInflater layoutInflater;


    public LockOverlayView(Context context) {
        super(context);

    }

    public void init() {
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.lock_overlay_view, this, true);

    }

    public void initButtons() {
        buttons = new Button[13];

        buttons[0]= (Button) findViewById(R.id.lock_overlay_zero);
        buttons[1]= (Button) findViewById(R.id.lock_overlay_one);
        buttons[2]= (Button) findViewById(R.id.lock_overlay_two);
        buttons[3]= (Button) findViewById(R.id.lock_overlay_three);
        buttons[4]= (Button) findViewById(R.id.lock_overlay_four);
        buttons[5]= (Button) findViewById(R.id.lock_overlay_five);
        buttons[6]= (Button) findViewById(R.id.lock_overlay_six);
        buttons[7]= (Button) findViewById(R.id.lock_overlay_seven);
        buttons[8]= (Button) findViewById(R.id.lock_overlay_eight);
        buttons[9]= (Button) findViewById(R.id.lock_overlay_nine);
        buttons[10]=(Button) findViewById(R.id.lock_overlay_backspace);

        for (int i=0;i<buttons.length;i++)
        {
            buttons[i].setOnClickListener(this);
        }



    }


    @Override
    public void onClick(View v) {
        String text=passwordText.getText().toString();
        switch (v.getId())
        {
            case R.id.lock_overlay_zero:
                passwordText.setText(text+"0");
                break;
            case R.id.lock_overlay_one:
                passwordText.setText(text+"1");
                break;
            case R.id.lock_overlay_two:
                passwordText.setText(text+"2");
                break;
            case R.id.lock_overlay_three:
                passwordText.setText(text+"3");
                break;
            case R.id.lock_overlay_four:
                passwordText.setText(text+"4");
                break;
            case R.id.lock_overlay_five:
                passwordText.setText(text+"5");
                break;
            case R.id.lock_overlay_six:
                passwordText.setText(text+"6");
                break;
            case R.id.lock_overlay_seven:
                passwordText.setText(text+"7");
                break;
            case R.id.lock_overlay_eight:
                passwordText.setText(text+"8");
                break;
            case R.id.lock_overlay_nine:
                passwordText.setText(text+"9");
                break;
        }

    }

}
