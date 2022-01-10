package com.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast

class MyService: Service() {

    val binder = MyBinder()

    override fun onBind(p0: Intent?): IBinder? {
        // onBind 메소드는 스타티드 서비스에서 사용하지는 않음
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.d("StartedService", "action = $action")
        return super.onStartCommand(intent, flags, startId)
    }
    
    override fun onDestroy() {
        Log.d("Service", "서비스가 종료되었습니다.")
        super.onDestroy()
    }

    fun serviceMessage(): String{
        return "Hello Activity! I am Service!"
    }

    companion object{
        val ACTION_START = "com.example.servicetest.START"
        val ACTION_RUN = "com.example.servicetest.RUN"
        val ACTION_STOP = "com.example.servicetest.STOP"
    }

    inner class MyBinder: Binder(){
        fun getService() : MyService{
            return this@MyService
        }
    }


}