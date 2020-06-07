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
import com.doctor.mokhtari.scanner_doc.activities.Objects.ReqQuestions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.mokhtari.scanner_doc.activities.utils.Utils.getRequestQues;
import static com.doctor.mokhtari.scanner_doc.activities.Frag_request_details.patient;

/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterQuestionDetails extends RecyclerView.Adapter<adapterQuestionDetails.MyViewHolder> {
    private List<ReqQuestions> data_services_list;
    public  static String[] ql={"آیا محل ضایعه بیمار همراه با درد است؟ ",
            "آیا محل ضایعه بیمار همراه با خارش است؟ ",
            "آیا بیمار تب دارد؟ ",
            "آیا بیمار در حال حاضر داروی خاصی مصرف می کند؟ در صورت استفاده از دارو نام آن را وارد کنید.",
            "آیا بیماری خاصی مانند( ایدز- هپاتیت - دیابت - سل) دارید. در صورت داشتن بیماری، نام بیماری خود را وارد کنید ",
            "آیا بیمار حامله است؟ ",
            "آیا در خانواده بیمار کسی شرایط مشابه بیمار را داشته است؟",
            "اولین منطقه ای که ضایعه ظهور پیدا کرد کدام قسمت بدن بود؟ ",
            "آیا ضایعه قابل لمس (برجستگی، خشکی یا زبری) است؟ ",
            "در این قسمت اگر تاریچه پزشکی خاصی (دوره درمانی قبلی، بیماری¬های مشابه) برای بیمار وجود دارد ذکر کنید. "
    };
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


    public adapterQuestionDetails(ArrayList<ReqQuestions> data_services_list) {
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
        final ReqQuestions data_service = data_services_list.get(position);

        if (!(position == 5 && patient.getGender().equals("مرد"))) {
            holder.item_name.setText(ql[position]);
            holder.item_datail.setText(getRequestQues(data_service.getYNQ()) + "\n" + data_service.getDesc());
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        } else {
            holder.item_name.setText("");
            holder.item_datail.setText("");
        }

        if(position==7){
            holder.item_datail.setText(data_service.getDesc());

        }
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