package nl.intratuin.handlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nl.intratuin.R;
import nl.intratuin.dto.Product;

public class OrderProductAdapter extends BaseAdapter {
    private List<Product> productList;
    private LayoutInflater inflater;
    private Context context;

    public OrderProductAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_product_list_category, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Product product = productList.get(position);

        holder.productName.setText(product.getProductName());
        holder.productPrice.setText("" + product.getProductPrice());
        Picasso.with(context)
                .load(product.getProductImage())
                .resize(50, 50)
                .centerCrop()
                .into(holder.productImage);
        return convertView;
    }


    private static class ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private TextView currency_eur;
        private ImageView productImage;

        ViewHolder(View v) {
            productName = (TextView) v.findViewById(R.id.productName__byCategory);
            productImage = (ImageView) v.findViewById(R.id.productImage_byCategory);
            productPrice = (TextView) v.findViewById(R.id.productPrice_byCategory);
            currency_eur = (TextView) v.findViewById(R.id.currency_eur_byCategory);
        }
    }
}
