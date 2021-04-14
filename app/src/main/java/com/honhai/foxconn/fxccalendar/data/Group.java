package com.honhai.foxconn.fxccalendar.data;

import java.util.ArrayList;

public class Group {
    private int groupId;
    private String groupName;
    private ArrayList<String> workId;
    private String objectId;
    private String groupIcon;

    private long localId;

    public Group(int groupId, String groupName, ArrayList<String> workId, String objectId, String groupIcon) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.workId = workId;
        this.objectId = objectId;
        this.groupIcon = groupIcon;
    }

    public Group() {

    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<String> getWorkId() {
        return workId;
    }

    public void setWorkId(ArrayList<String> workId) {
        this.workId = workId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }
}
