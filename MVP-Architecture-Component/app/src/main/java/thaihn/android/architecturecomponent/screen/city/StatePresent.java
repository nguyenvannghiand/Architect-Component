package thaihn.android.architecturecomponent.screen.city;

import java.util.ArrayList;
import java.util.List;
import thaihn.android.architecturecomponent.data.model.City;
import thaihn.android.architecturecomponent.data.model.State;
import thaihn.android.architecturecomponent.data.viewmodel.StateViewModel;

public class StatePresent implements StateContract.Presenter {

    private StateContract.View mView;
    private StateViewModel mStateViewModel;

    public StatePresent(StateViewModel model) {
        this.mStateViewModel = model;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void createData() {
        createDataDemo();
    }

    @Override
    public void setView(StateContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mStateViewModel = null;
    }

    private void createDataDemo() {
        for (int i = 1; i <= 5; i++) {
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
    }
}
