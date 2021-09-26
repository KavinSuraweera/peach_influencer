package com.adrenaline.peach;

public class Products {

    String name,des,pfp,pip,pis,ptv,type,img;

    Products(){

    }

    public Products(String name, String des, String pfp, String pip, String pis, String ptv, String type, String img) {
        this.name = name;
        this.des = des;
        this.pfp = pfp;
        this.pip = pip;
        this.pis = pis;
        this.ptv = ptv;
        this.type = type;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getPip() {
        return pip;
    }

    public void setPip(String pip) {
        this.pip = pip;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public String getPtv() {
        return ptv;
    }

    public void setPtv(String ptv) {
        this.ptv = ptv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
