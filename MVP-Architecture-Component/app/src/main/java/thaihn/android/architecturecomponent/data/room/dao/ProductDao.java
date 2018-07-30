package thaihn.android.architecturecomponent.data.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import thaihn.android.architecturecomponent.data.model.Product;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAll();

    @Query("SELECT * FROM product WHERE name LIKE :name LIMIT 1")
    LiveData<Product> findByName(String name);

    @Insert
    void insertAll(List<Product> products);

    @Insert
    void insert(Product... products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);
}
