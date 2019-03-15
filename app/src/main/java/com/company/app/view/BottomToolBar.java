package com.company.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.app.R;
import com.company.app.mp.model.HomeMenuBean;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * 描述:首页菜单选择View
 * 创建时间：2018/11/1-4:30 PM
 *
 * @author: yinshuai
 */
public class BottomToolBar extends LinearLayout {

    public List<HomeMenuBean> menuList;
    private OnMenuItemClickListener listener;

    private Context mContext;

    private int defaultSelect;
    private int selectFontColor;
    private int defaultFontColor;

    public BottomToolBar(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BottomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomToolBar);
        defaultSelect = array.getInt(R.styleable.BottomToolBar_defaultSelect, 1) - 1;
        selectFontColor = array.getColor(R.styleable.BottomToolBar_selectFontColor, Color.BLACK);
        defaultFontColor = array.getColor(R.styleable.BottomToolBar_defaultFontColor, Color.BLACK);
        init();
    }

    public void init() {
        this.setOrientation(HORIZONTAL);
    }


    public void setDatas(List<HomeMenuBean> datas) {
        removeAllViews();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                HomeMenuBean bean = datas.get(i);
                MenuItem item = new MenuItem(mContext, bean.name, bean.imageRes, bean.selectImageRes, bean.menuId);
                if (i == defaultSelect) {
                    item.select();
                }
                item.setOnListener(new OnItemClickListener() {
                    @Override
                    public void click(MenuItem item) {
                        if (listener != null) {
                            removeAllSelect();
                            item.select();
                            listener.itemClick(item.getMenuId());
                        }
                    }
                });
                addView(item);
            }
        }
    }


    public void setSelectPostion(int postion) {
        int size = this.getChildCount();
        if (postion > size) {
            return;
        }
        MenuItem item = (MenuItem) this.getChildAt(postion);
        if (item != null) {
            removeAllSelect();
            item.select();
        }
    }


    public void removeAllSelect() {
        int size = this.getChildCount();
        for (int i = 0; i < size; i++) {
            MenuItem item = (MenuItem) this.getChildAt(i);
            item.noSelect();
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.listener = listener;
    }

    public class MenuItem extends LinearLayout {
        private OnItemClickListener listener;
        private ImageView itemImage;
        private TextView itemName;
        private String menuId;
        public boolean isSelect = false;
        public int selectRes = 0;
        public int noSelectRes = 0;


        public MenuItem(Context context, String name, int resId, int selectRes, String menuId) {
            super(context);
            this.menuId = menuId;
            this.selectRes = selectRes;
            this.noSelectRes = resId;
            init(context);
            noSelect();
            setItemName(name);
        }

        public void init(Context context) {
            View rootView = LayoutInflater.from(context).inflate(R.layout.item_home_menu_view, this);
            itemImage = rootView.findViewById(R.id.itemImage);
            itemName = rootView.findViewById(R.id.itemName);

            LinearLayout.LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            this.setLayoutParams(params);

            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.click(MenuItem.this);
                    }
                }
            });
        }

        public void setItemImage(int resId) {
            if (resId <= 0) {
                itemImage.setImageResource(R.mipmap.ic_launcher);
            } else {
                itemImage.setImageResource(resId);
            }
        }

        public void setItemName(String name) {
            if (TextUtils.isEmpty(name)) {
                itemName.setText("菜单标题");
            } else {
                itemName.setText(name);
            }
        }

        public void setOnListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public void noSelect() {
            setItemImage(noSelectRes);
            itemName.setTextColor(defaultFontColor);
        }

        public void select() {
            setItemImage(selectRes);
            itemName.setTextColor(selectFontColor);
        }

        public String getMenuId() {
            return menuId;
        }
    }


    public interface OnItemClickListener {
        /**
         * item点击事件
         *
         * @param item
         */
        void click(MenuItem item);
    }


    public interface OnMenuItemClickListener {
        /**
         * item点击事件
         *
         * @param menuId
         */
        void itemClick(String menuId);
    }


}
