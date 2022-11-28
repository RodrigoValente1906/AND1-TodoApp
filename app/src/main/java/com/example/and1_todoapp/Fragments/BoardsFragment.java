package com.example.and1_todoapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and1_todoapp.R;
import com.example.and1_todoapp.Adapters.BoardsAdapter;

public class BoardsFragment extends Fragment {

    RecyclerView boardsList;
    BoardsAdapter boardsAdapter;
    Context context;
    Button button;

    public BoardsFragment()
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

        View view = inflater.inflate(R.layout.boards_fragment, container, false);

        button = view.findViewById(R.id.board_button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(BoardsFragment.this).navigate(R.id.create_board_fragment);
            }
        });

        boardsList = view.findViewById(R.id.recyclerViewBoard);
        boardsList.hasFixedSize();
        boardsList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        boardsAdapter = new BoardsAdapter();
        boardsAdapter.getBoardData();
        boardsList.setAdapter(boardsAdapter);

        boardsAdapter.setOnClickListener(board -> {
            Toast.makeText(context, board.getBoardName(), Toast.LENGTH_SHORT).show();

        });

        return view;
    }



}
