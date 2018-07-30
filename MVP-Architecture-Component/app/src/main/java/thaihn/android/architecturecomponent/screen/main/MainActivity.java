package thaihn.android.architecturecomponent.screen.main;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import thaihn.android.architecturecomponent.MyApplication;
import thaihn.android.architecturecomponent.R;
import thaihn.android.architecturecomponent.data.model.Product;
import thaihn.android.architecturecomponent.screen.BaseActivity;
import thaihn.android.architecturecomponent.screen.city.StateActivity;
import thaihn.android.architecturecomponent.utils.ToastUtils;

public class MainActivity extends BaseActivity implements MainContract.View, View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FloatingActionButton mActionButton;
    private Button mButtonState;

    private MainAdapter mMainAdapter;
    private MainContract.Presenter mPresenter;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recycle);
        mActionButton = findViewById(R.id.fab_add);
        mButtonState = findViewById(R.id.button_state);

        mActionButton.setOnClickListener(this);
        mButtonState.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new MainPresenter(this);
        mPresenter.setView(this);
        configRecycle(this);
        loadData();
    }

    @Override
    public void updateProductSuccess(List<Product> list) {
        mMainAdapter.setProducts(list);
        mMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateProductFail(String message) {
        ToastUtils.quickToast(this, message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add: {
                mPresenter.addProduct();
                break;
            }
            case R.id.button_state: {
                Intent intent = new Intent(this, StateActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void loadData() {
        MyApplication.getInstance()
                .getAppDatabase()
                .getProductDao()
                .getAll()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable List<Product> products) {
                        if (products.size() == 0) {
                            mPresenter.createData();
                            return;
                        }
                        mMainAdapter.setProducts(products);
                        mMainAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void configRecycle(Context context) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        mRecyclerView.setHasFixedSize(true);
        mMainAdapter = new MainAdapter(new ArrayList<Product>());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMainAdapter);
        mMainAdapter.notifyDataSetChanged();
    }
}
