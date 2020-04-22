package com.doctor.mokhtari.scanner_doc.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.BodyPart.region.RegionView;
import com.doctor.mokhtari.scanner_doc.activities.BodyPart.view.HumanBodyWidget;
import com.doctor.mokhtari.scanner_doc.activities.BodyPart.view.WaveEffectLayout;
import com.doctor.mokhtari.scanner_doc.activities.base.myFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_Body_part extends myFragment {

    private OnFragmentInteractionListener mListener;
    View rootView;
    @BindView(R.id.t2)
    SegmentTabLayout segmentTabLayout;

    public static FragmentActivity fragment_body_part;
    private WaveEffectLayout container;
    private HumanBodyWidget bodyWidget;
    private ImageView manIv, womanIv;
    private TextView manTv, womanTv, flipFrontTv, flipBackTv;
    private String[] mTitles = {"جلو","پشت" };
    private ArrayList<MyTouchListener> mTouchListeners = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static Frag_Body_part newInstance() {
        Frag_Body_part fragment = new Frag_Body_part();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.activity_bodypart, container, false);

        super.onCreate(savedInstanceState);
        setFragmentActivity(getActivity());
        fragment_body_part=getActivity();
       getActivity(). setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);



        initViews(savedInstanceState);


        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    //do something
                    for(MyTouchListener listener : mTouchListeners){
                        listener.onTouchEvent(event);
                    }
                }
                return true;
            }
        });



        return rootView;
    }
    public void initViews(Bundle savedInstanceState){
        ButterKnife.bind(this, rootView);
        container = (WaveEffectLayout) rootView.findViewById(R.id.container);
        manIv = (ImageView) rootView.findViewById(R.id.man_icon);
        manTv = (TextView) rootView.findViewById(R.id.man_text);
        womanIv = (ImageView)rootView. findViewById(R.id.woman_icon);
        womanTv = (TextView) rootView.findViewById(R.id.woman_text);
        flipFrontTv = (TextView)rootView.findViewById(R.id.flipFront);
        flipBackTv = (TextView)rootView. findViewById(R.id.flipBack);
        segmentTabLayout.setTabData(mTitles);
        bodyWidget = new HumanBodyWidget(getActivity(), container, savedInstanceState);
        container.setRegionView(new RegionView(container, getActivity()));

        Toolbar toolbar;
        toolbar=(Toolbar)rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)rootView.findViewById(R.id.txttoolbar);
        txttoolbar.setText("محل ضایعه");
        Typeface typeface3 = Typeface.createFromAsset(getActivity().getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);
        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void registerTouchListener(MyTouchListener listener){
        mTouchListeners.add(listener);
    }

    public void unRegisterTouchListener(MyTouchListener listener){
        mTouchListeners.remove(listener);
    }


    public void genderClick(View view){
        switch (view.getId()){
            case R.id.man_btn:
                if(bodyWidget.toggleBodyGenderImage(true)) {
                    rootView. findViewById(R.id.man_btn).setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    rootView. findViewById(R.id.woman_btn).setBackgroundColor(Color.TRANSPARENT);
                    manIv.setImageResource(R.mipmap.icon_man_pressed);
                    manTv.setTextColor(Color.WHITE);
                    womanIv.setImageResource(R.mipmap.icon_woman);
                    womanTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                }
                break;
            case R.id.woman_btn:
                if(bodyWidget.toggleBodyGenderImage(false)) {
                    rootView.findViewById(R.id.man_btn).setBackgroundColor(Color.TRANSPARENT);
                    rootView.findViewById(R.id.woman_btn).setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    manIv.setImageResource(R.mipmap.icon_man);
                    manTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                    womanIv.setImageResource(R.mipmap.icon_woman_pressed);
                    womanTv.setTextColor(Color.WHITE);
                }
                break;
        }
    }

    public void sideClick(View view){

        switch (view.getId()){
            case R.id.flipFront:
                if(bodyWidget.flipBody(false)) {
                    flipFrontTv.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    flipBackTv.setBackgroundColor(Color.TRANSPARENT);
                    flipFrontTv.setTextColor(Color.WHITE);
                    flipBackTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                }
                break;
            case R.id.flipBack:
                if(bodyWidget.flipBody(true)) {
                    flipFrontTv.setBackgroundColor(Color.TRANSPARENT);
                    flipBackTv.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    flipFrontTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                    flipBackTv.setTextColor(Color.WHITE);
                }
                break;
        }
    }

    public interface MyTouchListener
    {
        void onTouchEvent(MotionEvent event);
    }
}
