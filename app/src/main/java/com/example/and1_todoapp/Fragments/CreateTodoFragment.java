package com.example.and1_todoapp.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.and1_todoapp.Models.Member;
import com.example.and1_todoapp.Models.Reminder;
import com.example.and1_todoapp.Models.Todo;
import com.example.and1_todoapp.R;
import com.example.and1_todoapp.Models.Board;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CreateTodoFragment extends Fragment
{

    private Button displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://and1-todoapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    FirebaseDatabase firebaseDatabase;

    String[] boards = {"Football Game", "Party"};
    AutoCompleteTextView autoCompleteTextViewBoards;
    ArrayAdapter<String> adapterItemsBoards;

    AutoCompleteTextView autoCompleteTextViewMembers;
    ArrayAdapter<String> adapterItemsMembers;

    String[] reminders = {"1 week before", "1 day before", "1 hour before ", "10 minutes before"};
    AutoCompleteTextView autoCompleteTextViewReminder;
    ArrayAdapter<String> adapterItemsReminders;

    int year, month, day;
    int hour, minute;

    Button createTodoButton;

    String selectedMember;
    String selectedReminder;
    String selectedMemberID;
    Board selectedBoardd;

    TextView todoTitle;
    TextView todoDescription;

    String[] listOfMembers;

    Context context;

    public CreateTodoFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = container.getContext();

        View view = inflater.inflate(R.layout.create_todo_fragment, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        getDataMembers();
        getDataBoards();

        displayDate = view.findViewById(R.id.date);
        createTodoButton = view.findViewById(R.id.create_todo_button);

        todoTitle = view.findViewById(R.id.todo_title);
        todoDescription = view.findViewById(R.id.todo_description);

        createTodoButton.setOnClickListener(view1 -> {
            String deadline = "Deadline: " + hour + ":" + minute + " " + day + "/" + month + "/" + year;
            String title = todoTitle.getText().toString();
            String description = "Description: " + todoDescription.getText().toString();
            int todoId;

            Todo todo = new Todo(title,deadline,description,selectedBoardd, new Member(selectedMemberID,selectedMember), new Reminder(selectedReminder));

            databaseReference.child("todos").child(selectedMemberID).child(title).setValue(todo).addOnSuccessListener(aVoid -> {
                Log.d("Todo", "Successfully added to the database ");
            }).addOnFailureListener(e -> Log.w("Todo", "Cannot add the todo to the database"));

            NavHostFragment.findNavController(this).navigate(R.id.my_todos_menu);
        });

        autoCompleteTextViewBoards = view.findViewById(R.id.select_board);
        autoCompleteTextViewMembers = view.findViewById(R.id.select_member);
        autoCompleteTextViewReminder = view.findViewById(R.id.select_Reminder);
        adapterItemsReminders = new ArrayAdapter<String>(context, R.layout.item_list, reminders);
        autoCompleteTextViewReminder.setAdapter(adapterItemsReminders);
        autoCompleteTextViewReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedReminder = parent.getItemAtPosition(position).toString();
                Toast.makeText(context, "Reminder: " + selectedReminder, Toast.LENGTH_LONG).show();
            }
        });

        displayDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        android.R.style.Widget_Material_Light_ActionBar,
                        dateSetListener,
                        day, month, year);
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                displayDate.setText(date);
            }
        };
        return view;
    }

    private void getDataMembers()
    {

        databaseReference.child("member").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                HashMap<String,String> value =(HashMap<String, String>)snapshot.getValue();

                String[] array = value.values().toArray(new String[0]);

                adapterItemsMembers= new ArrayAdapter<String>(context, R.layout.item_list, array);
                autoCompleteTextViewMembers.setAdapter(adapterItemsMembers);
                autoCompleteTextViewMembers.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        selectedMember = parent.getItemAtPosition(position).toString();
                        for(int i=0;i<array.length;i++)
                        {
                            if(value.values().toArray(new String[0])[i].equals(selectedMember))
                                selectedMemberID = value.keySet().toArray(new String[0])[i];
                        }
                        Toast.makeText(context, "Member: " + selectedMember, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataBoards()
    {
        databaseReference.child("board").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                ArrayList<Board> boards = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    boards.add(ds.getValue(Board.class));
                }

                HashMap<String,Board> value =(HashMap<String, Board>)snapshot.getValue();

                for(int i=0;i<boards.size();i++)
                {
                    System.out.println(boards.get(i).boardName);
                }

                String array[] = new String[boards.size()];

                for(int i=0;i<boards.size();i++)
                {
                    array[i] = boards.get(i).boardName;
                }

                adapterItemsBoards = new ArrayAdapter<String>(context, R.layout.item_list, array);
                autoCompleteTextViewBoards.setAdapter(adapterItemsBoards);
                autoCompleteTextViewBoards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        String selectedBoard = parent.getItemAtPosition(position).toString();
                        Toast.makeText(context, "Board: " + selectedBoard, Toast.LENGTH_LONG).show();
                        for(int i=0;i<boards.size();i++)
                        {
                            if(selectedBoard.equals(boards.get(i).boardName))
                                selectedBoardd = boards.get(i);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
