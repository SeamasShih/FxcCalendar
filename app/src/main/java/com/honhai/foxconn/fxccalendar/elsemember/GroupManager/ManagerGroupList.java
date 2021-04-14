package com.honhai.foxconn.fxccalendar.elsemember.GroupManager;

public class ManagerGroupList {
    private String name;
    private String groupId;
    private boolean status;

    public ManagerGroupList(String name,String id, boolean status) {
        this.name = name;
        groupId = id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
