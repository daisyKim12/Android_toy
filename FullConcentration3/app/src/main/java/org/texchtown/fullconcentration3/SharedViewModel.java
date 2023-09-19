package org.texchtown.fullconcentration3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel  extends ViewModel {
    private MutableLiveData<String> selectTime = new MutableLiveData<String>();

    public void setConcentrationTime(String input) {
        selectTime.setValue(input);
    }

    public LiveData<String> getConcentrationTime() {
        return selectTime;
    }
}
