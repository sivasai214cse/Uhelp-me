package intern.siva.uhelpme.Pojo;

public class CatogeryPojo extends PostId {
    int imageurl;
    String CatogeryName;

    public CatogeryPojo() {
    }

    public CatogeryPojo(int imageurl, String catogeryName) {
        this.imageurl = imageurl;
        CatogeryName = catogeryName;
    }

    public int getImageurl() {
        return imageurl;
    }

    public void setImageurl(int imageurl) {
        this.imageurl = imageurl;
    }

    public String getCatogeryName() {
        return CatogeryName;
    }

    public void setCatogeryName(String catogeryName) {
        CatogeryName = catogeryName;
    }
}
