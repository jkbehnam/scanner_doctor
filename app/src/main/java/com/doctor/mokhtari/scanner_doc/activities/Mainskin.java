package com.doctor.mokhtari.scanner_doc.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.Adapters.adapterRcycleMain;
import com.doctor.mokhtari.scanner_doc.activities.Objects.MainList;
import com.doctor.mokhtari.scanner_doc.activities.questioner.question;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mainskin extends BaseActivity {
    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_request);
        ButterKnife.bind(this);



        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<MainList> glist = new ArrayList<>();
        glist.add(new MainList("پاسخ به سوالات", "transaction", true));
        glist.add(new MainList("ارسال تصویر آزمایش", "solved", false));
        glist.add(new MainList("ارسال تصویر ضایعه", "wallet", false));

        adapterRcycleMain madapter = new adapterRcycleMain(glist);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterRcycleMain.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                switch (position) {
                    case 0:
                        Intent i=new Intent(Mainskin.this, question.class);
                        startActivity(i);
                     //   loadFragment(new BuyQuestionFragment());
                        break;
                    case 1:
                     //   loadFragment(new Fragment_myQuestion_list());
                        // Intent i=new Intent(getActivity(), q_lists.class);
                        // startActivity(i);
                        break;
                    case 2:
                      //  loadFragment(new solvingListFragment());
                        break;
                    case 3:
                      //  loadFragment(new Fragment_ranking_list());
                        break;
                    case 4:
                       // loadFragment(new Fragment_source_list());
                        break;
                }
            }
        });
    }

}
