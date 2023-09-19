package org.texchtown.ibk_bank_home

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

class ListeningToActivityCallbacks : Application.ActivityLifecycleCallbacks {

    private val TAG = "Application"

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPostCreated(activity, savedInstanceState)
    }

    override fun onActivityPreStarted(activity: Activity) {
        super.onActivityPreStarted(activity)
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityPostStarted(activity: Activity) {
        super.onActivityPostStarted(activity)
    }

    override fun onActivityPreResumed(activity: Activity) {
        super.onActivityPreResumed(activity)
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPostResumed(activity: Activity) {
        super.onActivityPostResumed(activity)
    }

    override fun onActivityPrePaused(activity: Activity) {
        super.onActivityPrePaused(activity)
        Log.d(TAG, "onActivityPrePaused: ")
    }

    override fun onActivityPaused(p0: Activity) {
        Log.d(TAG, "onActivityPaused: ")
    }

    override fun onActivityPostPaused(activity: Activity) {
        super.onActivityPostPaused(activity)
        Log.d(TAG, "onActivityPostPaused: ")
    }

    override fun onActivityPreStopped(activity: Activity) {
        super.onActivityPreStopped(activity)
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityPostStopped(activity: Activity) {
        super.onActivityPostStopped(activity)
    }

    override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPreSaveInstanceState(activity, outState)
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPostSaveInstanceState(activity, outState)
    }

    override fun onActivityPreDestroyed(activity: Activity) {
        super.onActivityPreDestroyed(activity)
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        super.onActivityPostDestroyed(activity)
    }
}