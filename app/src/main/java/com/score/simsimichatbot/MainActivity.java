package com.score.simsimichatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listView;
    private ImageView sendButton;
    private EditText editText;
    private static ArrayList<Message> messagesList = new ArrayList<>();
    private MessagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.list_of_message);
        editText = findViewById(R.id.user_message);
        sendButton = findViewById(R.id.send);

        adapter = new MessagesAdapter(this, messagesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = editText.getText().toString();
                Message message = new Message(true, text);
                messagesList.add(message);
                adapter.setMessagesList(messagesList);
                adapter.notifyDataSetChanged();
                editText.setText("");

                listView.smoothScrollToPosition(adapter.getItemCount());
                new GetSimsimiResponse(getApplicationContext(), text, "en", new GetSimsimiResponse.setOnRequestListener() {
                    @Override
                    public void onRequestFinished(int status, String response) {
                        System.out.println(response);
                        Message message = new Message(false, response);
                        messagesList.add(message);
                        adapter.setMessagesList(messagesList);
                        adapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(adapter.getItemCount());
                    }
                });
            }
        });

        findViewById(R.id.root).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top,
                                       int right, int bottom, int oldLeft,
                                       int oldTop, int oldRight, int oldBottom) {

                listView.smoothScrollToPosition(adapter.getItemCount());
            }
        });

    }
}