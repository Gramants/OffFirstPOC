package com.example.offline.domain.services.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.offline.domain.services.rxbus.SyncEventsListByCatRxBus;
import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.domain.services.networking.RemoteEndPointService;
import com.example.offline.domain.services.networking.RemoteException;
import com.example.offline.model.Event;

import java.io.File;
import java.util.List;

import retrofit2.Response;
import timber.log.Timber;

import static com.example.offline.BuildConfig.API_BASE_URL_IMAGES;

public class SyncEventsByCatJob extends Job {

    private static final String TAG = SyncEventsByCatJob.class.getCanonicalName();
    private final int mCatId;
    private final int mFromPk;
    public SyncEventsByCatJob(int catId,int fromPk) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG)
                .persist());
        this.mCatId = catId;
        this.mFromPk= fromPk;
    }

    @Override
    public void onAdded() {
        Timber.d("Executing onAdded() for cat id: " + mCatId);
    }




    @Override
    public void onRun() throws Throwable {
        Timber.d("Executing onRun()  for cat id: " + mCatId);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        Response<List<Event>> eventsByCatCall= RemoteEndPointService.getInstance().getEventsByCatId(mCatId,mFromPk);

        SyncEventsListByCatRxBus.getInstance().post(SyncResponseEventType.SUCCESS, eventsByCatCall.body(),mCatId);

        for( Event event : eventsByCatCall.body() ) {
            FutureTarget<File> future = Glide.with(getApplicationContext())
                    .load(API_BASE_URL_IMAGES+ event.getFoto())
                    .downloadOnly(300, 300);
                    future.get();

        }



    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.d("canceling job. reason: %d, throwable: %s", cancelReason, throwable);
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
