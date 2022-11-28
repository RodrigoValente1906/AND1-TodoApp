package com.example.and1_todoapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.and1_todoapp.Models.Board;
import com.example.and1_todoapp.Models.Member;
import com.example.and1_todoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBoardFragment extends Fragment {

    private TextView boardTitle;
    private TextView boardDescription;
    private Button createBoardButton;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://and1-todoapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    private static final String TAG = "CreateBoardFragment";
    Context context;

    public CreateBoardFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        View view = inflater.inflate(R.layout.create_board_fragment, container, false);

        boardTitle = view.findViewById(R.id.board_title_textView);
        boardDescription = view.findViewById(R.id.board_desc_textView);
        createBoardButton = view.findViewById(R.id.create_board_button);

        createBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = boardTitle.getText().toString();
                String description = boardDescription.getText().toString();
                String usedId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                Board board = new Board(title, description, new Member(usedId, userName));

                databaseReference
                        .child("board").child(usedId).child(title)
                        .setValue(board)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "Successfully added to the database ");

                        })
                        .addOnFailureListener(e -> Log.w(TAG, "Cannot add the board to the database"));

                NavHostFragment.findNavController(CreateBoardFragment.this).navigate(R.id.menu_myBoards);
            }
        });
        return view;
    }
}
