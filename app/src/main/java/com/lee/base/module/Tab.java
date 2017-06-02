package com.lee.base.module;

import java.util.List;

/**
 * Created by liqg on 2015/10/23.
 */
public class Tab {
    String name;
    String iconNormal;
    String iconSelected;
    String showWith;
    boolean isSelected;
    List<Module> modules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconNormal() {
        return iconNormal;
    }

    public void setIconNormal(String iconNormal) {
        this.iconNormal = iconNormal;
    }

    public String getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(String iconSelected) {
        this.iconSelected = iconSelected;
    }

    /**
     * 点击tab显示的界面
     * HomePublic 首页
     * MainModules 九宫格模块
     *
     * @return
     */
    public String getShowWith() {
        return showWith;
    }

    public void setShowWith(String showWith) {
        this.showWith = showWith;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsDefault(boolean isDefault) {
        this.isSelected = isDefault;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
