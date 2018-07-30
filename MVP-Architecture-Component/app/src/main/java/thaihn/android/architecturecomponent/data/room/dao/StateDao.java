package thaihn.android.architecturecomponent.data.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import thaihn.android.architecturecomponent.data.model.City;
import thaihn.android.architecturecomponent.data.model.State;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StateDao {

    @Query("SELECT * FROM state WHERE id = :id")
    LiveData<State> getState(int id);

    @Query("SELECT * FROM state")
    LiveData<List<State>> getStates();

    @Query("SELECT * FROM city WHERE stateId = :stateId")
    LiveData<List<City>> getCities(int stateId);

    @Insert(onConflict = REPLACE)
    void saveState(State state);

    @Insert(onConflict = REPLACE)
    void saveStates(List<State> states);

    @Insert(onConflict = REPLACE)
    void saveCities(List<City> cities);
}
