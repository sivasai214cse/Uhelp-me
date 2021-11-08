package intern.siva.uhelpme.Pojo;

import java.util.Date;

public class ItemsCatogeryPojo extends PostId
{
    String AddressLin1;
    String Landmark;
    String Name;
    String PhoneNo;
    String image;
    Date time;
    String user;
    String CatogerType;

    public ItemsCatogeryPojo(String addressLin1, String landmark, String name, String phoneNo, String image, Date time, String user,String CatogerType) {
        AddressLin1 = addressLin1;
        Landmark = landmark;
        Name = name;
        PhoneNo = phoneNo;
        this.image = image;
        this.time = time;
        this.user = user;
        this.CatogerType=CatogerType;
    }

    public String getCatogerType() {
        return CatogerType;
    }

    public void setCatogerType(String catogerType) {
        CatogerType = catogerType;
    }

    public String getAddressLin1() {
        return AddressLin1;
    }

    public void setAddressLin1(String addressLin1) {
        AddressLin1 = addressLin1;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ItemsCatogeryPojo() {
    }

}
