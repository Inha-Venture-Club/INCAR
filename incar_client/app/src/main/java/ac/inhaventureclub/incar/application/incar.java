package ac.inhaventureclub.incar.application;

import android.app.Application;

import ac.inhaventureclub.incar.object.UserObject;

public class incar extends Application {
    public static int IS_GUEST = 0;
    public static UserObject USER = new UserObject();

    @Override
    public void onCreate() {
        super.onCreate();
        //HttpManager.createClient(this);
        //HttpManager.connectWebSocket();

    }
}
