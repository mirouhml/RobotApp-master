package edmt.dev.androidgridlayout;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateVariables {
    private final ImageView blackImageView;
    private final ImageView redImageView;
    private final ImageView yellowImageView;
    private final ImageView blueImageView;
    private final ImageView purpleImageView;
    private final ImageView pinkImageView;
    private final ImageView greenImageView;
    private final ImageView whiteImageView;
    private final ImageView grayImageView;
    private final ImageView blackLocation;
    private final ImageView redLocation;
    private final ImageView yellowLocation;
    private final ImageView blueLocation;
    private final ImageView purpleLocation;
    private final ImageView pinkLocation;
    private final ImageView greenLocation;
    private final ImageView whiteLocation;
    private final ImageView grayLocation;
    private final ImageView blackMystery;
    private final ImageView redMystery;
    private final ImageView yellowMystery;
    private final ImageView blueMystery;
    private final ImageView purpleMystery;
    private final ImageView pinkMystery;
    private final ImageView greenMystery;
    private final ImageView whiteMystery;
    private final ImageView grayMystery;
    private final TextView blackText;
    private final TextView redText;
    private final TextView yellowText;
    private final TextView blueText;
    private final TextView purpleText;
    private final TextView pinkText;
    private final TextView greenText;
    private final TextView whiteText;
    private final TextView grayText;

    public CreateVariables(Activity activity) {
        blackImageView = activity.findViewById(R.id.imageBlackView);
        redImageView = activity.findViewById(R.id.imageRedView);
        yellowImageView = activity.findViewById(R.id.imageYellowView);
        blueImageView = activity.findViewById(R.id.imageBlueView);
        purpleImageView = activity.findViewById(R.id.imagePurpleView);
        pinkImageView = activity.findViewById(R.id.imagePinkView);
        greenImageView = activity.findViewById(R.id.imageGreenView);
        whiteImageView = activity.findViewById(R.id.imageWhiteView);
        grayImageView = activity.findViewById(R.id.imageGrayView);


        blackLocation = activity.findViewById(R.id.circleBlackView);
        redLocation = activity.findViewById(R.id.circleRedView);
        yellowLocation = activity.findViewById(R.id.circleYellowView);
        blueLocation = activity.findViewById(R.id.circleBlueView);
        purpleLocation = activity.findViewById(R.id.circlePurpleView);
        pinkLocation = activity.findViewById(R.id.circlePinkView);
        greenLocation = activity.findViewById(R.id.circleGreenView);
        whiteLocation = activity.findViewById(R.id.circleWhiteView);
        grayLocation = activity.findViewById(R.id.circleGrayView);

        blackMystery = activity.findViewById(R.id.blackMystery);
        redMystery = activity.findViewById(R.id.redMystery);
        yellowMystery = activity.findViewById(R.id.yellowMystery);
        blueMystery = activity.findViewById(R.id.blueMystery);
        purpleMystery = activity.findViewById(R.id.purpleMystery);
        pinkMystery = activity.findViewById(R.id.pinkMystery);
        greenMystery = activity.findViewById(R.id.greenMystery);
        whiteMystery = activity.findViewById(R.id.whiteMystery);
        grayMystery = activity.findViewById(R.id.grayMystery);

        blackText = activity.findViewById(R.id.blackViewText);
        redText = activity.findViewById(R.id.redViewText);
        yellowText = activity.findViewById(R.id.yellowViewText);
        blueText = activity.findViewById(R.id.blueViewText);
        purpleText = activity.findViewById(R.id.purpleViewText);
        pinkText = activity.findViewById(R.id.pinkViewText);
        greenText = activity.findViewById(R.id.greenViewText);
        whiteText = activity.findViewById(R.id.whiteViewText);
        grayText = activity.findViewById(R.id.grayViewText);
    }

    public ImageView getBlackImageView() {
        return blackImageView;
    }

    public ImageView getRedImageView() {
        return redImageView;
    }

    public ImageView getYellowImageView() {
        return yellowImageView;
    }

    public ImageView getBlueImageView() {
        return blueImageView;
    }

    public ImageView getPurpleImageView() {
        return purpleImageView;
    }

    public ImageView getPinkImageView() {
        return pinkImageView;
    }

    public ImageView getGreenImageView() {
        return greenImageView;
    }

    public ImageView getWhiteImageView() {
        return whiteImageView;
    }

    public ImageView getGrayImageView() {
        return grayImageView;
    }

    public ImageView getBlackLocation() {
        return blackLocation;
    }

    public ImageView getRedLocation() {
        return redLocation;
    }

    public ImageView getYellowLocation() {
        return yellowLocation;
    }

    public ImageView getBlueLocation() {
        return blueLocation;
    }

    public ImageView getPurpleLocation() {
        return purpleLocation;
    }

    public ImageView getPinkLocation() {
        return pinkLocation;
    }

    public ImageView getGreenLocation() {
        return greenLocation;
    }

    public ImageView getWhiteLocation() {
        return whiteLocation;
    }

    public ImageView getGrayLocation() {
        return grayLocation;
    }

    public ImageView getBlackMystery() {
        return blackMystery;
    }

    public ImageView getRedMystery() {
        return redMystery;
    }

    public ImageView getYellowMystery() {
        return yellowMystery;
    }

    public ImageView getBlueMystery() {
        return blueMystery;
    }

    public ImageView getPurpleMystery() {
        return purpleMystery;
    }

    public ImageView getPinkMystery() {
        return pinkMystery;
    }

    public ImageView getGreenMystery() {
        return greenMystery;
    }

    public ImageView getWhiteMystery() {
        return whiteMystery;
    }

    public ImageView getGrayMystery() {
        return grayMystery;
    }

    public TextView getBlackText() {
        return blackText;
    }

    public TextView getRedText() {
        return redText;
    }

    public TextView getYellowText() {
        return yellowText;
    }

    public TextView getBlueText() {
        return blueText;
    }

    public TextView getPurpleText() {
        return purpleText;
    }

    public TextView getPinkText() {
        return pinkText;
    }

    public TextView getGreenText() {
        return greenText;
    }

    public TextView getWhiteText() {
        return whiteText;
    }

    public TextView getGrayText() {
        return grayText;
    }
}
