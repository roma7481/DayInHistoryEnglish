package alisaroma.dayinhistory2;

/**
 * Created by Roma-Alisa on 01-Dec-16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 5 ;

    ArrayList<String> listOfEvents = new ArrayList<String>();
    ArrayList<String> listOfBirths = new ArrayList<String>();
    ArrayList<String> listOfDeaths = new ArrayList<String>();
    ArrayList<String> listOfHolidays = new ArrayList<String>();
    EventFragment eventFragment = new EventFragment();
    BirthsFragment birthsFragment = new BirthsFragment();
    DeathsFragment deathsFragment = new DeathsFragment();
    HolidaysFragment holidaysFragment = new HolidaysFragment();

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View x =  inflater.inflate(R.layout.tablayout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        listOfEvents = getArguments().getStringArrayList("events");
        listOfBirths= getArguments().getStringArrayList("births");
        listOfDeaths= getArguments().getStringArrayList("deaths");
        listOfHolidays= getArguments().getStringArrayList("holidays");

        /**
         *Set an Adapter for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */


        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : {
                    return new InfoFragment();
                }
                case 1 : {
                    Bundle bundleEvent = new Bundle();
                    bundleEvent.putStringArrayList("events",listOfEvents);
                    eventFragment.setArguments(bundleEvent);
                    return eventFragment;
                }
                case 2 : {
                    Bundle bundleBirths = new Bundle();
                    bundleBirths.putStringArrayList("births",listOfBirths);
                    birthsFragment.setArguments(bundleBirths);
                    return birthsFragment;
                }
                case 3 : {
                    Bundle bundleDeaths = new Bundle();
                    bundleDeaths.putStringArrayList("deaths",listOfDeaths);
                    deathsFragment.setArguments(bundleDeaths);
                    return deathsFragment;
                }
                case 4 : {
                    Bundle bundleHolidays = new Bundle();
                    bundleHolidays.putStringArrayList("holidays",listOfHolidays);
                    holidaysFragment.setArguments(bundleHolidays);
                    return holidaysFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Info";
                case 1 :
                    return "Events";
                case 2 :
                    return "Births";
                case 3 :
                    return "Deaths";
                case 4 :
                    return "Holidays";
            }
            return null;
        }
    }
}
