package com.example.sun.drysister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mShowButton;
    private ImageView mShowImg;
    private ArrayList<String> mUrls;
    private PictureLoader mPictureLoader;
    private int mCurretPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPictureLoader = new PictureLoader();
        initData();
        initUi();
    }

    private void initUi() {
        mShowButton = findViewById(R.id.main_meizi_btn);
        mShowImg = findViewById(R.id.main_activity_meizi_im);
        mPictureLoader.load(mShowImg,mUrls.get(0));
        mShowButton.setOnClickListener(this);

    }

    private void initData() {
        mUrls = new ArrayList<>();
        mUrls.add("http://ww4.sinaimg.cn/large/610dc034jw1f6ipaai7wgj20dw0kugp4.jpg");
        mUrls.add("http://ww3.sinaimg.cn/large/610dc034jw1f6gcxc1t7vj20hs0hsgo1.jpg");
        mUrls.add("http://ww4.sinaimg.cn/large/610dc034jw1f6f5ktcyk0j20u011hacg.jpg");
        mUrls.add("http://ww1.sinaimg.cn/large/610dc034jw1f6e1f1qmg3j20u00u0djp.jpg");
        mUrls.add("http://ww3.sinaimg.cn/large/610dc034jw1f6aipo68yvj20qo0qoaee.jpg");
        mUrls.add("http://ww3.sinaimg.cn/large/610dc034jw1f69c9e22xjj20u011hjuu.jpg");
        mUrls.add("http://ww3.sinaimg.cn/large/610dc034jw1f689lmaf7qj20u00u00v7.jpg");
        mUrls.add("http://ww3.sinaimg.cn/large/c85e4a5cjw1f671i8gt1rj20vy0vydsz.jpg");
        mUrls.add("http://ww2.sinaimg.cn/large/610dc034jw1f65f0oqodoj20qo0hntc9.jpg");
        mUrls.add("http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_meizi_btn:
                if (mCurretPos >9){
                    mCurretPos = 0;
                }
                mPictureLoader.load(mShowImg,mUrls.get(mCurretPos));
                mCurretPos++;
                break;
        }
    }
}
