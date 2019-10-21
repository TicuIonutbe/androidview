package com.wave.dagger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Member {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("documentList")
    @Expose
    private ArrayList<Document> documentList;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("password")
    @Expose()
    private String password;

    @SerializedName("status")
    @Expose()
    private int status;

    @SerializedName("isRequester")
    @Expose()
    private boolean isRequester;

    public Member() {
    }

    public Member(String email, String firstName, String lastName, ArrayList<Document> documentList, int id) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentList = documentList;
        this.id = id;
    }

    public Member(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(ArrayList<Document> documentList) {
        this.documentList = documentList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRequester() {
        return isRequester;
    }

    public void setRequester(boolean requester) {
        isRequester = requester;
    }
}
