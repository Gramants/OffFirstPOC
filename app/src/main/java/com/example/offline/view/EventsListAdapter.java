package com.example.offline.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.offline.R;
import com.example.offline.model.Event;

import java.util.List;

import timber.log.Timber;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    private final List<Event> events;
    private final OnItemClickListener listener;

    public void clearItems() {
        this.events.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Event item);
    }

    public EventsListAdapter(List<Event> events, OnItemClickListener listener) {
        this.events = events;
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(events.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }

    public void updateCommentList(List<Event> newEvents) {
        Timber.d("Got new events " + newEvents.size());
        this.events.clear();
        this.events.addAll(newEvents);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mEventTitle;
        private TextView mEventDate;
        private LinearLayout mContainer;

        public ViewHolder (View itemView) {
            super(itemView);
            mContainer = (LinearLayout)itemView.findViewById(R.id.container);
            mEventTitle = (TextView) itemView.findViewById(R.id.eventtitle);
            mEventDate = (TextView) itemView.findViewById(R.id.eventdate);
        }

        public void bindItem(Event item, OnItemClickListener listener) {
            mEventTitle.setText(item.getTitolo());
            mEventDate.setText(item.getLocation());

            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }



}
