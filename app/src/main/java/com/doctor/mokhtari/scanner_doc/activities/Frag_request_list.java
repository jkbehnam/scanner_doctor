package com.doctor.mokhtari.scanner_doc.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Adapters.adapterRcycleMain2;
import com.doctor.mokhtari.scanner_doc.activities.CustomItems.myFragment;
import com.doctor.mokhtari.scanner_doc.activities.Objects.requests;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_request_list extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    @BindView(R.id.iv_empty_state)
    ImageView iv_empty_state;

    @BindView(R.id.tv_empty_state)
    TextView tv_empty_state;

    // TODO: Rename and change types and number of parameters
    public static Frag_request_list newInstance() {
        Frag_request_list fragment = new Frag_request_list();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_list, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentActivity(getActivity());
        setToolbar(rootView, "لیست درخواست ها");
        //  ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        //   ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<requests> glist = new ArrayList<>();
        glist.add(new requests("پشت دست", "97/3/2", "حسن حسن زاده", "تشخیص ارسال شده", "hand"));
        glist.add(new requests("پشت شانه", "97/3/3", "حمید میرزایی", "درخواست جدید", "sholder"));
        glist.add(new requests("پشت شانه", "97/3/3", "کامران جعفری", "بسته شد", "sholder"));

        // glist.add(new requests("پشت دست", "97/3/2", "دکتر یوسفی", "دریافت پاسخ", "transaction"));

        settitems(glist);

        View myLayout = rootView.findViewById(R.id.toolbar); // root View id from that link
        ImageView myView = (ImageView) myLayout.findViewById(R.id.iv_chat);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(Frag_chat_lists.newInstance());
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void settitems(ArrayList<requests> glist) {
        if (glist.size() != 0) {
            tv_empty_state.setVisibility(View.GONE);
            iv_empty_state.setVisibility(View.GONE);
        }
        adapterRcycleMain2 madapter = new adapterRcycleMain2(glist);
        mainActivity_recycle.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapterRcycleMain2.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

loadFragment(Frag_request_details.newInstance());
                // Intent i=new Intent(Mainskin.this, question.class);
                //startActivity(i);
                //   loadFragment(new BuyQuestionFragment());


            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  if (context instanceof OnFragmentInteractionListener) {
        //      mListener = (OnFragmentInteractionListener) context;
        //   } else {
        //     throw new RuntimeException(context.toString()
        //              + " must implement OnFragmentInteractionListener");
        //   }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acount_btn:

                break;
            case R.id.btnNewRequest:

                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
