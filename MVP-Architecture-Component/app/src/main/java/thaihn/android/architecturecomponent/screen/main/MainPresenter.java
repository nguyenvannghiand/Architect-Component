package thaihn.android.architecturecomponent.screen.main;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import thaihn.android.architecturecomponent.MyApplication;
import thaihn.android.architecturecomponent.R;
import thaihn.android.architecturecomponent.data.model.Product;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private Context mContext;

    public MainPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void createData() {
        List<Product> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName(mContext.getString(R.string.name_format, String.valueOf(i)));
            product.setImageUrl("https://picsum.photos/500/500?image=" + i);
            product.setPrice(i == 0 ? 50 : i * 100);
            list.add(product);
        }

        MyApplication.getInstance().getAppDatabase().getProductDao().insertAll(list);
    }

    @Override
    public void addProduct() {
        Product product = new Product();
        product.setName(mContext.getString(R.string.name_format, String.valueOf(10)));
        product.setImageUrl("https://picsum.photos/500/500?image=" + 10);
        product.setPrice(10 == 0 ? 50 : 10 * 100);
        MyApplication.getInstance().getAppDatabase().getProductDao().insert(product);
    }

    @Override
    public void setView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
