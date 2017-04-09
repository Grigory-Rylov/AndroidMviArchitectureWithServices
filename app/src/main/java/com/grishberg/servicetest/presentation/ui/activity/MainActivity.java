package com.grishberg.servicetest.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.grishberg.mvpstatelibrary.framework.state.MvpState;
import com.grishberg.mvpstatelibrary.framework.ui.BaseMvpActivity;
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
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
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
    public void onModelUpdated(MvpState model) {
        if (model instanceof MainScreenViewState.TimeSelectedResponse) {
            updateTime((MainScreenViewState.TimeSelectedResponse) model);
        }
        Log.d(TAG, "onModelUpdated: " + model);
    }

    private void updateTime(MainScreenViewState.TimeSelectedResponse model) {
        timeText.setText(String.format(getString(R.string.main_screen_time_format),
                model.getHour(), model.getMinutes()
        ));
    }
}
