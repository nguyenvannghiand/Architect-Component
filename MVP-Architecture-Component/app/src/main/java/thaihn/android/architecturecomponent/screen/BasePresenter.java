package thaihn.android.architecturecomponent.screen;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
