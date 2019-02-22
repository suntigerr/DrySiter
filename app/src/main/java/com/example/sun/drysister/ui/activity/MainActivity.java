package com.example.sun.drysister.ui.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sun.drysister.R;
import com.example.sun.drysister.bean.Sister;
import com.example.sun.drysister.imgloader.PictureLoader;
import com.example.sun.drysister.net.SisterApi;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mShowButton;
    private Button mRefreshButton;
    private ImageView mShowImg;

    private ArrayList<Sister> mData;
    private int mCurrentPos = 0;
    private int page = 1;//当前页数
    private PictureLoader mPictureLoader;
    private SisterApi mSisterApi;
    private SisterTask mSisterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPictureLoader = new PictureLoader();
        mSisterApi = new SisterApi();
        initData();
        initUi();
    }

    private void initUi() {
        mShowButton = findViewById(R.id.main_meizi_btn);
        mRefreshButton = findViewById(R.id.main_meizi_ref_btn);
        mShowImg = findViewById(R.id.main_activity_meizi_im);

        mShowButton.setOnClickListener(this);
        mRefreshButton.setOnClickListener(this);

    }

    private void initData() {
        mData = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_meizi_btn:
                if (mData != null && !mData.isEmpty()) {
                    if (mCurrentPos > 9) {
                        mCurrentPos = 0;
                    }
                    mPictureLoader.load(mShowImg, mData.get(mCurrentPos).getUrl());
                    mCurrentPos++;
                }
                break;
            case R.id.main_meizi_ref_btn:
                mSisterTask = new SisterTask();
                mSisterTask.execute();
                mCurrentPos = 0;
                page++;

        }
    }

    private class SisterTask extends AsyncTask<Void, Void, ArrayList<Sister>> {
        public SisterTask() {
        }

        @Override
        protected ArrayList<Sister> doInBackground(Void... voids) {
            return mSisterApi.fetchSister(10, page);
        }

        @Override
        protected void onPostExecute(ArrayList<Sister> sisters) {
            super.onPostExecute(sisters);
            mData.clear();
            mData.addAll(sisters);
            page++;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mSisterTask = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSisterTask.cancel(true);
    }
}
