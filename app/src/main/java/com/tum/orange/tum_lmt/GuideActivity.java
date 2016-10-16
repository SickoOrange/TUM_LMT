package com.tum.orange.tum_lmt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private int[] mImages = {R.drawable.guideimage1, R.drawable.guideimage2, R.drawable
            .guideimage3};
    private List<View> mList = new ArrayList<View>();
    private ViewPager vp;
    private ArrayList<View> viewPoints;
    private LinearLayout guide_ll;
    private int lastPosition = 0;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        guide_ll.getChildAt(0).setEnabled(true);
        ImageAdapter adapter = new ImageAdapter(mList, this);
        vp.setAdapter(adapter);

    }

    private void initData() {
        View view;
        viewPoints = new ArrayList<View>();
        View point;
        for (int i = 0; i < mImages.length; i++) {
            view = getImageView(i);
            mList.add(view);
            //加载小白点指示器
            point = new View(this);
            point.setBackgroundResource(R.drawable.indicator_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                params.leftMargin = 10;
            }
            point.setEnabled(false);
            guide_ll.addView(point, params);
        }
    }

    public Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片  
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    private void initView() {
        btn = (Button) findViewById(R.id.finishGuide);
        vp = (ViewPager) findViewById(R.id.guide_ViewPager);
        guide_ll = (LinearLayout) findViewById(R.id.guide_ll);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                guide_ll.getChildAt(position).setEnabled(true);
                guide_ll.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
                System.out.println(lastPosition);
                if (position == mList.size()-1) {
                    System.out.println("VISIBLE");
                    btn.setVisibility(View.VISIBLE);
                }else {
                    btn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View getImageView(int index) {
        View view = getLayoutInflater().inflate(R.layout.guideview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.guide_Image);
        //imageView.setImageResource(mImages[index]);
        //imageView.setBackgroundResource(mImages[index]);
        imageView.setImageBitmap(readBitMap(this, mImages[index]));
        return view;
    }

    class ImageAdapter extends PagerAdapter {
        private List<View> list;

        public ImageAdapter(List<View> list, Context mContext) {
            this.list = list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(list.get(position));
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
