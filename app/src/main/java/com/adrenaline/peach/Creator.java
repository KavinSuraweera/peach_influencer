package com.adrenaline.peach;

public class Creator {

    private String fName;
    private String lName;
    private String contactNo;
    private String userName;
    private String eMail;
    private String password;

    public  Creator(){}

    public Creator(String fName, String lName, String contactNo, String userName, String eMail, String password)
    {
        this.fName = fName;
        this.lName = lName;
        this.contactNo = contactNo;
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
