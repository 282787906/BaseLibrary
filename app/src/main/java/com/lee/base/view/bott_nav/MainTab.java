package com.lee.base.view.bott_nav;


/**
 * Created by liqg on 2015/10/23.
 */
public class MainTab {
    boolean isSelected;
    int name;
    int image_normal;
    int image_selected;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getImage_normal() {
        return image_normal;
    }

    public void setImage_normal(int image_normal) {
        this.image_normal = image_normal;
    }

    public int getImage_selected() {
        return image_selected;
    }

    public void setImage_selected(int image_selected) {
        this.image_selected = image_selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public MainTab(int name,
                   int image_normal,
                   int image_selected) {
        setName(name);
        setImage_normal(image_normal);
        setImage_selected(image_selected);
    }

}
