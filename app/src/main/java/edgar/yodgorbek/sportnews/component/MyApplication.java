package edgar.yodgorbek.sportnews.component;

import android.app.Activity;
import android.app.Application;

import edgar.yodgorbek.sportnews.module.ContextModule;
import com.crashlytics.android.Crashlytics;
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


