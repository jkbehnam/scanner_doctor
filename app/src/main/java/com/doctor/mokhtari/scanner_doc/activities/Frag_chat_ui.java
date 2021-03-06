package com.doctor.mokhtari.scanner_doc.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Objects.Request;
import com.doctor.mokhtari.scanner_doc.activities.Objects.chatMessage;
import com.doctor.mokhtari.scanner_doc.activities.base.myFragment;
import com.doctor.mokhtari.scanner_doc.activities.webservice.ConnectToServer;
import com.doctor.mokhtari.scanner_doc.activities.webservice.VolleyCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onurkagan.ksnack_lib.Animations.Slide;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.onurkagan.ksnack_lib.KSnack.KSnackBarEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

import static com.doctor.mokhtari.scanner_doc.activities.Main.user_id;
import static com.doctor.mokhtari.scanner_doc.activities.utils.Utils.getTimeStamp;
import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_END_CHAT;
import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_GET_CHAT;
import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_RESHOT;
import static com.doctor.mokhtari.scanner_doc.activities.webservice.URLs.URL_SEND_CHAT;


public class Frag_chat_ui extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    Request request;
    @BindView(R.id.chat_view)
    ChatView chatView;
    @BindView(R.id.iv_end_chat)
    TextView iv_end_chat;
    @BindView(R.id.tvDetails)
    TextView tvDetails;

    ArrayList<ChatMessage> chat_list;

    // TODO: Rename and change types and number of parameters
    public static Frag_chat_ui newInstance(Request request) {
        Frag_chat_ui fragment = new Frag_chat_ui(request);
        return fragment;
    }

    public Frag_chat_ui(Request request) {
        this.request = request;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_ui, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "ارتباط با " + request.getRequest_patient());
        iv_end_chat.setOnClickListener(this);
        tvDetails.setOnClickListener(this);

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                // perform actual message sending
                if (!request.getRequest_state().equals("endchat")) {
                    sendMessageServer(chatMessage);
                    return true;
                }
                return false;
            }
        });


        showEndedSnackbar(request.getRequest_state());
        getMessageServer();
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
            case R.id.iv_end_chat:
                endChat();
                break;
            case R.id.tvDetails:
                loadFragment(Frag_request_details.newInstance(request));
                break;


        }
    }


    public void sendMessageServer(ChatMessage chatMessage) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("request_key", request.getRequest_id());
        param.put("content", chatMessage.getMessage());
        param.put("sender", "doctor");
        param.put("user_id", request.getRequest_patient());
        param.put("doc_id", user_id);
        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                // reciveRequest(result);
            }
        }, param, URL_SEND_CHAT);
    }

    public void getMessageServer() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("request_key", request.getRequest_id());

        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                reciveRequest(result);
            }
        }, param, URL_GET_CHAT);
    }

    public void reciveRequest(String response) throws JSONException {

        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<chatMessage> requests = new ArrayList<>();
        try {
            final chatMessage[] request = gson.fromJson(obj.getString("chat"), chatMessage[].class);
            requests.addAll(Arrays.asList(request));
        } catch (Exception e) {
            String s = e.getLocalizedMessage();

        }
        addtochat(requests);
    }

    public void addtochat(ArrayList<chatMessage> glist) {
        //extract data from fired event

        for (chatMessage cm : glist
        ) {
            if (cm.getSender().equals("user")) {
                chatView.addMessage(new ChatMessage(cm.getContent(), getTimeStamp(cm.getTime()) * 1000, ChatMessage.Type.RECEIVED, request.getRequest_patient()));
            } else {
                chatView.addMessage(new ChatMessage(cm.getContent(), getTimeStamp(cm.getTime()) * 1000, ChatMessage.Type.SENT, "شما"));
            }
        }
    }

    public void endChat() {
        showLoading_base();
        Map<String, String> param = new HashMap<String, String>();
        param.put("request_key", request.getRequest_id());

        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                hideLoading_base();
             //   reciveRequest(result);
            }
        }, param, URL_END_CHAT);
    }
    public void reshot() {
        showLoading_base();
        Map<String, String> param = new HashMap<String, String>();
        param.put("request_key", request.getRequest_id());

        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                hideLoading_base();
                Toast.makeText(getActivity(), "امکان ارسال تصویر مجدد رای بیمار فعال شد", Toast.LENGTH_SHORT).show();
               // reciveRequest(result);
            }
        }, param, URL_RESHOT);
    }

    public void showEndedSnackbar(String s) {
        if (s.equals("endchat")) {

            KSnack kSnack = new KSnack(getActivity());
            kSnack
                    .setListener(new KSnackBarEventListener() { // listener
                        @Override
                        public void showedSnackBar() {
                            System.out.println("Showed");
                        }

                        @Override
                        public void stoppedSnackBar() {
                            System.out.println("Stopped");
                        }
                    })
                    .setAction("Text", new View.OnClickListener() { // name and clicklistener
                        @Override
                        public void onClick(View v) {
                            System.out.println("Your action !");
                        }
                    })
                    .setMessage("مکالمه به درخواست شما با پایان رسیده است") // message
                    .setTextColor(R.color.white) // message text color
                    .setBackColor(R.color.red_600) // background color
                    .setButtonTextColor(R.color.white) // action button text color
                    .setAnimation(Slide.Up.getAnimation(kSnack.getSnackView()), Slide.Down.getAnimation(kSnack.getSnackView()))
                    .setDuration(4000) // you can use for auto close.
                    .show();
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
