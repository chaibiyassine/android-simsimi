package com.score.simsimichatbot;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.github.library.bubbleview.BubbleTextView;

import java.util.ArrayList;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    public ArrayList<Message> messagesList;
    private OnItemClickListener listener;
    private Context context;
    private MessagesAdapter.MessagesViewHolder holder;

    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messagesList = messages;

    }

    public interface OnItemClickListener {
        void onItemClick(int position, View messageView);
    }

    public void setMessagesList(ArrayList<Message> messagesList) {
        this.messagesList = messagesList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder {
        BubbleTextView messageSent;
        BubbleTextView messageReceived;


        MessagesViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            messageSent = itemView.findViewById(R.id.text_message_send);
            messageReceived = itemView.findViewById(R.id.text_message_receive);
            itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, itemView);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message, parent, false);
        return new MessagesViewHolder(view, listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final MessagesAdapter.MessagesViewHolder holder, int position) {
        this.holder = holder;
        if(messagesList.size() == 0) return;
        final Message currentMessage = messagesList.get(position);


        holder.messageReceived.setVisibility(View.GONE);
        holder.messageSent.setVisibility(View.GONE);

        if(currentMessage.isSend) {
            holder.messageSent.setVisibility(View.VISIBLE);
            holder.messageSent.setText(currentMessage.message);
        } else {
            holder.messageReceived.setVisibility(View.VISIBLE);
            holder.messageReceived.setText(currentMessage.message);
        }


    }







    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}

