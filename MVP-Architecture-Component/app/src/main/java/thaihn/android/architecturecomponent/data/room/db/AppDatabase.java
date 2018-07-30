package thaihn.android.architecturecomponent.data.room.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import thaihn.android.architecturecomponent.data.model.City;
import thaihn.android.architecturecomponent.data.model.Product;
import thaihn.android.architecturecomponent.data.model.State;
import thaihn.android.architecturecomponent.data.room.dao.ProductDao;
import thaihn.android.architecturecomponent.data.room.dao.StateDao;
import thaihn.android.architecturecomponent.utils.DateTypeConverter;

@Database(entities = { Product.class, City.class, State.class }, version = 2)
@TypeConverters({ DateTypeConverter.class })
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "ProductDatabase";

    private static AppDatabase mInstance;

    public abstract ProductDao getProductDao();

    public abstract StateDao getStateDao();

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }

    public void removeInstance() {
        mInstance = null;
    }
}
