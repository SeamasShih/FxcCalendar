package com.honhai.foxconn.fxccalendar.elsemember.GroupMemberList;

class GroupMember {
    public GroupMember(int imageID, String name) {

        this.memberImageID = imageID;
        this.memberName = name;
    }

    private String memberName;
    private int memberImageID;

    public String getMemberName() {
        return memberName;
    }

    public int getMemberImageID() {
        return memberImageID;
    }
}
