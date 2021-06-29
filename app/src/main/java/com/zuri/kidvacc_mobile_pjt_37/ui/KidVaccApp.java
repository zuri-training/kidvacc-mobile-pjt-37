package com.zuri.kidvacc_mobile_pjt_37.ui;

import android.app.Application;
import android.content.Intent;

import com.zuri.kidvacc_mobile_pjt_37.ui.onboarding_page.OnBoardingActivity;

public class KidVaccApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
