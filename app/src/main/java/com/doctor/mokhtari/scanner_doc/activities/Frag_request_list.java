package com.doctor.mokhtari.scanner_doc.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Adapters.adapterRcycleMain2;
import com.doctor.mokhtari.scanner_doc.activities.Objects.Request;
import com.doctor.mokhtari.scanner_doc.activities.base.myFragment;
import com.doctor.mokhtari.scanner_doc.activities.helper.PrefManager;
import com.doctor.mokhtari.scanner_doc.activities.webservice.ConnectToServer;
import com.doctor.mokhtari.scanner_doc.activities.webservice.VolleyCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.mokhtari.scanner_doc.activities.Main.user_id;
import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_GET_REQUEST_LIST;


public class Frag_request_list extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    public static ArrayList<Request> requests = new ArrayList<>();

    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    @BindView(R.id.iv_empty_state)
    ImageView iv_empty_state;

    @BindView(R.id.tv_empty_state)
    TextView tv_empty_state;
    @BindView(R.id.reqListSwipeContainer)
    SwipeRefreshLayout reqListSwipeContainer;
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

        // glist.add(new requests("پشت دست", "97/3/2", "دکتر یوسفی", "دریافت پاسخ", "transaction"));

        reqListSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                requests=new ArrayList<>();
                getRequestList();
            }
        });

        if (requests.size() == 0) {
            getRequestList();
        } else {
            settitems(requests);
        }
         View myLayout = rootView.findViewById(R.id.toolbar); // root View id from that link
        ImageView IvChat = (ImageView) myLayout.findViewById(R.id.iv_chat);
        IvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(Frag_chat_lists.newInstance());
            }
        });
        ImageView IvProfile = (ImageView) myLayout.findViewById(R.id.iv_profile);
        IvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, new String[]{ "خروج"}, "");

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
    public void getRequestList() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("user_id", user_id);
        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                if(reqListSwipeContainer.isRefreshing()){reqListSwipeContainer.setRefreshing(false);}

                reciveRequest(result);
            }
        }, param, URL_GET_REQUEST_LIST);
    }

    public void reciveRequest(String response) throws JSONException {

        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);

        try {
            final Request[] request = gson.fromJson(obj.getString("requests"), Request[].class);
            requests.addAll(Arrays.asList(request));
        } catch (Exception e) {
            String s = e.getLocalizedMessage();

        }
        settitems(requests);
    }

    public void settitems(ArrayList<Request> glist) {
        if (glist.size() != 0) {
            tv_empty_state.setVisibility(View.GONE);
            iv_empty_state.setVisibility(View.GONE);
        }
        adapterRcycleMain2 madapter = new adapterRcycleMain2(glist);
        mainActivity_recycle.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapterRcycleMain2.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position, Request req) {
                loadFragment(Frag_request_details.newInstance(req));

            }

        });

    }
    private void showDialog(final View view, CharSequence[] charSequenceArr, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:
                        PrefManager pm = new PrefManager(getActivity());
                        pm.clearSession();
                        getActivity().finish();
                        break;
                }

                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
}
