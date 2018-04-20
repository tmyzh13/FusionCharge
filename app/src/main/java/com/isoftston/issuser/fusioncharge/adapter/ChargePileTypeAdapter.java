package com.isoftston.issuser.fusioncharge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.isoftston.issuser.fusioncharge.views.fragments.CommentsFragment;
import com.isoftston.issuser.fusioncharge.views.fragments.PictureFragment;
import com.isoftston.issuser.fusioncharge.views.fragments.PositionFragment;

/**
 * Created by issuser on 2018/4/12.
 */

public class ChargePileTypeAdapter extends FragmentPagerAdapter {

    private PictureFragment pictureFragment;
    private PositionFragment positionFragment;
    private CommentsFragment commentsFragment;

    public ChargePileTypeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            if (pictureFragment == null) {
                pictureFragment = new PictureFragment();
            }
            return pictureFragment;
        } else if (position == 1) {
            if (positionFragment == null) {
                positionFragment = new PositionFragment();
            }
            return positionFragment;
        } else if (position == 2) {
            if (commentsFragment == null) {
                commentsFragment = new CommentsFragment();
            }
            return commentsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
