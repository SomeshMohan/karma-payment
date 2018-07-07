package karmachallenge.com.karmapay

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import karmachallenge.com.karmapay.ui.main.LoginFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow()
        }
    }

}
