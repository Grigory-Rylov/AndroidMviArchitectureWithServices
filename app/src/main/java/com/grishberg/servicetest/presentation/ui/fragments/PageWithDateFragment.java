package com.grishberg.servicetest.presentation.ui.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.grishberg.mvpstatelibrary.framework.state.MvpState;
import com.grishberg.mvpstatelibrary.framework.ui.BaseMvpFragment;
import com.grishberg.servicetest.R;
import com.grishberg.servicetest.common.di.MyServiceLocator;
import com.grishberg.servicetest.presentation.presenters.PageWithDatePresenter;
import com.grishberg.servicetest.presentation.states.main.PageWithTimePresenterState;
import com.grishberg.servicetest.presentation.states.main.PageWithDateViewState;

/**
 * Created by grishberg on 01.04.17.
 */

public class PageWithDateFragment extends BaseMvpFragment<PageWithDatePresenter> {

    public static final String TIME_PICKER_TAG = "TimePicker";
    private static final String TAG = PageWithDateFragment.class.getSimpleName();

    public static Fragment newInstance() {
        return new PageWithDateFragment();
    }

    @Override
    protected PageWithDatePresenter createPresenter() {
        return new PageWithDatePresenter(MyServiceLocator.provideMainScreenInteractor());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_with_date, container, false);
        View button = view.findViewById(R.id.main_page_select_date_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().updateState(new PageWithTimePresenterState.SelectTimeButtonClicked());
            }
        });
        return view;
    }

    @Override
    public void onModelUpdated(MvpState model) {
        if (model instanceof PageWithDateViewState.ShowDateDialog) {
            showDateDialog();
        }
    }

    private void showDateDialog() {
        TimePickerDialogFragment fragment = (TimePickerDialogFragment) getFragmentManager().findFragmentByTag("TimePicker");
        if (fragment == null) {
            fragment = new TimePickerDialogFragment();
            fragment.show(getFragmentManager(), TIME_PICKER_TAG);
        }
        fragment.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                getPresenter().updateState(new PageWithTimePresenterState.TimeSelected(hourOfDay, minute));
            }
        });
    }
}
