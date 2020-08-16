package edmt.dev.androidgridlayout;

public class Thing {
    private String name;
    private String gender;
    private int ImageResourceId;

    public Thing(String name, String gender, int ImageResourceId){
        this.name = name;
        this.gender = gender;
        this.ImageResourceId = ImageResourceId;
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
}
