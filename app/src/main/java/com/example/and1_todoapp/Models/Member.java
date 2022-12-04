package com.example.and1_todoapp.Models;

public class Member
{
    public String nickname;
    public String memberId;

    public Member()
    {

    }

    public Member(String nickname, String memberId)
    {
        this.nickname = nickname;
        this.memberId = memberId;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setMemberId(String memberId)
    {
        this.memberId = memberId;
    }

    public String getMemberId()
    {
        return memberId;
    }
}
