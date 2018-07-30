package thaihn.android.architecturecomponent.data.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;
import thaihn.android.architecturecomponent.MyApplication;
import thaihn.android.architecturecomponent.data.model.City;
import thaihn.android.architecturecomponent.data.model.State;
import thaihn.android.architecturecomponent.data.room.dao.StateDao;

public class StateViewModel extends AndroidViewModel {

    private StateDao mStateDao;

    public StateViewModel(@NonNull Application application) {
        super(application);
        mStateDao = MyApplication.getInstance().getAppDatabase().getStateDao();
    }

    public LiveData<State> getState(int id) {
        LiveData<State> liveState = mStateDao.getState(id);
        liveState = Transformations.switchMap(liveState, new Function<State, LiveData<State>>() {
            @Override
            public LiveData<State> apply(final State inputState) {
                LiveData<List<City>> liveCities = mStateDao.getCities(inputState.getId());
                LiveData<State> outputLive =
                        Transformations.map(liveCities, new Function<List<City>, State>() {
                            @Override
                            public State apply(List<City> input) {
                                inputState.setCities(input);
                                return inputState;
                            }
                        });
                return outputLive;
            }
        });
        return liveState;
    }

    public LiveData<List<State>> getStates() {
        LiveData<List<State>> statesLiveData = mStateDao.getStates();
        statesLiveData = Transformations.switchMap(statesLiveData,
                new Function<List<State>, LiveData<List<State>>>() {

                    @Override
                    public LiveData<List<State>> apply(final List<State> inputStates) {
                        final MediatorLiveData<List<State>> statesMediatorLiveData =
                                new MediatorLiveData<>();
                        for (final State state : inputStates) {
                            statesMediatorLiveData.addSource(mStateDao.getCities(state.getId()),
                                    new Observer<List<City>>() {

                                        @Override
                                        public void onChanged(@Nullable List<City> cities) {
                                            state.setCities(cities);
                                            statesMediatorLiveData.postValue(inputStates);
                                        }
                                    });
                        }
                        return statesMediatorLiveData;
                    }
                });
        return statesLiveData;
    }

    public void saveState(State state) {
        mStateDao.saveState(state);
        mStateDao.saveCities(state.getCities());
    }

    public void saveStates(List<State> states) {
        mStateDao.saveStates(states);
        for (State state : states) {
            mStateDao.saveCities(state.getCities());
        }
    }
}
