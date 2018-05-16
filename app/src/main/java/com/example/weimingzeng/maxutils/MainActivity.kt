package com.example.weimingzeng.maxutils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.weimingzeng.maxutils.Activity.DrawerLayoutActivity
import com.example.weimingzeng.maxutils.viewpager.MaxViewPager

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewpager = findViewById<MaxViewPager>(R.id.maxViewpager)
        val datas = ArrayList<View>()
        val view1 = View(this)
        view1.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary))
        val view2 = View(this)
        view2.setBackgroundColor(this.getResources().getColor(R.color.white))
        val view3 = View(this)
        view3.setBackgroundColor(this.getResources().getColor(R.color.colorAccent))
        val view4 = TextView(this)
        view4.text = "aaaaaaa"
        val view5 = TextView(this)
        view4.text = "aaaaaaa"
        val view6 = View(this)
        view6.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary))
        datas.add(view5)
        datas.add(view1)
//        datas.add(view2)
        datas.add(view3)
        datas.add(view4)
        datas.add(view6)
        viewpager.setData(datas)
        val intent = Intent(this, DrawerLayoutActivity::class.java)
        startActivity(intent)
    }
}
