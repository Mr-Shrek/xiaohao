package com.yhw.alixiaohao.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.Window;
import android.view.WindowManager;

import com.yhw.alixiaohao.MyApplication;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.utils.Logcat;

/**
 * dex 文件加载中调用的页面
 */
public class LoadResActivity extends Activity {

    private Logcat log = new Logcat(LoadResActivity.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.alpha_in, 0);
        setContentView(R.layout.layout_load);
        new LoadDexTask().execute();
    }

    class LoadDexTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                MultiDex.install(getApplication());
                log.d("loadDex : install finish");
//                ((MyApplication) getApplication()).installFinish(getApplication());
            } catch (Exception e) {
                log.d("loadDex : "+e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            log.d("loadDex : get install finish");
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        //cannot backpress
    }
}
