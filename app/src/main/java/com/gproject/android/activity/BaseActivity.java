package com.gproject.android.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gproject.R;
import com.gproject.android.manager.DormManager;
import com.gproject.android.manager.EventManager;
import com.gproject.android.manager.ImageManager;
import com.gproject.android.manager.NetworkManager;

/**
 * Created by 姜腾 on 2017/2/7.
 */

public class BaseActivity extends FragmentActivity {

    protected NetworkManager networkManager;
    protected ActivityState activityState;

    protected boolean shouldRefreshView;

    protected DormManager dormManager;

    private View loadingView;

    public void onEvent(String event) {
        Log.d("Event", "default handler");
    }


    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public DormManager getDormManager() {
        return dormManager;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        networkManager = new NetworkManager(this);
        dormManager = DormManager.GetInstance(this);
        //
        ImageManager.GetInstance(getApplicationContext());
        EventManager.GetInstance().getEventBus().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity state
        activityState = ActivityState.RUN;

        // refresh view setting
        if (shouldRefreshView) {
            shouldRefreshView = false;
            refreshView();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ActivityState.STOP;
    }

    @Override
    protected void onDestroy() {
//        hideLoading();
        super.onDestroy();
        networkManager.cancelAllRequest();
        EventManager.GetInstance().getEventBus().unregister(this);
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(findViewById(R.id.root), InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(findViewById(R.id.root).getWindowToken(), 0); //强制隐藏键盘
    }

    public void setTitle(String title) {

        TextView textView = (TextView) findViewById(R.id.header_title);
        if (title != null) {
            textView.setText(title);
        } else {
            textView.setText("");
        }
    }

    public View addBackBtn() {
        View back = findViewById(R.id.header_left_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back.setVisibility(View.VISIBLE);
        return back;
    }

    public View addCloseBtn() {
        View close = findViewById(R.id.header_close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        close.setVisibility(View.VISIBLE);
        return close;
    }


    public ImageButton getLeftBtn() {
        ImageButton left = (ImageButton) findViewById(R.id.header_left_btn);
        left.setVisibility(View.VISIBLE);
        return left;
    }

    public ImageButton getRightBtn() {
        ImageButton right = (ImageButton) findViewById(R.id.header_right_btn);
        right.setVisibility(View.VISIBLE);
        return right;
    }

    public TextView getRightTextView() {
        TextView right = (TextView) findViewById(R.id.header_right_text_btn);
        right.setVisibility(View.VISIBLE);
        return right;
    }


    public void showLoadingView(View loadingView) {
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();
        ImageView imageView = (ImageView) loadingView.findViewById(R.id.loadingview_image);
    }

    public void hideLoadingView(View loadingView) {
        loadingView.setVisibility(View.GONE);
    }


    public void showLoading(String title) {
        hideLoading();
        loadingView = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);
        TextView titleView = (TextView) loadingView.findViewById(R.id.dialog_loading_title);
        if (title != null) {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }
        showCustomLoadingView();
    }

    public void showCustomLoadingView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View header = findViewById(R.id.header);
        if (header != null) {
            params.addRule(RelativeLayout.BELOW, R.id.header);
        }
        loadingView.setLayoutParams(params);
        loadingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        RelativeLayout contentView = (RelativeLayout) findViewById(R.id.root);
        contentView.addView(loadingView);
        contentView.bringChildToFront(loadingView);
    }

    public void hideLoading() {
        if (loadingView != null) {
            RelativeLayout contentView = (RelativeLayout) findViewById(R.id.root);
            contentView.removeView(loadingView);
            loadingView = null;
        }
    }

    public ProgressBar showCustomProgressView(String tip) {
        hideLoading();
        loadingView = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null);
        ProgressBar progressBar = (ProgressBar) loadingView.findViewById(R.id.dialog_progress_bar);
        TextView progressTextView = (TextView) loadingView.findViewById(R.id.dialog_progress_text);
        if (tip != null) {
            progressTextView.setText(tip);
        }
        progressBar.setProgress(0);
        showCustomLoadingView();
        return progressBar;
    }

    public void showText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    public void showConfirmCancelDialog(String title, final View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        this.showConfirmCancelDialog(title, null, null, confirmListener, cancelListener);
    }


    public void showConfirmCancelDialog(String title, String confirmTitle, String detail, final View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View layout = getLayoutInflater().inflate(R.layout.dialog_confirmcancel, null);
        // set the dialog title
        TextView titleView = (TextView) layout.findViewById(R.id.dialog_confirmcancel_title);
        titleView.setText(title);
        TextView confirmView = (TextView) layout.findViewById(R.id.dialog_confirmcancel_confirm);
        if (confirmTitle != null) {
            confirmView.setText(confirmTitle);
        }
        if (detail != null) {
            TextView textView = (TextView) layout.findViewById(R.id.dialog_confirmcancel_detail);
            textView.setVisibility(View.VISIBLE);
            textView.setText(detail);
        }
        layout.findViewById(R.id.dialog_confirmcancel_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.dialog_confirmcancel_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.onClick(v);
                }
                dialog.cancel();
            }
        });

        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
    }


    public void showConfirmDialog(String title, final View.OnClickListener confirmListener) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View layout = getLayoutInflater().inflate(R.layout.dialog_confirmcancel, null);
        // set the dialog title
        TextView titleView = (TextView) layout.findViewById(R.id.dialog_confirmcancel_title);
        titleView.setText(title);
        layout.findViewById(R.id.dialog_confirmcancel_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.dialog_confirmcancel_cancelcontainer).setVisibility(View.GONE);

        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
    }

    public void showUpdateDialog(String title, String confirmTitle, String detail, final View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View layout = getLayoutInflater().inflate(R.layout.dialog_update, null);
        // set the dialog title
        TextView confirmView = (TextView) layout.findViewById(R.id.dialog_update_confirm);
        if (confirmTitle != null) {
            confirmView.setText(confirmTitle);
        }
        if (detail != null) {
            TextView textView = (TextView) layout.findViewById(R.id.dialog_update_detail);
            textView.setVisibility(View.VISIBLE);
            textView.setText(detail);
        }
        layout.findViewById(R.id.dialog_update_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.dialog_update_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.onClick(v);
                }
                dialog.cancel();
            }
        });

        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
    }


    public void refreshView() {
    }

    public void showHintView(int imgResId, View.OnClickListener listener) {
        View hintView = findViewById(R.id.hintview);
        hintView.setVisibility(View.VISIBLE);
        hintView.setOnClickListener(listener);
        hintView.bringToFront();
        ImageView imageView = (ImageView) hintView.findViewById(R.id.hintview_image);
        imageView.setImageResource(imgResId);
    }

    public void hideHintView() {
        View hintView = findViewById(R.id.hintview);
        hintView.setVisibility(View.GONE);
    }

    public enum ActivityState {
        RUN,
        STOP
    }


}
