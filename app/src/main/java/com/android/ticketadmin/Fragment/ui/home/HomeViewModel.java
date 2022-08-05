package com.android.ticketadmin.Fragment.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.ticketadmin.Model.Home.Status;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Status> statuses;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        statuses = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Status> getStatuses(){
        return statuses;
    }
}