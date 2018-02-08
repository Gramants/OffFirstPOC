package com.example.offline.domain.services.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.offline.domain.services.rxbus.SyncEventsListByCatRxBus;
import com.example.offline.domain.services.rxbus.SyncHomePageRxBus;
import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.domain.services.networking.RemoteEndPointService;
import com.example.offline.domain.services.networking.RemoteException;
import com.example.offline.model.HomePageRaw;

import retrofit2.Response;
import timber.log.Timber;

public class SyncHomePageJob extends Job {
    private final int mSavedTimestamp;

    private static final String TAG = SyncHomePageJob.class.getCanonicalName();
    public SyncHomePageJob(int savedTimestamp) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG)
                .persist());

        this.mSavedTimestamp=savedTimestamp;
    }

    @Override
    public void onAdded() {
        Timber.e("Executing load homepage");
    }

    @Override
    public void onRun() throws Throwable {
        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        Response<HomePageRaw> homePageCall= RemoteEndPointService.getInstance().getHomePageFromRemote(mSavedTimestamp);
        Timber.e("Executing  Response<HomePage>:");
        SyncHomePageRxBus.getInstance().post(SyncResponseEventType.SUCCESS, homePageCall.body());
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        // sync to remote failed
        SyncEventsListByCatRxBus.getInstance().post(SyncResponseEventType.FAILED, null,0);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if(throwable instanceof RemoteException) {
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode < 500) {
                return RetryConstraint.CANCEL;
            }
        }
        // if we are here, most likely the connection was lost during job execution
        return RetryConstraint.RETRY;
    }
}
