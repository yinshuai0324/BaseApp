package com.company.app.view.alert;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.company.app.R;


/**
 * Created by Jango on 2015/6/5.
 */
public class BaseAlert extends BaseAlertDialog {
    TextView noButt;
    TextView yesButt;
    TextView title;
    TextView text;

    View buttHLine;
    View buttVLine;

    String titleStr = "";
    String textStr = "";
    String yesStr = "";
    String noStr = "";

    SpannableStringBuilder textBuilder = null;

    boolean enableYesButt = true;
    boolean enableNoButt = true;


    public interface ButtListener {
        void click(BaseAlert baseAlert);
    }

    private ButtListener yesButtListener;
    private ButtListener noButtListener;

    public void setYesButtListener(ButtListener buttListener) {
        yesButtListener = buttListener;
    }

    public void setNoButtListener(ButtListener buttListener) {
        noButtListener = buttListener;
    }

    public void enableYesButt(boolean enable) {
        enableYesButt = enable;
    }

    public void enableNoButt(boolean enable) {
        enableNoButt = enable;
    }

    private void checkLines() {
        if (enableYesButt && enableNoButt) {
            buttHLine.setVisibility(View.VISIBLE);
            buttVLine.setVisibility(View.VISIBLE);
        } else if (enableYesButt || enableNoButt) {
            buttHLine.setVisibility(View.VISIBLE);
            buttVLine.setVisibility(View.GONE);
        }
        if (!enableYesButt && !enableNoButt) {
            buttHLine.setVisibility(View.GONE);
            buttVLine.setVisibility(View.GONE);
        }
    }


    public BaseAlert(Context context, int alert_view_directionsforuse) {
        super(context, R.layout.view_alert_dialog);
    }

    public BaseAlert(Context context, String title, String text, String yesStr, String noStr, ButtListener yesButtListener, ButtListener noButtListener) {
        super(context, R.layout.view_alert_dialog);

        setTitle(title);
        setText(text);
        setYesStr(yesStr);
        setNoStr(noStr);
        setYesButtListener(yesButtListener);
        setNoButtListener(noButtListener);

    }

    public BaseAlert(Context context, String title, SpannableStringBuilder text, String yesStr, String noStr, ButtListener yesButtListener, ButtListener noButtListener) {
        super(context, R.layout.view_alert_dialog);

        setTitle(title);
        setText(text);
        setYesStr(yesStr);
        setNoStr(noStr);
        setYesButtListener(yesButtListener);
        setNoButtListener(noButtListener);

    }

    public BaseAlert(Context context, String title, String textStr, String yesStr, ButtListener yesButtListener) {
        super(context, R.layout.view_alert_dialog);

        setTitle(title);
        setText(textStr);
        setYesStr(yesStr);
        setYesButtListener(yesButtListener);

        enableNoButt(false);
    }

    public BaseAlert(Context context, String title, String text) {
        super(context, R.layout.view_alert_dialog);

        setTitle(title);
        setText(text);
        enableNoButt(false);
        enableYesButt(false);
    }


    void iniView() {

        if (titleStr == null) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(titleStr);
        }
        if (textBuilder == null) {
            text.setText(textStr);
        } else {
            text.setTextColor(Color.BLACK);
            text.setText(textBuilder);
        }
        yesButt.setText(yesStr);
        noButt.setText(noStr);

        if (enableNoButt) {
            noButt.setVisibility(View.VISIBLE);
        } else {
            noButt.setVisibility(View.GONE);
        }

        if (enableYesButt) {
            yesButt.setVisibility(View.VISIBLE);
        } else {
            yesButt.setVisibility(View.GONE);
        }
        checkLines();
    }

    public void show() {
        if (mContext instanceof Activity){
            if (((Activity)mContext).isFinishing()) {
                return;
            }
        }
        super.show();

        noButt = (TextView) findViewById(R.id.no);
        yesButt = (TextView) findViewById(R.id.yes);
        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);

        buttHLine = findViewById(R.id.buttHLine);
        buttVLine = findViewById(R.id.buttVLine);

        iniView();

        yesButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesButtListener != null)
                    yesButtListener.click(BaseAlert.this);
                dismiss();
            }
        });

        noButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noButtListener != null)
                    noButtListener.click(BaseAlert.this);
                dismiss();
            }
        });
    }

    public void setTitle(String str) {
        titleStr = str;
    }

    public void setText(String str) {
        textStr = str;
    }

    public void setYesStr(String str) {
        yesStr = str;
    }

    public void setNoStr(String str) {
        noStr = str;
    }

    public void setYesButt(String str, ButtListener listener) {
        setYesStr(str);
        setYesButtListener(listener);
    }

    public void setNoButt(String str, ButtListener listener) {
        setNoStr(str);
        setNoButtListener(listener);
    }

    public void setText(SpannableStringBuilder builder) {
        textBuilder = builder;
    }
}
