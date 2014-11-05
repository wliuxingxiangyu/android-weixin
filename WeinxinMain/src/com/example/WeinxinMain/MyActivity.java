package com.example.WeinxinMain;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    private ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mData;
    private TextView chatView,friendView,addressView;
    private LinearLayout chat;

    private ImageView imageView;
    private int width3_1;

    private int mCurrentPagerIndex;




    private BadgeView mBadgeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        initLine();
        initView();
    }

    private void initLine() {
        imageView = (ImageView) findViewById(R.id.imageView);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        width3_1 = displayMetrics.widthPixels/3;
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.width = width3_1;
        imageView.setLayoutParams(lp);

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        chatView = (TextView) findViewById(R.id.chatView);
        friendView = (TextView) findViewById(R.id.friendView);
        addressView = (TextView) findViewById(R.id.addressView);
        chat = (LinearLayout) findViewById(R.id.chat);

        mData = new ArrayList<Fragment>();
        ChatFragment chatFragment = new ChatFragment();
        FriendFragment friendFragment = new FriendFragment();
        AddressListFragment addressListFragment = new AddressListFragment();

        mData.add(chatFragment);
        mData.add(friendFragment);
        mData.add(addressListFragment);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem( int i) {
                return mData.get(i);
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        };

        viewPager.setAdapter(mAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled( int position,  float positionOffset, int positionOffsetPx) {
                Log.e("TAG",position+","+positionOffset+","+positionOffsetPx);
              LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                if(mCurrentPagerIndex==0&& position ==0){
                    lp.leftMargin = (int) (positionOffset*width3_1+mCurrentPagerIndex*width3_1);
                }else if (mCurrentPagerIndex==1&&position==0){
                    lp.leftMargin = (int) (mCurrentPagerIndex*width3_1+(positionOffset-1)*width3_1);

                }else if(mCurrentPagerIndex==1&&position==1){
                    lp.leftMargin = (int) (mCurrentPagerIndex*width3_1+positionOffset*width3_1);

                }else if (mCurrentPagerIndex==2&&position==1){
                    lp.leftMargin = (int) (mCurrentPagerIndex*width3_1+(positionOffset-1)*width3_1);

                }
                imageView.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected( int i) {
                  resetTextView();
                switch (i){
                    case 0:
                        if (mBadgeView!=null){
                            chat.removeView(mBadgeView);
                        }
                        mBadgeView = new BadgeView(MyActivity.this);
                        mBadgeView.setBadgeCount(7);
                        chat.addView(mBadgeView);
                        chatView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        friendView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        addressView.setTextColor(Color.parseColor("#008000"));
                        break;


                }
                mCurrentPagerIndex = i;


            }

            @Override
            public void onPageScrollStateChanged(final int i) {

            }
        });


    }

    private void resetTextView() {
        chatView.setTextColor(Color.BLACK);
        friendView.setTextColor(Color.BLACK);
        addressView.setTextColor(Color.BLACK);
    }
}
