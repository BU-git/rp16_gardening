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
import nl.intratuin.dto.Product;

/**
 * Class {@code ProductListAdapter} is used for creating list of products.
 *
 * @see BaseAdapter
 */
public class ProductListAdapter extends BaseAdapter {
    private List<Product> productList;
    private LayoutInflater inflater;
    private Context context;

    /**
     * Instantiates a new Product list adapter.
     *
     * @param context     the context
     * @param productList the product list
     */
    public ProductListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        inflater = LayoutInflater.from(context);
    }

    /**
     * Get count of {@code Product}
     *
     * @return count of {@code Product}
     * @see BaseAdapter
     */
    @Override
    public int getCount() {
        return productList.size();
    }

    /**
     * Get a specific {@code Product}
     *
     * @param position position in array
     * @return a specific {@code Product}
     * @see BaseAdapter
     */
    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    /**
     * Return {@code Product} id
     *
     * @param position
     * @return {@code Product} id
     * @see BaseAdapter
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the {@code Product} at the specified position, with some image changes
     *
     * @param position    specific position
     * @param convertView the old view to reuse
     * @param parent      the parent to attach
     * @return View with specified position
     * @see BaseAdapter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_product_list_category, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Product product = (Product) this.getItem(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText("" + product.getProductPrice());
        Picasso.with(context)
                .load(product.getProductImage())
                .resize(100, 100)
                .centerCrop()
                .into(holder.productImage);
        return convertView;
    }

    /**
     * Class for simplify holding a product view
     */
    //let's make ViewHolder as a usual class
    private static class ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private TextView currency_eur;
        private ImageView productImage;

        /**
         * Instantiates a new View holder.
         *
         * @param v the view
         */
        ViewHolder(View v) {
            productName = (TextView) v.findViewById(R.id.productName__byCategory);
            productImage = (ImageView) v.findViewById(R.id.productImage_byCategory);
            productPrice = (TextView) v.findViewById(R.id.productPrice_byCategory);
            currency_eur = (TextView) v.findViewById(R.id.currency_eur_byCategory);
        }
    }
}
