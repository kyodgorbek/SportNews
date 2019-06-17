package edgar.yodgorbek.yangiliklar.component;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;

import edgar.yodgorbek.yangiliklar.module.ContextModule;
import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    ApplicationComponent applicationComponent;

    public static MyApplication get(Activity activity) {
        return (MyApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        applicationComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
        applicationComponent.injectApplication(this);

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}


