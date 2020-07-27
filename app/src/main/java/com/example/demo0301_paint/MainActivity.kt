package com.example.demo0301_paint

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream

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
                //Toast.makeText(this,"已经获取存储权限",Toast.LENGTH_SHORT).show()
                // 保存图片
                SaveImage()
            }
        }

        btn_clear.setOnClickListener {
            draw_view.clearCanvas()
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
                    //Toast.makeText(this,"已经获取存储权限",Toast.LENGTH_SHORT).show()
                    // 保存图片
                    SaveImage()
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

    /**
     * 保存图片
     */
    fun SaveImage()
    {

        // 获取draw_view的位图
        val bitmap = draw_view.getBitmap()
        // 将位图保存为png格式的字节输出流
        val bStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)

        // 将位图存储到系统相册中
        var img_uri = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap!!,
            "paint_image",
            "image from Paint")

        if(img_uri != null){
            Toast.makeText(this, "保存成功，请到相册查看", Toast.LENGTH_SHORT).show()
        }

    }


}
