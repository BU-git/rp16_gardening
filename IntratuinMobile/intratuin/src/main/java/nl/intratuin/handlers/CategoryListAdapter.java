package nl.intratuin.handlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nl.intratuin.R;
import nl.intratuin.dto.TreeNode;

public class CategoryListAdapter extends BaseAdapter {
    private final Context context;
    private List<TreeNode> categoryList;

    public CategoryListAdapter(Context context, List<TreeNode> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_subcategory, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TreeNode category = (TreeNode) this.getItem(position);
        holder.categoryName.setText(category.getName());
        return convertView;
    }

    private static class ViewHolder {
        private TextView categoryName;

        ViewHolder(View v) {
            categoryName = (TextView) v.findViewById(R.id.tvSubCategory);
        }
    }
}
