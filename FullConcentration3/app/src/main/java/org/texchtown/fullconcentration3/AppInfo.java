package org.texchtown.fullconcentration3;

import android.graphics.drawable.Drawable;

import java.util.Objects;

public class AppInfo {

    //data type to save
    String packageName;
    String appName;
    Drawable appLogo;
    boolean appStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfo)) return false;
        AppInfo appInfo = (AppInfo) o;
        return Objects.equals(getPackageName(), appInfo.getPackageName());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    //getting setting method
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(Drawable appLogo) {
        this.appLogo = appLogo;
    }

    public boolean isAppStatus() {
        return appStatus;
    }

    public void setAppStatus(boolean appStatus) {
        this.appStatus = appStatus;
    }
}
