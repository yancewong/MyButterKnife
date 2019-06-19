package com.yw.injecttest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yw.library.InjectManager
import com.yw.library.InjectManagerExt

/**
 * @author wangyanpeng
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectManagerExt.inject(this)
    }
}
