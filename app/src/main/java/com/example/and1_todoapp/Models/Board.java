package com.example.and1_todoapp.Models;

import java.util.ArrayList;

public class Board
{
    public String boardName;
    public String boardDescription;
    public Member owner;
    public ArrayList<Todo> todos;

    public Board()
    {

    }

    public Board(String boardName, String boardDescription, Member member)
    {
        this.boardName = boardName;
        this.boardDescription = boardDescription;
        owner = member;
        todos = new ArrayList<>();
    }

    public void addTodoToBoard(Todo todo)
    {
        todos.add(todo);
    }

    public void deleteTodo(String name)
    {
        todos.remove(name);
    }

    public ArrayList<Todo> getTodos()
    {
        return todos;
    }

    public void setBoardName(String boardName)
    {
        this.boardName = boardName;
    }

    public String getBoardName()
    {
        return boardName;
    }

    public void setBoardDescription(String boardDescription)
    {
        this.boardDescription = boardDescription;
    }

    public String getBoardDescription()
    {
        return boardDescription;
    }

    public void setTodos(ArrayList<Todo> todos)
    {
        this.todos = todos;
    }

    public void setOwner(Member owner)
    {
        this.owner = owner;
    }

    public Member getOwner()
    {
        return owner;
    }
}
