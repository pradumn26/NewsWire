package com.developinggeek.thebetterlawyernewsapp.Activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import com.developinggeek.thebetterlawyernewsapp.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroSlider extends AppIntro{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("LATEST NEWS"," Provides the top Indian News headlines. Read breaking news for today, latest news headlines from India in business, markets, sports, politics and lot more", R.drawable.latest, Color.parseColor("#0d2561")));

        addSlide(AppIntroFragment.newInstance("NOTIFICATION","Latest Government notification News, Photos, Blogposts, Videos and Wallpapers  ", R.drawable.notification, Color.parseColor("#303F9F")));

        setFadeAnimation();



    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }
}
