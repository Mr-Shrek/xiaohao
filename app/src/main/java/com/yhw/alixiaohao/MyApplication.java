package com.yhw.alixiaohao;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yhw.alixiaohao.entity.BindTelDao;
import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.entity.CallRecordDao;
import com.yhw.alixiaohao.entity.DaoMaster;
import com.yhw.alixiaohao.entity.DaoSession;
import com.yhw.alixiaohao.entity.MyUserDao;
import com.yhw.alixiaohao.entity.SIMStatus;
import com.yhw.alixiaohao.entity.SIMStatusDao;
import com.yhw.alixiaohao.entity.SmallNumberDao;
import com.yhw.alixiaohao.entity.SpecTCDao;
import com.yhw.alixiaohao.entity.UserDao;
import com.yhw.alixiaohao.entity.UserInfoDao;
import com.yhw.alixiaohao.utils.DensityUtil;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.SPUtil;

public class MyApplication extends Application {

    private Logcat log = new Logcat(MyApplication.class);
    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";

    private static Context context;

    public static Context getContext() {
        return context;
    }


    /**
     * 运行在OnCreate之前
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);//dex文件拆分，如果dex大于65536拆分dex文件
        /*if (!quickStart() && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//>=5.0的系统默认对dex进行oat优化
            if (needWait(base)){
                waitForDexopt(base);
            }
            MultiDex.install (this );
        } else {
            return;
        }*/

    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*if (quickStart()) {
            return;
        }*/
        context = getApplicationContext();
        DensityUtil.init(this);
        SPUtil.init(this);
        initGreenDao();
        initTable();
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "alixiaohao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;
    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 初始化SqlLite表
     */
    private void initTable(){
        UserDao.createTable(daoSession.getDatabase(), true);
        CallRecordDao.createTable(daoSession.getDatabase(), true);
        SIMStatusDao.createTable(daoSession.getDatabase(), true);
        UserInfoDao.createTable(daoSession.getDatabase(), true);
        BindTelDao.createTable(daoSession.getDatabase(), true);
        SpecTCDao.createTable(daoSession.getDatabase(), true);
        SmallNumberDao.createTable(daoSession.getDatabase(), true);
        MyUserDao.createTable(daoSession.getDatabase() ,true);
    }


/*
    //-----------------------------------65536 error MultiDex 拆分 dex 文件，安装首次启动 ANR 问题，
    public boolean quickStart() {
        if (StringUtils.contains( getCurProcessName(this), ":mini")) {
            log.d("loadDex : mini start!");
            return true;
        }
        return false ;
    }

    //neead wait for dexopt ?
    private boolean needWait(Context context){
        String flag = get2thDexSHA1(context);
        log.d("loadDex : dex2-sha1 :"+flag);
        SharedPreferences sp = context.getSharedPreferences(
                PackageUtil.getPackageInfo(context). versionName, MODE_MULTI_PROCESS);
        String saveValue = sp.getString(KEY_DEX2_SHA1, "");
        return !StringUtils.equals(flag,saveValue);
    }

    //Get classes.dex file signature
    private String get2thDexSHA1(Context context) {
        ApplicationInfo ai = context.getApplicationInfo();
        String source = ai.sourceDir;
        try {
            JarFile jar = new JarFile(source);
            Manifest mf = jar.getManifest();
            Map<String, Attributes> map = mf.getEntries();
            Attributes a = map.get("classes2.dex");
            return a.getValue("SHA1-Digest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    // optDex finish
    public void installFinish(Context context){
        SharedPreferences sp = context.getSharedPreferences(
                PackageUtil.getPackageInfo(context).versionName, MODE_MULTI_PROCESS);
        sp.edit().putString(KEY_DEX2_SHA1,get2thDexSHA1(context)).commit();
    }

    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context. ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess. processName;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return null ;
    }

    public void waitForDexopt(Context base) {
        Intent intent = new Intent();
        ComponentName componentName = new
                ComponentName( "com.yhw.alixiaohao.activity", LoadResActivity.class.getName());
        intent.setComponent(componentName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        base.startActivity(intent);
        long startWait = System.currentTimeMillis ();
        long waitTime = 10 * 1000 ;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1 ) {
            waitTime = 20 * 1000 ;//实测发现某些场景下有些2.3版本有可能10s都不能完成optdex
        }
        while (needWait(base)) {
            try {
                long nowWait = System.currentTimeMillis() - startWait;
                log.d("loadDex : wait ms :"+nowWait);
                if (nowWait >= waitTime) {
                    return;
                }
                Thread.sleep(200 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/


}
