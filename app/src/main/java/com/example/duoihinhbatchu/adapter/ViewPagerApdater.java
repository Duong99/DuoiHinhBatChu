package com.example.duoihinhbatchu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.model.History;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ViewPagerApdater extends PagerAdapter {
    private ArrayList<History> histories;
    private Context context;

    public ViewPagerApdater(ArrayList<History> histories, Context context) {
        this.histories = histories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_image_viewpager, null);

        ImageView imvImagePager = view.findViewById(R.id.imvImagePager);

        ByteArrayInputStream stream = new ByteArrayInputStream(histories.get(position).getImage());
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        imvImagePager.setImageBitmap(bitmap);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
