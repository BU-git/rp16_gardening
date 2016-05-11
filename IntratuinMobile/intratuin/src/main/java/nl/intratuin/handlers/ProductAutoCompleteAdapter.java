package nl.intratuin.handlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.intratuin.App;
import nl.intratuin.R;
import nl.intratuin.dto.Product;
import nl.intratuin.manager.RequestResponseManager;
import nl.intratuin.net.UriConstructor;


/**
 * Class {@code ProductAutoCompleteAdapter} is helping construct correct AutoCompleteTextView.
 *
 * @see BaseAdapter
 * @see Filterable
 */
public class ProductAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<Product> resultSearch;
    private Filter filter;


    /**
     * Instantiates a new Product auto complete adapter.
     *
     * @param context the context
     */
    public ProductAutoCompleteAdapter(Context context) {
        this.context = context;
        resultSearch = new ArrayList<>();
    }

    /**
     * Get count of {@code Product}
     *
     * @return count of {@code Product}
     * @see BaseAdapter
     */
    @Override
    public int getCount() {
        return resultSearch.size();
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
        return resultSearch.get(position);
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
        return resultSearch.get(position).getProductId();
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.dropdown_search_product, parent, false);
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
                .resize(80, 80)
                .centerCrop()
                .into(holder.productImage);
        return convertView;
    }

    /**
     * Return or create(if not exist) a search filter
     *
     * @return {@code Filter}
     */
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SearchFilter();
        }
        return filter;
    }

    /**
     * Class is helping to search products in AutoCompleteTextView
     *
     * @see android.widget.Filter
     */
    private class SearchFilter extends android.widget.Filter {

        /**
         * Filtering products according constraint
         *
         * @param constraint
         * @return result of filtering
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null) {
                List<Product> retList = findProducts(context, constraint.toString());
                filterResults.values = retList;
                filterResults.count = retList.size();
            }
            return filterResults;
        }

        /**
         * Publish the filtering products
         *
         * @param constraint
         * @param results    of filtering
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                resultSearch = (List<Product>) results.values;
            }
        }

        /**
         * Retrieve products by name
         *
         * @param context
         * @param name
         * @return list of products by name
         */
        private List<Product> findProducts(Context context, String name) {
            String searchUri = new UriConstructor(context).makeURI("search").toString() + "{name}";
            RequestResponseManager<Product[]> managerLoader = new RequestResponseManager(context, App.getShowManager(), Product[].class);
            return Arrays.asList(managerLoader.loaderFromWebService(searchUri, name));
        }
    }

    /**
     * Class for simplify holding a product view
     */
    private static class ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private ImageView productImage;
        private TextView currency_eur;

        /**
         * Instantiates a new View holder.
         *
         * @param v the current view
         */
        ViewHolder(View v) {
            productName = (TextView) v.findViewById(R.id.productName);
            productImage = (ImageView) v.findViewById(R.id.productImage);
            productPrice = (TextView) v.findViewById(R.id.productPrice);
            currency_eur = (TextView) v.findViewById(R.id.currency_eur);
        }
    }
}
