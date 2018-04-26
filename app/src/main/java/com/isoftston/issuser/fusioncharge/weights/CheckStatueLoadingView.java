package com.isoftston.issuser.fusioncharge.weights;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.corelibs.views.CircularBar;
import com.isoftston.issuser.fusioncharge.R;

/**
 * Created by issuser on 2018/4/22.
 */

public class CheckStatueLoadingView extends Dialog {

    private static int theme = com.corelibs.R.style.dialog;
    private Context context;
    private LoadingView bar;
    private TextView msg;

    public CheckStatueLoadingView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
        setMessage(null);
    }
    public CheckStatueLoadingView(Context context, String message) {
        super(context, theme);
        this.context = context;
        init();
        setMessage(message);
    }

    public CheckStatueLoadingView(Context context, int message) {
        super(context, theme);
        this.context = context;
        init();
        setMessage(message);
    }

    private void init() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_check_statue_loading, null);
		setContentView(contentView);
		setCanceledOnTouchOutside(false);

        bar = (LoadingView) contentView.findViewById(R.id.view);
		msg = (TextView) contentView.findViewById(R.id.tv_msg);
		setCanceledOnTouchOutside(false);
	}


	public void setMessage(String message) {
		if (message == null) {
			if (msg.getVisibility() == View.VISIBLE)
				msg.setVisibility(View.GONE);
			return;
		}

		if (msg.getVisibility() == View.GONE)
			msg.setVisibility(View.VISIBLE);
		msg.setText(message);
	}

	public void setMessage(int message) {
		if (msg.getVisibility() == View.GONE)
			msg.setVisibility(View.VISIBLE);
		msg.setText(message);
	}

	@Override
	public void show() {
		super.show();
        bar.startAnimation(0,100,800);
	}

	@Override
	public void dismiss() {
        super.dismiss();
        bar.stopAnimator();
	}
}
