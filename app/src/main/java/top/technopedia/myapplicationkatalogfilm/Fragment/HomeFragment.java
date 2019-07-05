package top.technopedia.myapplicationkatalogfilm.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.technopedia.myapplicationkatalogfilm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager mViewPager;
    TabLayout mTablayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

        mTablayout = view.findViewById(R.id.tab_layout);
        mTablayout.setupWithViewPager(mViewPager);

        mTablayout.post(new Runnable() {
            @Override
            public void run() {
                mTablayout.setupWithViewPager(mViewPager);
            }
        });

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private class sliderAdapter extends FragmentPagerAdapter {

        String now_playing = getResources().getString(R.string.now_playing);
        String up_coming = getResources().getString(R.string.upcoming);
        final String tabs[] = {now_playing, up_coming};

        sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NowPlayingFragment();
                case 1:
                    return new UpComingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

}
