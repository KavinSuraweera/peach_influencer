package com.adrenaline.peach;

import android.widget.EditText;

public class ReadWriteUserDetails {
    private EditText fName, lName, ContactNo, UserName;

    public ReadWriteUserDetails(EditText fName, EditText lName, EditText contactNo, EditText userName) {
        this.fName = fName;
        this.lName = lName;
        this.ContactNo = contactNo;
        this.UserName = userName;
    }
}
