package com.sort;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MediaService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub.asBinder();
    }


    private IMediaService.Stub stub = new IMediaService.Stub() {
        @Override
        public void play(String url) throws RemoteException {

        }

        @Override
        public int getState() throws RemoteException {
            return 0;
        }
    };
}
