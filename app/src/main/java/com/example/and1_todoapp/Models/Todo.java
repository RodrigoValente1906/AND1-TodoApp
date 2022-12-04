package com.example.and1_todoapp.Models;

public class Todo
{
    public String title;
    public String deadline;
    public String description;
    public Board board;
    public Member member;
    public Reminder reminder;

    public Todo()
    {

    }

    public Todo(String title, String deadline, String description, Board board, Member member, Reminder reminder)
    {
        this.title = title;
        this.deadline = deadline;
        this.description = description;
        this.board = board;
        this.member = member;
        this.reminder = reminder;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setDeadline(String deadline)
    {
        this.deadline = deadline;
    }

    public String getDeadline()
    {
        return deadline;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setMember(Member member)
    {
        this.member = member;
    }

    public Member getMember()
    {
        return member;
    }

    public void setReminder(Reminder reminder)
    {
        this.reminder = reminder;
    }

    public Reminder getReminder()
    {
        return reminder;
    }
}
