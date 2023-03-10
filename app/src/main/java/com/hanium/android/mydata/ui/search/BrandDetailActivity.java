package com.hanium.android.mydata.ui.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hanium.android.mydata.R;
import com.hanium.android.mydata.SharedPreference;
import com.hanium.android.mydata.ui.user.LoginActivity;

import org.json.JSONObject;

public class BrandDetailActivity extends AppCompatActivity {

    final static String TAG = "BrandDetailActivity";

//    String myJSON;
//    JSONArray bScraps = null;

//    private static final String TAG_RESULTS = "result";
//    private static final String TAG_USERID = "userID";
//    private static final String TAG_BRANDID = "brandID";
//    private static final String TAG_BRANDSCRAP = "bScrap";

    private TextView content, category1, category2, benefit, extraInfo;
    private ImageView favBrand;

    private String userID, brandID, brandScrap;
    private boolean isLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.brand_detail_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userID = SharedPreference.getUserID(BrandDetailActivity.this);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        brandID = intent.getStringExtra("brandID");
        String bName = intent.getStringExtra("bName");
        String bCategory1 = intent.getStringExtra("bCategory1");
        String bCategory2 = intent.getStringExtra("bCategory2");
        String bBestProd = intent.getStringExtra("bBestProd");
        String bExtraInfo = intent.getStringExtra("bExtraInfo");

        String discntType = intent.getStringExtra("discntType");
        String discntRate = intent.getStringExtra("discntRate");
        String discntInfo = intent.getStringExtra("discntInfo");

        content = findViewById(R.id.brand_content);
        category1 = findViewById(R.id.brand_categoty1);
        category2 = findViewById(R.id.brand_categoty2);
        benefit = findViewById(R.id.brand_benefit);
        extraInfo = findViewById(R.id.brand_extraInfo);

        favBrand = findViewById(R.id.brand_like);

        content.setText(bName);
        category1.setText(bCategory1);
        category2.setText(bCategory2);
        benefit.setText(discntRate+ " " +discntType);
        extraInfo.setText(bExtraInfo);


        getScrap(); // ????????? ?????? ????????????


        favBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userID.length() == 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BrandDetailActivity.this);
                    builder.setMessage("???????????? ????????? ??????????????????.\n????????? ???????????? ?????????????????????????")
                            .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent toLoginIntent = new Intent(BrandDetailActivity.this, LoginActivity.class);
                                    startActivity(toLoginIntent);
                                    return;
                                }
                            })
                            .setNegativeButton("??????", null)
                            .show();
                } else {

                    if (isLike == false) {
                        brandScrap = "Y";
                    } else {
                        brandScrap = "N";
                    }

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
//                            String bScrap = jsonObject.getString("bScrap");

                                if (success) {
                                    if (brandScrap.equals("N")) {
                                        favBrand.setImageResource(R.drawable.unlike_icon);
                                        isLike = false;
//                                        Toast.makeText(BrandDetailActivity.this, "????????? ??????", Toast.LENGTH_SHORT).show();
                                    } else if (brandScrap.equals("Y")) {
                                        favBrand.setImageResource(R.drawable.like_icon);
                                        isLike = true;
//                                        Toast.makeText(BrandDetailActivity.this, "?????????", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(BrandDetailActivity.this, "????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            } catch (Exception e) {
                                Toast.makeText(BrandDetailActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                Log.d(TAG, e.getMessage());
                            }
                        }
                    };

                    UpdateBrandScrapRequest updateRequestBrandScrapRequest = new UpdateBrandScrapRequest(userID, brandID, brandScrap, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BrandDetailActivity.this);
                    queue.add(updateRequestBrandScrapRequest);
                }
            }
        });

    }

    public void getScrap() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if(success) {
                        String bScrap = jsonObject.getString("bScrap");

                        Log.d(TAG, "????????? ??????: " +bScrap);

                        if (bScrap.equals("Y")) {
                            favBrand.setImageResource(R.drawable.like_icon);
                            isLike = true;
                        } else if (bScrap.equals("N")) {
                            favBrand.setImageResource(R.drawable.unlike_icon);
                            isLike = false;
                        } else { // bScrap == null
                            favBrand.setImageResource(R.drawable.unlike_icon);
                            isLike = false;
                        }

                    } else {
//                        Toast.makeText(SearchDetailActivity.this, "????????? ????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "????????? ???????????? ??????");

                        setScrap(); // brandScrap ???????????? ????????? ??????
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
            }
        };

        GetBrandScrapRequest brandScrapRequest = new GetBrandScrapRequest(userID, brandID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BrandDetailActivity.this);
        queue.add(brandScrapRequest);
    }

    public void setScrap() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Log.d(TAG, "????????? db ??????");

                    } else {
                        Log.d(TAG, "????????? db ?????? ??????");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "error");
                    Log.d(TAG, e.getMessage());
                }

            }
        };

        SetBrandScrapRequest setBrandScrapRequest = new SetBrandScrapRequest(userID, brandID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BrandDetailActivity.this);
        queue.add(setBrandScrapRequest);
    }

}
