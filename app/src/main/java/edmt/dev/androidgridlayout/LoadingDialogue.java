package edmt.dev.androidgridlayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialogue {
    Activity activity;
    AlertDialog dialog;

    public LoadingDialogue(Activity activity){
        this.activity = activity;
    }

    void startLoadingDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.popupwindow,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    void dismissLoadingDIalogue(){
        dialog.dismiss();
    }
}
