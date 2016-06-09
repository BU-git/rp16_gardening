package nl.intratuin.handlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nl.intratuin.R;
import nl.intratuin.dto.CachedProduct;

public class CachedProductListAdapter extends BaseAdapter {
    private List<CachedProduct> cachedProductList;
    private LayoutInflater inflater;
    private Context context;

    public CachedProductListAdapter(Context context, List<CachedProduct> cachedProductList) {
        this.context = context;
        this.cachedProductList = cachedProductList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cachedProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return cachedProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cachedProductList.get(position).getProductId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cached_product_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CachedProduct cachedProduct = cachedProductList.get(position);
        holder.tvProductName.setText(cachedProduct.getProductName());
        holder.tvProductPrice.setText("" + cachedProduct.getProductPrice());
        holder.tvDate.setText(cachedProduct.getCachedDate());
        Picasso.with(context)
                .load(cachedProduct.getProductImage())
                .resize(100, 100)
                .centerCrop()
                .into(holder.ivProductImage);
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvProductName;
        private TextView tvProductPrice;
        private TextView currency_eur;
        private TextView tvDate;
        private ImageView ivProductImage;

        ViewHolder(View v) {
            tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            ivProductImage = (ImageView) v.findViewById(R.id.ivProductImage);
            tvProductPrice = (TextView) v.findViewById(R.id.tvProductPrice);
            currency_eur = (TextView) v.findViewById(R.id.currency_eur);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
        }
    }
}
