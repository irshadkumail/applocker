package com.androidclarified.applocker.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.preference.PreferenceManager;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
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
    private FrameLayout buttonFrame;
    private TextView passwordHeadingText;
    private boolean isPasswordEntered;
    private View rootView;
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

    private void inflateTheme() {
        rootView=layoutInflater.inflate(R.layout.lock_overlay_normal, this, true);


    }

    public void setOverlayScreenListener(OverlayScreenListener overlayScreenListener) {
        this.overlayScreenListener = overlayScreenListener;
    }

    public void initButtons() {
        buttons = new TextView[11];

        passwordHeadingText = (TextView) findViewById(R.id.lock_overlay_password_heading_text);

        mainLayout = (RelativeLayout)rootView.findViewById(R.id.lock_overlay_main_layout);
        mainImage = (ImageView)rootView. findViewById(R.id.main_image);
        buttonFrame = (FrameLayout)rootView. findViewById(R.id.lock_button_table);
        passwordText = (TextView)rootView.  findViewById(R.id.locker_overlay_text);
        lockIcon = (ImageView) rootView. findViewById(R.id.lock_overlay_normal_icon);
        buttons[0] = (TextView) rootView. findViewById(R.id.lock_overlay_zero);
        buttons[1] = (TextView) rootView. findViewById(R.id.lock_overlay_one);
        buttons[2] = (TextView) rootView. findViewById(R.id.lock_overlay_two);
        buttons[3] = (TextView) rootView. findViewById(R.id.lock_overlay_three);
        buttons[4] = (TextView) rootView. findViewById(R.id.lock_overlay_four);
        buttons[5] = (TextView) rootView. findViewById(R.id.lock_overlay_five);
        buttons[6] = (TextView) rootView. findViewById(R.id.lock_overlay_six);
        buttons[7] = (TextView) rootView. findViewById(R.id.lock_overlay_seven);
        buttons[8] = (TextView) rootView. findViewById(R.id.lock_overlay_eight);
        buttons[9] = (TextView) rootView. findViewById(R.id.lock_overlay_nine);
        buttons[10] = (TextView) rootView. findViewById(R.id.lock_overlay_backspace);
        handler = new Handler();
        setPasswordHeadingText();

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());

        if (sharedPreferences.getBoolean("show_password_pref",false))
            passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);


        int themeIndex = AppSharedPreferences.getLockThemePreference(getContext());
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);

        }


    }

    private Animation getSlideUpAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);

        return animation;


    }

    private Animation getSlideDownAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        animation.setFillAfter(false);

        return animation;
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
            Picasso.with(getContext()).load("file://" + AppSharedPreferences.getGalleryImagePreference(getContext())).fit().centerCrop().into(mainImage);
            buttonFrame.setBackgroundColor(getResources().getColor(R.color.frame_bg));
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
    public void startAnim()
    {
        rootView.startAnimation(getSlideUpAnimation());
    }
    public void stopAnim()
    {
        rootView.startAnimation(getSlideDownAnimation());
    }

    private void checkForConfirmPasswordMode(String text) {
        if (!confirmPasswordSecondEntry) {
            rootView.startAnimation(getSlideDownAnimation());
            previousPassword = text;
            passwordText.setText("");
            changeButtonsStatus(true);
            confirmPasswordSecondEntry = true;
            passwordHeadingText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    passwordHeadingText.setText("Confirm Password");
                }
            },500);
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
            startWrongPasswordAnim();
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

    private void startWrongPasswordAnim() {
        Animation shakeAnim = AnimationUtils.loadAnimation(getContext(), R.anim.my_shake);
        lockIcon.startAnimation(shakeAnim);
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(800);
    }


}
