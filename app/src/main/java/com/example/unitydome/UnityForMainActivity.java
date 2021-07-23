package com.example.unitydome;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.unity3d.player.UnityPlayerActivity;

/**
 * Copyright (C), 2003-2021, 深圳市图派科技有限公司
 * Date: 2021/7/23
 * Description:
 * Author: zl
 */
public class UnityForMainActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_BACK){
            // 添加返回键返回 MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //销毁
            finish();
        }
        return super.onKeyDown(i, keyEvent);
    }

}
