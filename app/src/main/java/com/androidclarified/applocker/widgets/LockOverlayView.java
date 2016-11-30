package com.androidclarified.applocker.widgets;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.services.AppCheckerService;
import com.androidclarified.applocker.utils.AppConstants;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.androidclarified.applocker.utils.AppUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;

/**
 * Created by My Pc on 10/23/2016.
 */

public class LockOverlayView extends RelativeLayout implements View.OnClickListener {


    private TextView passwordText;

    private TextView[] buttons;
    private LayoutInflater layoutInflater;
    private RelativeLayout mainLayout;
    private ImageView mainImage;
    private ImageView lockIcon;
    private TextView passwordHeadingText;
    private boolean isPasswordEntered;
    // private AppCheckerService appCheckerService;
    private OverlayScreenListener overlayScreenListener;

    private Handler handler;

    private boolean isConfirmPasswordMode = false;
    private String previousPassword;
    private boolean confirmPasswordSecondEntry = false;


    public LockOverlayView(Context context, boolean isConfirmPasswordMode) {
        super(context);
        this.isConfirmPasswordMode = isConfirmPasswordMode;
        init();

    }

    public LockOverlayView(Context context) {
        super(context);
        init();

    }

    public LockOverlayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();

    }

    public LockOverlayView(Context context, AttributeSet attributeSet, int tdef) {
        super(context, attributeSet, tdef);
        init();

    }


    public void init() {
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflateTheme();
        initButtons();
    }
    private void inflateTheme()
    {
        int themeIndex = AppSharedPreferences.getLockThemePreference(getContext());

        if (themeIndex==AppConstants.GALLERY_THEME)
            layoutInflater.inflate(R.layout.lock_overlay_image_theme, this, true);
        else
            layoutInflater.inflate(R.layout.lock_overlay_normal,this,true);

    }

    public void setOverlayScreenListener(OverlayScreenListener overlayScreenListener) {
        this.overlayScreenListener = overlayScreenListener;
    }

    public void initButtons() {
        buttons = new TextView[11];

        passwordHeadingText = (TextView) findViewById(R.id.lock_overlay_password_heading_text);

        mainLayout = (RelativeLayout) findViewById(R.id.lock_overlay_main_layout);
        mainImage = (ImageView) findViewById(R.id.main_image);
        passwordText = (TextView) findViewById(R.id.locker_overlay_text);
        lockIcon = (ImageView) findViewById(R.id.lock_overlay_normal_icon);
        buttons[0] = (TextView) findViewById(R.id.lock_overlay_zero);
        buttons[1] = (TextView) findViewById(R.id.lock_overlay_one);
        buttons[2] = (TextView) findViewById(R.id.lock_overlay_two);
        buttons[3] = (TextView) findViewById(R.id.lock_overlay_three);
        buttons[4] = (TextView) findViewById(R.id.lock_overlay_four);
        buttons[5] = (TextView) findViewById(R.id.lock_overlay_five);
        buttons[6] = (TextView) findViewById(R.id.lock_overlay_six);
        buttons[7] = (TextView) findViewById(R.id.lock_overlay_seven);
        buttons[8] = (TextView) findViewById(R.id.lock_overlay_eight);
        buttons[9] = (TextView) findViewById(R.id.lock_overlay_nine);
        buttons[10] = (TextView) findViewById(R.id.lock_overlay_backspace);
        handler = new Handler();
        setPasswordHeadingText();


        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }


    }

    public void slideUpAnimation()
    {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.slide_up);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].startAnimation(animation);
        }

    }

    public void slideDownAnimation()
    {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.slide_down);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].startAnimation(animation);
        }

    }

    private void setPasswordHeadingText() {
        if (isConfirmPasswordMode)
            passwordHeadingText.setText("Set Password");
        else
            passwordHeadingText.setText("Password");
    }

    public void setBackgroundColor() {
        int themeIndex = AppSharedPreferences.getLockThemePreference(getContext());

        if (themeIndex == AppConstants.GALLERY_THEME) {
            File file = new File(AppSharedPreferences.getGalleryImagePreference(getContext()));
            Picasso.with(getContext()).load("file://" + AppSharedPreferences.getGalleryImagePreference(getContext())).fit().centerCrop().into(mainImage);
        } else {
            mainLayout.setBackgroundColor(AppUtils.getColor(getContext(), themeIndex));
            mainImage.setVisibility(GONE);
        }
    }

    public void setImageIcon(String packName) {
        try {
            lockIcon.setImageDrawable(getContext().getPackageManager().getApplicationIcon(packName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        Log.d("Irshad", "View Clicked");
        String text = passwordText.getText().toString();
        Log.d("Irshad", text.length() + " " + text);
        switch (v.getId()) {
            case R.id.lock_overlay_zero:
                passwordText.setText(text + "0");
                checkForPasswordLength(text + "0");
                break;
            case R.id.lock_overlay_one:
                passwordText.setText(text + "1");
                checkForPasswordLength(text + "1");
                break;
            case R.id.lock_overlay_two:
                passwordText.setText(text + "2");
                checkForPasswordLength(text + "2");
                break;
            case R.id.lock_overlay_three:
                passwordText.setText(text + "3");
                checkForPasswordLength(text + "3");
                break;
            case R.id.lock_overlay_four:
                passwordText.setText(text + "4");
                checkForPasswordLength(text + "4");
                break;
            case R.id.lock_overlay_five:
                passwordText.setText(text + "5");
                checkForPasswordLength(text + "5");
                break;
            case R.id.lock_overlay_six:
                passwordText.setText(text + "6");
                checkForPasswordLength(text + "6");
                break;
            case R.id.lock_overlay_seven:
                passwordText.setText(text + "7");
                checkForPasswordLength(text + "7");
                break;
            case R.id.lock_overlay_eight:
                passwordText.setText(text + "8");
                checkForPasswordLength(text + "8");
                break;
            case R.id.lock_overlay_nine:
                passwordText.setText(text + "9");
                checkForPasswordLength(text + "9");
                break;
            case R.id.lock_overlay_backspace:
                if (!text.isEmpty())
                    passwordText.setText(removeLastChar(text));
                break;

        }

    }

    private void checkForPasswordLength(final String string) {
        if (string.length() >= 4) {
            changeButtonsStatus(false);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkPassword(string);

                }
            }, 500);
        }
    }

    private String removeLastChar(String string) {
        return string.substring(0, string.length() - 1);
    }

    private void checkPassword(String text) {
        if (isConfirmPasswordMode) {
            checkForConfirmPasswordMode(text);
        } else {
            checkforEnterPasswordMode(text);
        }
    }

    private void checkForConfirmPasswordMode(String text) {
        if (!confirmPasswordSecondEntry) {
            passwordHeadingText.setText("Confirm Password");
            previousPassword = text;
            passwordText.setText("");
            changeButtonsStatus(true);
            confirmPasswordSecondEntry = true;
            Toast.makeText(getContext(), "" + previousPassword, Toast.LENGTH_SHORT).show();
        } else {
            if (previousPassword.equalsIgnoreCase(text)) {
                AppSharedPreferences.putPasswordSharedPreference(getContext(), text);
                changeButtonsStatus(true);
                
                overlayScreenListener.hideOverlayScreen();

            } else {
                passwordText.setText("");
                changeButtonsStatus(true);
                Toast.makeText(getContext(), "Password does not match. Try Again!!", Toast.LENGTH_SHORT).show();
            }
        }


    }


    private void checkforEnterPasswordMode(String text) {
        if (text.equalsIgnoreCase(AppSharedPreferences.getPasswordPreference(getContext()))) {
            AppCheckerService.isPasswordEntered = true;
            changeButtonsStatus(true);
            overlayScreenListener.hideOverlayScreen();
            passwordText.setText("");
        } else {
            changeButtonsStatus(true);
            passwordText.setText("");
            Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
        }

    }

    private void changeButtonsStatus(boolean status) {
        for (int i = 0; i < buttons.length; i++) {
            if (status)
                buttons[i].setOnClickListener(this);
            else
                buttons[i].setOnClickListener(null);
        }

    }


}
