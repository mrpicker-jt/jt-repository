package com.gproject.android.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gproject.R;
import com.gproject.android.fragment.ModelFragment;
import com.gproject.android.fragment.MoniterFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends BaseActivity implements View.OnClickListener {
    private static final int TAB_MONITER = 0;
    private static final int TAB_MODEL = 1;
    //view
    @BindView(R.id.tab_monitor)
    public TextView moniterBtn;
    @BindView(R.id.tab_modle)
    public TextView modelBtn;
    private MoniterFragment moniterFragment;
    private ModelFragment modelFragemnt;
    private ArrayList<Fragment> fragmentItems;
    //data
    private int tabIndex;
    long firstClickBack;

    //静态启动方法
    public static void Start(Context context) {
        Intent intent = new Intent(context, TabActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        moniterFragment = (MoniterFragment) getSupportFragmentManager().findFragmentById(R.id.tab_frag_moniter);
        modelFragemnt = (ModelFragment) getSupportFragmentManager().findFragmentById(R.id.tab_frag_model);
        moniterBtn.setOnClickListener(this);
        modelBtn.setOnClickListener(this);

        // data
        fragmentItems = new ArrayList<Fragment>();
        firstClickBack=System.currentTimeMillis();

        initTabHost();

        // action
        tabIndex = TAB_MONITER;
        updateTabItemView(tabIndex, true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            long secondClickBack = System.currentTimeMillis();
            if(secondClickBack - firstClickBack >1500){
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstClickBack = secondClickBack;
                return true;
            }else{
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initTabHost() {
        fragmentItems.add(moniterFragment);
        fragmentItems.add(modelFragemnt);
    }

    private void showFragment(int index) {
        if (index == tabIndex) {
            return;
        } else {
            updateTabItemView(tabIndex, false);
            updateTabItemView(index, true);
            tabIndex = index;
        }
    }

    private void updateTabItemView(int index, boolean isSelected) {
        Fragment fragment = fragmentItems.get(index);
        if (isSelected) {
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(fragment).commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_monitor:
                showFragment(TAB_MONITER);
                break;
            case R.id.tab_modle:
                showFragment(TAB_MODEL);
                break;
        }
    }
}
