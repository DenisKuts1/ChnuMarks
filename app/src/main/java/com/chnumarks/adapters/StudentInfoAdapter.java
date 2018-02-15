package com.chnumarks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chnumarks.R;
import com.chnumarks.fragments.StudentListFragment;
import com.chnumarks.models.StudentInfo;

import java.util.List;

/*
 * {@link RecyclerView.Adapter} that can display a {@link StudentInfo} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.ViewHolder> {

    private final List<StudentInfo> mValues;
    private final StudentListFragment.OnListFragmentInteractionListener mListener;
    private int maxLabs;

    public StudentInfoAdapter(List<StudentInfo> items, int maxLabs, StudentListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.maxLabs = maxLabs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_studentinfo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /*
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        */

        holder.studentInfo = mValues.get(position);
        holder.tvName.setText(holder.studentInfo.name);
        holder.tvTotal.setText(String.valueOf(holder.studentInfo.getTotalSum()));
        holder.pbProgress.setMax(maxLabs);
        holder.pbProgress.setProgress(holder.studentInfo.mark.size());


        /*
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                  //  mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View ourView;
        public final TextView tvName;
        public final TextView tvTotal;
        public final ProgressBar pbProgress;
        public StudentInfo studentInfo;

        public ViewHolder(View view) {
            super(view);
            ourView = view;
            tvName = (TextView)view.findViewById(R.id.tvName);
            tvTotal = (TextView)view.findViewById(R.id.tvTotal);
            pbProgress = (ProgressBar) view.findViewById(R.id.pbProgress);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "To string method" + "'";
        }
    }
}
