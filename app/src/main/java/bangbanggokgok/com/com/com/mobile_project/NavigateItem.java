package bangbanggokgok.com.com.com.mobile_project;

import android.graphics.Bitmap;

public class NavigateItem {
    private String seq;
    private String title;
    private Bitmap image;
    private String place;
    private String realmName;
    private String endDate;
    public NavigateItem(String seq, String title, Bitmap image, String place, String realmName, String endDate) {
        this.seq = seq;
        this.title = title;
        this.image = image;
        this.place = place;
        this.realmName = realmName;
        this.endDate = endDate;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place; }

    public String getRealmName() { return realmName; }

    public void setRealmName(String realmName) { this.realmName = realmName; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getSeq() { return seq; }

    public void setSeq(String seq) { this.seq = seq; }

    public void setImage(Bitmap image) { this.image = image; }

    public Bitmap getImage() { return image; }
}
