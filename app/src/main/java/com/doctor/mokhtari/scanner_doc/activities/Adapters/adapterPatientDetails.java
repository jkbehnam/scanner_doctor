package com.doctor.mokhtari.scanner_doc.activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Objects.Patient_detail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterPatientDetails extends RecyclerView.Adapter<adapterPatientDetails.MyViewHolder> {
    private List<Patient_detail> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;


        @BindView(R.id.item_name)
        TextView item_name;
@BindView(R.id.item_datail)
TextView item_datail;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
           // Typeface typeface3 = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
           // tv.setTypeface(typeface3, Typeface.BOLD);
            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterPatientDetails(ArrayList<Patient_detail> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient_detail, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Patient_detail data_service = data_services_list.get(position);

        holder.item_name.setText(data_service.getTitle());
        holder.item_datail.setText(data_service.getContent());

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

       // holder.iv.setImageResource(R.drawable.transaction);
     //   Glide.with(context).load(getImage(data_service.getImg())).into(holder.iv);


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