package com.chris.smartpark.busi.entity;

import java.util.Date;

/**
 * Created by lisen on 2018/12/2.
 */
public class DoorAuthEntity {
    private int CardID ;
    private int DoorID ;
    private String PassWord ;
    private Date DueDate ;
    private int AuthorType ;
    private int AuthorStatus ;
    private int UserTimeGrp;
    private int DownLoaded ;
    private int FirstDownLoaded ;
    private int PreventCard ;
    private Date StartTime ;

    public int getCardID() {
        return CardID;
    }

    public void setCardID(int cardID) {
        CardID = cardID;
    }

    public int getDoorID() {
        return DoorID;
    }

    public void setDoorID(int doorID) {
        DoorID = doorID;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public int getAuthorType() {
        return AuthorType;
    }

    public void setAuthorType(int authorType) {
        AuthorType = authorType;
    }

    public int getAuthorStatus() {
        return AuthorStatus;
    }

    public void setAuthorStatus(int authorStatus) {
        AuthorStatus = authorStatus;
    }

    public int getUserTimeGrp() {
        return UserTimeGrp;
    }

    public void setUserTimeGrp(int userTimeGrp) {
        UserTimeGrp = userTimeGrp;
    }

    public int getDownLoaded() {
        return DownLoaded;
    }

    public void setDownLoaded(int downLoaded) {
        DownLoaded = downLoaded;
    }

    public int getFirstDownLoaded() {
        return FirstDownLoaded;
    }

    public void setFirstDownLoaded(int firstDownLoaded) {
        FirstDownLoaded = firstDownLoaded;
    }

    public int getPreventCard() {
        return PreventCard;
    }

    public void setPreventCard(int preventCard) {
        PreventCard = preventCard;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }
}
