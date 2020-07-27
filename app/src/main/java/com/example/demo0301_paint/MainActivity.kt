package com.example.demo0301_paint

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_save.setOnClickListener {
            // 如果没有获取外部存储的写权限
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                // 动态获取权限
                GetPermission()
            }else{     // 如果已经获取外部设备的写权限
                Toast.makeText(this,"已经获取存储权限",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            101-> {
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"请打开存储权限",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"已经获取存储权限",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    /**
     * 获取外部存储的权限
     */
    fun GetPermission()
    {
        // 如果Android版本号大于等于23，Android6.0以上
        if (Build.VERSION.SDK_INT >= 23) {
            // 定义获得权限的返回码
            val REQUEST_CODE_CONTACT = 101
            // 定义外部存储的写权限
            val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            //验证是否许可权限
            for (str in permissions) {
                // 如果不在许可列别中
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT)
                }
            }
        }
    }

}
