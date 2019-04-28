package com.parkhelp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.parkhelp.Fragments.LoginFragment;
import com.parkhelp.Fragments.RegisterFragment;

public class TabAdapter  extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public TabAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }



    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                RegisterFragment registerFragment = new RegisterFragment();
                return registerFragment;
            default:
                return null; } }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }


}