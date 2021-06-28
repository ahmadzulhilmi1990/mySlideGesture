package com.zulhilmizamri.cdx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class VerifyMoneyActivity extends AppCompatActivity {
    private static final String TAG = "VerifyMoneyActivity";

    private SlidingUpPanelLayout mLayout;
    private LinearLayout layout_eclipse,dragView,layout_profile,view_layout_profile;
    int margin_bottom = 0;
    private Activity activity = this;
    private Context context = this;
    private TextView txt_note,txt_add_note;
    private Button btn_amount;
    private LinearLayout icon_close,icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_money);
        headerBackground();

        icon_close = (LinearLayout)findViewById(R.id.icon_close);
        icon_back = (LinearLayout)findViewById(R.id.icon_back);
        btn_amount = (Button)findViewById(R.id.btn_amount);
        txt_note = (TextView) findViewById(R.id.txt_note);
        txt_add_note = (TextView)findViewById(R.id.txt_add_note);

          //onclick listener
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        icon_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenheight = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int halfheight = screenheight/2;
        int est300 = halfheight - 300;

        Log.d(TAG,"Screen H = " + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenheight, getResources().getDisplayMetrics()));
        Log.d(TAG,"halfheight H = " + halfheight);
        Log.d(TAG,"est300 H = " + est300);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        view_layout_profile = (LinearLayout)findViewById(R.id.view_layout_profile);
        layout_eclipse = (LinearLayout) findViewById(R.id.layout_eclipse);
        dragView = (LinearLayout) findViewById(R.id.dragView);
        layout_profile = (LinearLayout) findViewById(R.id.layout_profile);
        //layout_eclipse.setMinimumHeight(est300);

        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                float getY;
                float dragdown;

                getY = 100 + (slideOffset*100);
                dragdown = 90 + (slideOffset*100);

                margin_bottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getY, getResources().getDisplayMetrics());
                int heightdragdown = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dragdown, getResources().getDisplayMetrics());

                //layout_eclipse.getLayoutParams().height = margin_bottom;
                Log.d(TAG,"layout_eclipse Height = " + layout_eclipse.getHeight());
                Log.d(TAG,"getY = " + getY);

                if(layout_eclipse.getHeight()<getY){
                    getY = 100 - (slideOffset*100);
                    dragdown = 90 - (slideOffset*100);
                    heightdragdown = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dragdown, getResources().getDisplayMetrics());
                    margin_bottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getY, getResources().getDisplayMetrics());
                }
                Log.d(TAG,"margin_bottom = " + margin_bottom);
                Log.d(TAG,"heightdragdown = " + heightdragdown);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        /*width*/ ViewGroup.LayoutParams.FILL_PARENT,
                        /*height*/ margin_bottom
                        /*weight*/ //1.0f
                );
                layout_eclipse.setLayoutParams(param);

                if(getY > 200){
                    dragView.setPadding(0, 0, 0, margin_bottom);
                    //dragView.setPadding(0, 0, 0, margin_bottom);
                }
                if(getY > 0) {
                    Log.d(TAG,"Margin-->" + ((int)dragdown - 30));
                    layout_profile.setPadding(0,((int)dragdown - 30),0,0);
                    //layout_profile.setPadding(0,((int)dragdown /2),0,0);
                }else{
                    layout_profile.setPadding(0,0,0,0);
                }

            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState + " | margin_bottom = " + margin_bottom);
                if(newState == PanelState.EXPANDED) {
                    Intent to = new Intent(context,CompleteActivity.class);
                    startActivity(to);
                    finish();
                }
            }
        });

        mLayout.setFadeOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });
    }


    /*@Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void headerBackground(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity,R.color.white));
        window.setNavigationBarColor(ContextCompat.getColor(activity,R.color.white));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
