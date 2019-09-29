package com.sort;

import android.app.Service;
import android.content.*;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("TAG", "dd");

        }
    };


    @Override
    public void onCreate() {
        Log.e("MyService","onCreate" );
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction("MyService");

        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService","onStartCommand" );

        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyService","onBind" );
        return new MyBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e("MyService","onRebind" );
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.e("MyService","unbindService" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyService","onDestroy" );
    }


    class MyBinder extends Binder {


    }

}
