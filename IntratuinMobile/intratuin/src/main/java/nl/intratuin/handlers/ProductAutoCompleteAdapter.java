package nl.intratuin.handlers;

import android.content.Context;
import android.os.AsyncTask;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

import nl.intratuin.R;
import nl.intratuin.dto.Product;
import nl.intratuin.net.RequestResponseGET;


public class ProductAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<Product> resultSearch;
    private Filter filter;


    public ProductAutoCompleteAdapter(Context context) {
        this.context = context;
        resultSearch = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return resultSearch.size();
    }

    @Override
    public Object getItem(int position) {
        return resultSearch.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SearchFilter();
        }
        return filter;
    }

    private class SearchFilter extends android.widget.Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //List<Product> list = listProduct;
            FilterResults filterResults = new FilterResults();

            if (constraint != null) {
                List<Product> retList = findProducts(constraint.toString());
                filterResults.values = retList;
                filterResults.count = retList.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //searchAdapter.clear();
            if (results.count > 0) {
                resultSearch = (List<Product>) results.values;
            }
        }

        private List<Product> findProducts(String searchQuery) {
            List<Product> productSearchResult = new ArrayList<>();
            String searchUri = "http://128.0.169.5:8888/Intratuin/product/search/{name}";

            AsyncTask<String, Void, List<Product>> productFilterResult =
                    new RequestResponseGET(searchUri, 1, Product[].class).execute(searchQuery);
            try {
                productSearchResult = productFilterResult.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return productSearchResult;
        }
    }

    private static class ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView currency_eur;
        ImageView productImage;

        ViewHolder(View v) {
            productName = (TextView) v.findViewById(R.id.productName);
            productImage = (ImageView) v.findViewById(R.id.productImage);
            productPrice = (TextView) v.findViewById(R.id.productPrice);
            currency_eur = (TextView) v.findViewById(R.id.currency_eur);
        }
    }
}

