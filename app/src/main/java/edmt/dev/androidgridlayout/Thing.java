package edmt.dev.androidgridlayout;

public class Thing {
    private String name;
    private String gender;
    private int ImageResourceId;
    private boolean hasImage = true;
    private boolean hasText = true;
    private int number;

    public Thing(String name, String gender, int ImageResourceId) {
        this.name = name;
        this.gender = gender;
        this.ImageResourceId = ImageResourceId;
        this.hasImage = true;
        hasText = true;
    }

    public Thing(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.hasImage = false;
        hasText = true;
    }

    public Thing(int number, int ImageResourceId) {
        this.number = number;
        this.ImageResourceId = ImageResourceId;
    }

    public Thing(String name) {
        this.name = name;
        this.hasImage = false;
        hasText = false;
    }

    public String getNAme() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getImageResourceId() {
        return ImageResourceId;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public boolean isHasText() {
        return hasText;
    }

    public int getNumber() {
        return number;
    }
}
