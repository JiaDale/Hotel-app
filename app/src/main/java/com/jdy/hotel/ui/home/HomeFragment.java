package com.jdy.hotel.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.jdy.hotel.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private AppBarLayout mAppBarLayout;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mAppBarLayout = root.findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, i) -> {
            View card = root.findViewById(R.id.search_card_view);
            View image = root.findViewById(R.id.home_app_frame_image);
            if (null == card || image == null) {
                return;
            }
            double tempFlag = Math.log(appBarLayout.getHeight() - card.getHeight()) - 1;
            double x = Math.log(Math.abs(i));
            float tempAlpha = (float) (1 - x / tempFlag);
            image.setAlpha(tempAlpha);
            if (tempAlpha > 0) return;

            tempFlag = Math.log(card.getHeight());
            x = Math.log(i + image.getHeight() + card.getHeight()) / Math.log(1.75);
            tempAlpha = (float) (x - tempFlag - 5);
            card.setAlpha(tempAlpha);
            if (tempAlpha > 0) {
                showToolbar(root);
            } else {
                hiddenToolbar(root);
            }

//            card = root.findViewById(R.id.home_app_collapsing_toolbar);
//
//            card.setVisibility(View.VISIBLE);
        });


//        View toolbar = root.findViewById(R.id.home_app_collapsing_toolbar);
//        toolbar.setVisibility(View.VISIBLE);


//        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel.class);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }com.google.android.material.appbar.CollapsingToolbarLayout
//        });
        return root;
    }

    private void hiddenToolbar(View root) {
//        FragmentActivity activity = getActivity();
//        if (activity == null){
//            return;
//        }
//        ActionBar actionBar = getActivity().getActionBar();
//        if (actionBar == null) return;
//
//        if (actionBar.getDisplayOptions() ==ActionBar.)

    }

    private void showToolbar(View root) {

    }


    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
    }
}