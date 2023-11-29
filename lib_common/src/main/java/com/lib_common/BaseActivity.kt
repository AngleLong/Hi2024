package com.lib_common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStateBar()

        //如果有参数要处理,需要该方法,所以把该方法直接放这里就好了.
        ARouter.getInstance().inject(this)

        //构建binding对象
        setContentView(createBinding())

        //配置View的相关参数
        operationView()

        //绑定相关内容, 但是这里不一定需要, 因为使用了hilt 可能就不需要了.
        bindAction()

        //初始化页面数据的请求
        initData()
    }

    abstract fun operationView()
    abstract fun bindAction()


    /**
     * 可以自行构建改方法,从而更改相关的状态栏
     */
    open fun initStateBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true).init()
    }

    /**
     * 初始化页面数据的请求,例如进页面的请求
     */
    open fun initData() {

    }

    private fun createBinding(): View {
        val superclass = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method: Method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            mBinding = method.invoke(null, layoutInflater) as VB
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return mBinding.root
    }
}