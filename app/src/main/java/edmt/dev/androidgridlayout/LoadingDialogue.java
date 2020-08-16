package edmt.dev.androidgridlayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialogue {
    Activity activity;
    AlertDialog dialog;
    boolean isOn = false;

    public LoadingDialogue(Activity activity){
        this.activity = activity;
    }

    void startLoadingDialogue(int layout){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(layout,null));
        builder.setCancelable(false);
        isOn = true;
        dialog = builder.create();
        dialog.show();
    }

    void dismissLoadingDIalogue(){
        dialog.dismiss();
        isOn = false;
    }
}
