package nl.intratuin.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import nl.intratuin.R;
import nl.intratuin.db.DBHelper;
import nl.intratuin.db.contract.ProductContract;
import nl.intratuin.dto.CachedProduct;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private List<CachedProduct> cachedProductList;
    private OnItemClickListener listener;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public interface OnItemClickListener {
        void onItemClick(CachedProduct cachedProduct);
    }

    public MovieAdapter(Context context, List<CachedProduct> movies, OnItemClickListener listener) {
        this.context = context;
        this.cachedProductList = movies;
        this.listener = listener;

        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cached_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindMovie(cachedProductList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cachedProductList.size();
    }

    public void remove(int position) {
        CachedProduct product = cachedProductList.get(position);
        sqLiteDatabase.delete(ProductContract.ProductEntity.TABLE_NAME,
                ProductContract.ProductEntity.PRODUCT_ID + "= ?", new String[]{"" + product.getProductId()});
        sqLiteDatabase.close();
        cachedProductList.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(int firstPosition, int secondPosition) {
        Collections.swap(cachedProductList, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName;
        private TextView tvProductPrice;
        private TextView currency_eur;
        private TextView tvDate;
        private ImageView ivProductImage;

        public ViewHolder(View v) {
            super(v);
            tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            ivProductImage = (ImageView) v.findViewById(R.id.ivProductImage);
            tvProductPrice = (TextView) v.findViewById(R.id.tvProductPrice);
            currency_eur = (TextView) v.findViewById(R.id.currency_eur);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
        }

        public void bindMovie(final CachedProduct cachedProduct, final OnItemClickListener listener) {
            this.tvProductName.setText(cachedProduct.getProductName());
            this.tvProductPrice.setText("" + cachedProduct.getProductPrice());
            this.tvDate.setText(cachedProduct.getCachedDate());
            Picasso.with(context)
                    .load(cachedProduct.getProductImage())
                    .resize(100, 100)
                    .centerCrop()
                    .into(this.ivProductImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(cachedProduct);
                }
            });
        }
    }
}