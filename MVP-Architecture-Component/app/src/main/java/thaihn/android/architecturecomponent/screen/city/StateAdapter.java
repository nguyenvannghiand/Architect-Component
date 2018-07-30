package thaihn.android.architecturecomponent.screen.city;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import thaihn.android.architecturecomponent.R;
import thaihn.android.architecturecomponent.data.model.State;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private List<State> mStates;

    public StateAdapter(List<State> list) {
        this.mStates = list;
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_state, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {
        holder.bindData(mStates.get(position));
    }

    @Override
    public int getItemCount() {
        return mStates == null ? 0 : mStates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private TextView mTextSize;

        public ViewHolder(View v) {
            super(v);
            mTextName = v.findViewById(R.id.text_state);
            mTextSize = v.findViewById(R.id.size_state);
        }

        public void bindData(State state) {
            if (state == null) return;
            if (state.getCities() == null) return;
            mTextSize.setText(state.getCities().size() + "");
            mTextName.setText(state.getName());
        }
    }

    public List<State> getStates() {
        return mStates;
    }

    public void setStates(List<State> states) {
        mStates = states;
    }
}
