package thaihn.android.architecturecomponent.screen.city;

import java.util.List;
import thaihn.android.architecturecomponent.data.model.State;
import thaihn.android.architecturecomponent.screen.BasePresenter;
import thaihn.android.architecturecomponent.screen.BaseView;

public interface StateContract {

    interface View extends BaseView {

        void showCitiesSuccess(List<State> list);

        void showCitiesError(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData();

        void createData();
    }
}
