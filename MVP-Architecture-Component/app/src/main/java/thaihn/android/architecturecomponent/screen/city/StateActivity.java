package thaihn.android.architecturecomponent.screen.city;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import thaihn.android.architecturecomponent.R;
import thaihn.android.architecturecomponent.data.model.City;
import thaihn.android.architecturecomponent.data.model.State;
import thaihn.android.architecturecomponent.data.viewmodel.StateViewModel;
import thaihn.android.architecturecomponent.screen.BaseActivity;
import thaihn.android.architecturecomponent.utils.ToastUtils;

public class StateActivity extends BaseActivity
        implements StateContract.View, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    private StateAdapter mStateAdapter;
    private StateContract.Presenter mPresenter;
    private StateViewModel mStateViewModel;
    private List<State> mStates;

    @Override

    protected int getLayoutResources() {
        return R.layout.activity_city;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recycle_state);
        mFloatingActionButton = findViewById(R.id.fab_add);

        mFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add: {
                int size = mStates.size();
                System.out.println("TTT size " + size);
                addData(size++);
                break;
            }
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mStates = new ArrayList<>();
        mStateViewModel = ViewModelProviders.of(this).get(StateViewModel.class);
        mPresenter = new StatePresent(mStateViewModel);
        mPresenter.setView(this);
        configRecycle(this);
        mStateViewModel.getStates().observe(this, new Observer<List<State>>() {
            @Override
            public void onChanged(@Nullable List<State> states) {
                showCitiesSuccess(states);
            }
        });
    }

    @Override
    public void showCitiesSuccess(List<State> list) {
        if (list.size() == 0) {
            mPresenter.createData();
            return;
        }
        mStates = list;
        mStateAdapter.setStates(list);
        mStateAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCitiesError(String message) {
        ToastUtils.quickToast(this, message);
    }

    private void addData(int i) {
        State state = new State();
        state.setId(i);
        state.setName("VN " + i);

        List<City> cities = new ArrayList<>();
        for (int j = 1; j <= 10; j++) {
            City city = new City();
            city.setId(j);
            city.setStateId(i);
            city.setName("HN " + j);
            cities.add(city);
        }
        state.setCities(cities);
        mStateViewModel.saveState(state);
    }

    private void configRecycle(Context context) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mStateAdapter = new StateAdapter(new ArrayList<State>());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mStateAdapter);
        mStateAdapter.notifyDataSetChanged();
    }
}
