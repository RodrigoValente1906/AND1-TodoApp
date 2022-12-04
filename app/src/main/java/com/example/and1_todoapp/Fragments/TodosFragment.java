package com.example.and1_todoapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and1_todoapp.R;
import com.example.and1_todoapp.Adapters.TodosAdapter;

public class TodosFragment extends Fragment
{
    RecyclerView todosList;
    TodosAdapter todosAdapter;
    Context context;
    Button addTodo;

    public TodosFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = container.getContext();
        View view = inflater.inflate(R.layout.todos_fragment, container, false);

        todosList = view.findViewById(R.id.recyclerViewTodos);
        todosList.hasFixedSize();
        todosList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        todosAdapter = new TodosAdapter();
        todosAdapter.getTodoData();
        todosList.setAdapter(todosAdapter);

        addTodo =  view.findViewById(R.id.addTodo);

        addTodo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(TodosFragment.this).navigate(R.id.create_todo_fragment);
            }
        });

        return view;
    }
}
