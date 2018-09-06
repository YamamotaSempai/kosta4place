package kost4place.aa.kz.kosta4place.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import kost4place.aa.kz.kosta4place.R;
import kost4place.aa.kz.kosta4place.adapter.ViewPagerNewsAdapter;

public class MainActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private ViewPagerNewsAdapter viewPagerNewsAdapter;
    private LinearLayout sliderDots;
    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderDots = findViewById(R.id.sliderDots); //заменить
        mPager = findViewById(R.id.viewPager);

        viewPagerNewsAdapter = new ViewPagerNewsAdapter(this);
        mPager.setAdapter(viewPagerNewsAdapter);

        dotsCount = viewPagerNewsAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dots.length; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTaskViewPager(), 2000, 4000);
    }

    public class TimerTaskViewPager extends TimerTask {
//todo ОБНОВА ПИЗЛДУЦ
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mPager.getCurrentItem() == 0) {
                        mPager.setCurrentItem(1);
                    }else if (mPager.getCurrentItem() == 1) {
                        mPager.setCurrentItem(2);
                    }else if (mPager.getCurrentItem() == 2) {
                        mPager.setCurrentItem(3);
                    }

                }
            });
        }
    }
}



//for (int i = 0; i < 3; i++) {
//        switch (i) {
//        case 0:
//        mPager.setCurrentItem(i);
//        break;
//        case 1:
//        mPager.setCurrentItem(i);
//        break;
//        case 2:
//        mPager.setCurrentItem(i);
//        break;
//        case 3:
//        mPager.setCurrentItem(i);
//        i = 0;
//        break;
//        }
//        }