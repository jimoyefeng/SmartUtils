package com.example.user.testkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_demo.setOnClickListener {
            //            rundemo()
//            testWith2()
            testRun()
        }

    }


    fun rundemo() {

        val a = "我是帅比".run {
            3
            4
            this
            5
        }
        Log.i("TEST", "-=-=-=--》" + a)

    }


    fun testWith() {
        // fun <T, R> with(receiver: T, f: T.() -> R): R = receiver.f()
        val a = ArrayList<String>().apply {
            add("testWith")
            add("testWith")
            add("testWith")
        }
        Log.i("TEST", "-=-=-=--》" + a)
    }

    fun testWith2() {
        // fun <T, R> with(receiver: T, f: T.() -> R): R = receiver.f()
        with(ArrayList<String>()) {
            add("testWith")
            add("testWith")
            add("testWith")
            Log.i("TEST", "-=-=-=--》" + this)
        }.let { Log.i("TEST", "-=-=-=--》" + it) }
    }

    fun testRun() {
        // fun <T, R> T.run(f: T.() -> R): R = f()
        "testRun".run {
            1
            2
            3
            4
            println("this1111 = " + this)
        }.let { println(it) }
    }

}
