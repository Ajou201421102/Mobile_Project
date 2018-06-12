package bangbanggokgok.com.com.com.mobile_project;

public class Informaion {
    private String urlList;
    private String titleNode;
    private String placeNode ;
    private String realmNameNode;
    private String startDateNode;
    private String priceNode;
    private String endDateNode;
    private String GPS_x;
    private String GPS_y;
    private String seq;
    public Informaion(String urlList,String titleNode,String placeNode,String priceNode,String realmNameNode,String startDateNode,String endDateNode, String GPS_x, String GPS_y,String seq){
        this.urlList = urlList;
        this.titleNode = titleNode;
        this.placeNode = placeNode;
        this.priceNode = priceNode;
        this.realmNameNode =realmNameNode;
        this.startDateNode = startDateNode;
        this.endDateNode = endDateNode;
        this.GPS_x = GPS_x;
        this.GPS_y = GPS_y;
        this.seq = seq;
    }

    public void setUrlList(String urlList) {
        this.urlList = urlList;
    }

    public String getUrlList() {
        return urlList;
    }

    public void setTitleNode(String titleNode) {
        this.titleNode = titleNode;
    }

    public String getTitleNode() {
        return titleNode;
    }

    public void setPlaceNode(String placeNode) {
        this.placeNode = placeNode;
    }

    public String getPlaceNode() {
        return placeNode;
    }

    public void setRealmNameNode(String realmNameNode) {
        this.realmNameNode = realmNameNode;
    }

    public String getRealmNameNode() {
        return realmNameNode;
    }

    public void setStartDateNode(String startDateNode) {
        this.startDateNode = startDateNode;
    }

    public String getStartDateNode() {
        return startDateNode;
    }

    public void setPriceNode(String priceNode) {
        this.priceNode = priceNode;
    }

    public String getPriceNode() {
        return priceNode;
    }

    public void setEndDateNode(String endDateNode) {
        this.endDateNode = endDateNode;
    }

    public String getEndDateNode() {
        return endDateNode;
    }

    public void setGPS_x(String GPS_x) {
        this.GPS_x = GPS_x;
    }

    public String getGPS_x() {
        return GPS_x;
    }

    public void setGPS_y(String GPS_y) {
        this.GPS_y = GPS_y;
    }

    public String getGPS_y() {
        return GPS_y;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSeq() {
        return seq;
    }
}
