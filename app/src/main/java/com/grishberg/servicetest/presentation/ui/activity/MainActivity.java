package com.grishberg.servicetest.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mvpstatelib.framework.state.MvpState;
import com.github.mvpstatelib.framework.ui.BaseMvpActivity;
import com.github.mvpstatelib.state.annotations.SubscribeState;
import com.grishberg.servicetest.R;
import com.grishberg.servicetest.common.di.MyServiceLocator;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;
import com.grishberg.servicetest.presentation.presenters.MainScreenPresenter;
import com.grishberg.servicetest.presentation.states.main.MainScreenViewState;
import com.grishberg.servicetest.presentation.ui.adapters.MainScreenPageAdapter;

import java.util.List;

public class MainActivity extends BaseMvpActivity<MainScreenPresenter> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView timeText;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.main_screen_viewpager);
        MainScreenPageAdapter adapter = new MainScreenPageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        View button = findViewById(R.id.main_screen_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondActivity.start(MainActivity.this);
                finish();
            }
        });

        timeText = (TextView) findViewById(R.id.main_screen_time_text);
    }

    @Override
    protected MainScreenPresenter createPresenter() {
        MainScreenInteractor interactor = MyServiceLocator.provideMainScreenInteractor();
        return new MainScreenPresenter(interactor);
    }

    @Override
    public void onStateUpdated(MvpState state) {
        GeneratedMainActivitySubscriber.processState(this, state);
        Log.d(TAG, "onModelUpdated: " + state);
    }

    @SubscribeState
    void updateTime(MainScreenViewState.TimeSelectedResponse state) {
        timeText.setText(String.format(getString(R.string.main_screen_time_format),
                state.getHour(), state.getMinutes()
        ));
    }
}
