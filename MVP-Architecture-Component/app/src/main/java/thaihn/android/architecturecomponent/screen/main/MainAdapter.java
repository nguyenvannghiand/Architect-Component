package thaihn.android.architecturecomponent.screen.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import thaihn.android.architecturecomponent.R;
import thaihn.android.architecturecomponent.data.model.Product;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Product> mProducts;

    public MainAdapter(List<Product> list) {
        this.mProducts = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name;

        public ViewHolder(View v) {
            super(v);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
        }

        public void bind(Product product) {
            String price = itemView.getContext()
                    .getString(R.string.price_format, String.valueOf(product.getPrice()));
            name.setText(product.getName() + "-" + price);
            Glide.with(itemView.getContext()).load(product.getImageUrl()).into(image);
        }
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }
}
