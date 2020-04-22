package com.doctor.mokhtari.scanner_doc.activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Objects.AddImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.patient.mokhtari.scanner.activities.New_request.select_photo.GlideApp;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterShowPhoto extends RecyclerView.Adapter<adapterShowPhoto.MyViewHolder> {
    private List<AddImage> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        @BindView(R.id.cv_add_photo)
        CardView cv_add_photo;
        @BindView(R.id.iv_add_photo)
        ImageView iv_add_photo;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterShowPhoto(ArrayList<AddImage> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_show, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AddImage data_service = data_services_list.get(position);
        if (!data_service.getAddress().equals("")) {
            Glide.with(context)
                    .load(data_service.getAddress())
                    .thumbnail(.005f)
                    .into(holder.iv_add_photo);
         //  holder.iv_add_photo.setImageResource(R.drawable.doc_face);
        }
        // write code
        holder.cv_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClickListner.OnCardClicked(view, position);
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