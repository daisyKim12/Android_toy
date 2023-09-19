package org.texchtown.fullconcentration3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class AllowAppSettingFragment extends Fragment {


    //widget
    ImageView appOne, appTwo, appThree, appFour, appFive, appSix, appSeven, appEight;
    RecyclerView appList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_allow_app_setting, container, false);

        appOne = rootView.findViewById(R.id.app1);
        appTwo = rootView.findViewById(R.id.app2);
        appThree = rootView.findViewById(R.id.app3);
        appFour = rootView.findViewById(R.id.app4);
        appFive = rootView.findViewById(R.id.app5);
        appSix = rootView.findViewById(R.id.app6);
        appSeven = rootView.findViewById(R.id.app7);
        appEight = rootView.findViewById(R.id.app8);

        appList = rootView.findViewById(R.id.app_list);

        //connecting recycler object and layout manager
        GridLayoutManager gridManager = new GridLayoutManager(getActivity(), 4);
        appList.setLayoutManager(gridManager);

        //connecting recycler object and adapter
        loadAppList();

        return rootView;
    }


    //method to use at service
    public void loadAppList() {
        PackageManager packageManager = getActivity().getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //getting current app
        List<ResolveInfo> homeApps = packageManager.queryIntentActivities(intent, 0);
        //making a new list
        List<AppInfo> apps = new ArrayList<>();
        for (ResolveInfo info: homeApps) {
            //passing data from homeApp List to apps List
            AppInfo appInfo = new AppInfo();
            appInfo.setAppLogo(info.activityInfo.loadIcon(packageManager));
            appInfo.setPackageName(info.activityInfo.packageName);
            appInfo.setAppName((String) info.activityInfo.loadLabel(packageManager));
            apps.add(appInfo);
        }
        AppListAdapter adapter = new AppListAdapter(apps);
        appList.setAdapter(adapter);
    }
}