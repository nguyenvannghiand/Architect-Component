package thaihn.android.architecturecomponent.screen.main;

import java.util.List;
import thaihn.android.architecturecomponent.data.model.Product;
import thaihn.android.architecturecomponent.screen.BasePresenter;
import thaihn.android.architecturecomponent.screen.BaseView;

public interface MainContract {

    interface View extends BaseView {

        void updateProductSuccess(List<Product> list);

        void updateProductFail(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void addProduct();

        void createData();
    }
}
