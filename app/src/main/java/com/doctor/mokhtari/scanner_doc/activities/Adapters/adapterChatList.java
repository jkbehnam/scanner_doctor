package com.doctor.mokhtari.scanner_doc.activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Objects.Request;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.mokhtari.scanner_doc.activities.utils.Utils.getPersianDate;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterChatList extends RecyclerView.Adapter<adapterChatList.MyViewHolder> {
    private List<Request> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        @BindView(R.id.cv_request)
        CardView cv_request;
        @BindView(R.id.tv_reqiest_bodypart)
        TextView tv_reqiest_bodypart;
        @BindView(R.id.tv_request_date)
        TextView tv_request_date;
        @BindView(R.id.tv_request_doctor)
        TextView tv_request_doctor;
        @BindView(R.id.tv_request_state)
        TextView tv_request_state;
        @BindView(R.id.iv_requet)
        ImageView iv_requet;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterChatList(ArrayList<Request> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_list, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Request data_service = data_services_list.get(position);


        holder.tv_reqiest_bodypart.setText(data_service.getRequest_patient());
        holder.tv_request_doctor.setText("");
        // holder.tv_request_state.setText(data_service.getRequest_state());
        holder.tv_request_state.setVisibility(View.GONE);
        holder.tv_request_date.setText(getPersianDate(data_service.getRequest_date()));


        Typeface typeface3 = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
        holder.tv_request_state.setTypeface(typeface3, Typeface.BOLD);

        Glide.with(context).load(data_service.getRequest_img()).into(holder.iv_requet);
        holder.cv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data_services_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
}