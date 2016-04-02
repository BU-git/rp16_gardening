package nl.intratuin.handlers;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.intratuin.R;
import nl.intratuin.net.RequestResponseGET;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nl.intratuin.dto.Product;


public class ProductAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private final Context context;
    private List<Product> resultSearch;

    public ProductAutoCompleteAdapter(Context context) {
       this.context = context;
        resultSearch = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return resultSearch.size();
    }

    @Override
    public Product getItem(int position) {
        return resultSearch.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.dropdown_search_product, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = getItem(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText("" + product.getProductPrice());
        Picasso.with(context)
                .load(product.getProductImage())
                .resize(80, 80)
                .centerCrop()
                .into(holder.productImage);

        return  convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Product> products = findProducts(constraint.toString());
                    filterResults.values = products;
                    filterResults.count = products.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultSearch = (List<Product>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<Product> findProducts(String searchQuery) {
        List<Product> productSearchResult = new ArrayList<>();
        String searchUri = null;

        try {
            searchUri = new URL("http", "128.0.169.5", 8888, "/product/search?searchQuery={searchQuery}").toString();
        } catch (MalformedURLException  e) {
        }

        AsyncTask<String, Void, List<Product>> productFilterResult =
                new RequestResponseGET<String>(searchUri, 3,
                        ((FragmentActivity)context).getSupportFragmentManager()).execute(searchQuery);
        try {
            productSearchResult = productFilterResult.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return productSearchResult;
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
