package com.grishberg.servicetest.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mvpstatelib.framework.state.MvpState;
import com.github.mvpstatelib.framework.ui.BaseMvpFragment;
import com.grishberg.servicetest.R;
import com.grishberg.servicetest.common.di.MyServiceLocator;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;
import com.grishberg.servicetest.presentation.presenters.PagePresenter;

/**
 * Created by grishberg on 01.04.17.
 */

public class PagerFragment extends BaseMvpFragment<PagePresenter> {

    private static final String ARG_POSITION = "ARG_POSITION";
    private int position;

    public static Fragment newInstance(int position) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        TextView textView = (TextView) view.findViewById(R.id.page_name);
        textView.setText(String.valueOf(position));

        return view;
    }

    @Override
    protected PagePresenter createPresenter() {
        MainScreenInteractor interactor = MyServiceLocator.provideMainScreenInteractor();
        return new PagePresenter(interactor);
    }

    @Override
    public void onStateUpdated(MvpState model) {

    }
}
