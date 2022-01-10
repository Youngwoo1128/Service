package com.example.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.example.service.databinding.ActivityBindServiceBinding

class BindService : AppCompatActivity() {

    lateinit var binding: ActivityBindServiceBinding
    var myService : MyService? = null
    var isService = false
    val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            //service와 통신 할수 있는 하나의 다리 같은 역할
            val binder = p1 as MyService.MyBinder
            myService = binder.getService()
            isService = true
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
            // 비정상적으로 Service가 종료 되었을떄 호출
            // 정상적으로 종료되면 호출 안됨
            isService = false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.serviceBind.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        binding.serviceUnbind.setOnClickListener {
            if (isService){
                unbindService(connection)
                isService = false
            }
        }
        binding.callServiceFunction.setOnClickListener {
            callServiceFunction()
        }
    }

    fun callServiceFunction(){
        if (isService){
            val message = myService?.serviceMessage()
            Toast.makeText(this, "message=${message}", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "서비스가 연결되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}