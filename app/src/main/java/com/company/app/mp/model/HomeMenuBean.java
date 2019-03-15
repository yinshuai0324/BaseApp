package com.company.app.mp.model;

/**
 * 描述:首页菜单itemID
 * 创建时间：2018/11/1-4:34 PM
 *
 * @author: yinshuai
 */
public class HomeMenuBean {
    public String name;
    public int imageRes;
    public int selectImageRes;
    public String menuId;

    public HomeMenuBean(String name, int imageRes, int selectImageRes, String menuId) {
        this.name = name;
        this.imageRes = imageRes;
        this.selectImageRes = selectImageRes;
        this.menuId = menuId;

    }
}
