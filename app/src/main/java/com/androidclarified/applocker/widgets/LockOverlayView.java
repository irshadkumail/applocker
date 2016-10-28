package com.androidclarified.applocker.widgets;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.services.AppCheckerService;

/**
 * Created by My Pc on 10/23/2016.
 */

public class LockOverlayView extends RelativeLayout implements View.OnClickListener {


    private TextView passwordText;
    private Button[] buttons;
    private LayoutInflater layoutInflater;
    private boolean isPasswordEntered;
    private AppCheckerService appCheckerService;
    private OverlayScreenListener overlayScreenListener;


    public LockOverlayView(Context context) {
        super(context);
        appCheckerService = (AppCheckerService) context;
        init();

    }

    public void init() {
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.lock_overlay_view, this, true);
        initButtons();

    }

    public void setOverlayScreenListener(OverlayScreenListener overlayScreenListener) {
        this.overlayScreenListener = overlayScreenListener;
    }

    public void initButtons() {
        buttons = new Button[11];

        passwordText = (TextView) findViewById(R.id.locker_overlay_text);
        buttons[0] = (Button) findViewById(R.id.lock_overlay_zero);
        buttons[1] = (Button) findViewById(R.id.lock_overlay_one);
        buttons[2] = (Button) findViewById(R.id.lock_overlay_two);
        buttons[3] = (Button) findViewById(R.id.lock_overlay_three);
        buttons[4] = (Button) findViewById(R.id.lock_overlay_four);
        buttons[5] = (Button) findViewById(R.id.lock_overlay_five);
        buttons[6] = (Button) findViewById(R.id.lock_overlay_six);
        buttons[7] = (Button) findViewById(R.id.lock_overlay_seven);
        buttons[8] = (Button) findViewById(R.id.lock_overlay_eight);
        buttons[9] = (Button) findViewById(R.id.lock_overlay_nine);
        buttons[10] = (Button) findViewById(R.id.lock_overlay_backspace);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }


    }


    @Override
    public void onClick(View v) {
        Log.d("Irshad", "Viewq Clicked");
        String text = passwordText.getText().toString();
        switch (v.getId()) {
            case R.id.lock_overlay_zero:
                passwordText.setText(text + "0");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_one:
                passwordText.setText(text + "1");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_two:
                passwordText.setText(text + "2");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_three:
                passwordText.setText(text + "3");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_four:
                passwordText.setText(text + "4");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_five:
                passwordText.setText(text + "5");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_six:
                passwordText.setText(text + "6");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_seven:
                passwordText.setText(text + "7");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_eight:
                passwordText.setText(text + "8");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_nine:
                passwordText.setText(text + "9");
                checkForPasswordLength(text);
                break;
            case R.id.lock_overlay_backspace:
                if (!text.isEmpty())
                    passwordText.setText(removeLastChar(text));
                break;

        }

    }

    private void checkForPasswordLength(String string) {
        if (string.length() >= 4) {
            passwordText.setEnabled(false);
            checkPassword(string);
        }
    }

    private String removeLastChar(String string) {
        return string.substring(0, string.length() - 1);
    }

    private void checkPassword(String text) {
        if (text.equalsIgnoreCase("1234")) {
            AppCheckerService.isPasswordEntered = true;
            overlayScreenListener.hideOverlayScreen();
            passwordText.setText("");
        } else {
            passwordText.setEnabled(true);
            passwordText.setText("");
            Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
        }

    }


}
