package com.doctor.mokhtari.scanner_doc.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Adapters.adapterShowPhoto;
import com.doctor.mokhtari.scanner_doc.activities.CustomItems.RtlGridLayoutManager;
import com.doctor.mokhtari.scanner_doc.activities.Objects.AddImage;
import com.doctor.mokhtari.scanner_doc.activities.Objects.BodyPointMain;
import com.doctor.mokhtari.scanner_doc.activities.Objects.Patient;
import com.doctor.mokhtari.scanner_doc.activities.Objects.Request;
import com.doctor.mokhtari.scanner_doc.activities.base.myFragment;
import com.doctor.mokhtari.scanner_doc.activities.webservice.ConnectToServer;
import com.doctor.mokhtari.scanner_doc.activities.webservice.VolleyCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_GET_REQUEST_DETAIL;
import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_RESHOT;
import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_SEND_DIAGNOSIS;
import static com.yalantis.ucrop.UCropFragment.TAG;


public class Frag_request_details extends myFragment implements View.OnClickListener {
    adapterShowPhoto madapter;
    adapterShowPhoto madapter2;
    private OnFragmentInteractionListener mListener;

    ArrayList<AddImage> glist;
    @BindView(R.id.test_img_recycle)
    RecyclerView test_img_recycle;
    @BindView(R.id.scan_img_recycle)
    RecyclerView scan_img_recycle;
    @BindView(R.id.pat_det_tv)
    TextView pat_det_tv;
    @BindView(R.id.tv_body_part)
    TextView tv_body_part;
    @BindView(R.id.ReqPatientName)
    TextView ReqPatientName;
    @BindView(R.id.ReqChat)
    TextView ReqChat;
    @BindView(R.id.ReqProgress)
    TextView ReqProgress;
    @BindView(R.id.ReqQuestions)
    TextView ReqQuestions;
    @BindView(R.id.ReqDate)
    TextView ReqDate;
    @BindView(R.id.ReqDiagnosis)
    EditText ReqDiagnosis;
    @BindView(R.id.ReqTreatment)
    EditText ReqTreatment;
    @BindView(R.id.retryPhotoExam)
    TextView retryPhotoExam;
    @BindView(R.id.retryPhoto)
    TextView retryPhoto;
    int position;
    Request request;
    JSONObject jsonObject;
    ArrayList<AddImage> bodyphotos=new ArrayList<>();
    ArrayList<AddImage> testphotos=new ArrayList<>();

    public static ArrayList<BodyPointMain> reqBodyPoints2 = new ArrayList<>();
    public static Patient patient;

    // TODO: Rename and change types and number of parameters
    public Frag_request_details(Request RequestId) {
        this.request = RequestId;
        getRequestDetail();
    }

    public static Frag_request_details newInstance(Request RequestId) {
        Frag_request_details fragment = new Frag_request_details(RequestId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_details, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "خلاصه درخواست");


        RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(getActivity(), 5);
        RtlGridLayoutManager layoutManager2 = new RtlGridLayoutManager(getActivity(), 5);

        test_img_recycle.setLayoutManager(layoutManager);
        scan_img_recycle.setLayoutManager(layoutManager2);

        pat_det_tv.setOnClickListener(this::onClick);
        tv_body_part.setOnClickListener(this::onClick);
        ReqQuestions.setOnClickListener(this);
        ReqChat.setOnClickListener(this);
        retryPhotoExam.setOnClickListener(this);
        retryPhoto.setOnClickListener(this);

        ReqDiagnosis.setText(request.getDiagnosis());
        ReqTreatment.setText(request.getTreatment());

        View myLayout = rootView.findViewById(R.id.toolbar); // root View id from that link
        ImageView IcSubmit = (ImageView) myLayout.findViewById(R.id.iv_submit);
        IcSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDiagnosis(ReqDiagnosis.getText().toString(), ReqTreatment.getText().toString());
            }
        });
        settitems(bodyphotos,testphotos);
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
            case R.id.pat_det_tv:
                loadFragment(Frag_patient_details.newInstance());
                break;
            case R.id.tv_body_part:
                loadFragment(Frag_Body_part.newInstance());
                break;
            case R.id.ReqQuestions:
                loadFragment(Frag_questions_details.newInstance(jsonObject));
                break;
            case R.id.ReqChat:
                loadFragment(Frag_chat_ui.newInstance(request));
                break;
            case R.id.retryPhoto:
                reshot("body");
                break;
            case R.id.retryPhotoExam:
                reshot("test");
                break;
        }
    }
    public void reshot(String type) {
        showLoading_base();
        Map<String, String> param = new HashMap<String, String>();
        param.put("request_key", request.getRequest_id());
        param.put("type", type);
        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                hideLoading_base();
                Toast.makeText(getActivity(), "امکان ارسال تصویر مجدد برای بیمار فعال شد", Toast.LENGTH_SHORT).show();
                // reciveRequest(result);
            }
        }, param, URL_RESHOT);
    }
    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
        glist.get(position).setAddress(url);
        madapter.notifyDataSetChanged();
        madapter.notifyItemChanged(position);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getRequestDetail() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("request_id", request.getRequest_id());
        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                reciveRequest(result);
            }
        }, param, URL_GET_REQUEST_DETAIL);
    }

    public void reciveRequest(String response) throws JSONException {

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);

        try {
            AddImage[] request = gson.fromJson(obj.getString("bodyphotos"), AddImage[].class);
            bodyphotos.addAll(Arrays.asList(request));
            request = gson.fromJson(obj.getString("testphotos"), AddImage[].class);
            testphotos.addAll(Arrays.asList(request));
            JSONArray ja = obj.getJSONArray("questions");
            if (ja.length() != 0)
                jsonObject = (JSONObject) ja.get(0);
            reqBodyPoints2.clear();
            BodyPointMain[] request2 = gson.fromJson(obj.getString("bodypoints"), BodyPointMain[].class);
            reqBodyPoints2.addAll(Arrays.asList(request2));
            patient = null;
            patient = gson.fromJson(obj.getString("patient"), Patient.class);
            // JSONObject patient= obj.getJSONObject("patient");
        } catch (Exception e) {
        }
        settitems(bodyphotos, testphotos);
    }

    public void settitems(ArrayList<AddImage> bodyphotos, ArrayList<AddImage> testphotos) {


        madapter = new adapterShowPhoto(bodyphotos);
        scan_img_recycle.setAdapter(madapter);
        madapter2 = new adapterShowPhoto(testphotos);
        test_img_recycle.setAdapter(madapter2);

        madapter.setOnCardClickListner(new adapterShowPhoto.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
//                Intent intent = new Intent(getActivity(), imageSampleActivity.class);
//                getActivity().startActivity(intent);
                showphoto shortAnswerAlert = new showphoto();
                shortAnswerAlert.init_dialog(getActivity(), (bodyphotos.get(position).getAddress()));
                shortAnswerAlert.show();
            }
        });
        madapter2.setOnCardClickListner(new adapterShowPhoto.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                // Intent intent = new Intent(getActivity(), imageSampleActivity.class);
                //  getActivity().startActivity(intent);

                showphoto shortAnswerAlert = new showphoto();
                shortAnswerAlert.init_dialog(getActivity(), (testphotos.get(position).getAddress()));
                shortAnswerAlert.show();
            }
        });


    }

    public void sendDiagnosis(String diagnosis, String treatment) {
        showLoading_base();
        Map<String, String> param = new HashMap<String, String>();
        param.put("request_key", request.getRequest_id());
        param.put("diagnosis", diagnosis);
        param.put("treatment", treatment);
        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                hideLoading_base();
                reciveRequest(result);
            }
        }, param, URL_SEND_DIAGNOSIS);
    }
}
