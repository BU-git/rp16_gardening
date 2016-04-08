package nl.intratuin.handlers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.intratuin.ProductListByCategoryActivity;
import nl.intratuin.R;
import nl.intratuin.SearchActivity;
import nl.intratuin.dto.TreeNode;

public class HierarchyCategoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<Indent> hierarchyArray;
    List<TreeNode> originalTreeNode;
    List<TreeNode> openTreeNode;
    Context context;

    public HierarchyCategoryAdapter(Context context, List<TreeNode> originalTreeNode) {
        this.context = context;
        this.originalTreeNode = originalTreeNode;
        inflater = LayoutInflater.from(context);
        hierarchyArray = new ArrayList<>();
        openTreeNode = new ArrayList<>();

        generateHierarchy();
    }

    @Override
    public int getCount() {
        return hierarchyArray.size();
    }

    @Override
    public Object getItem(int position) {
        return hierarchyArray.get(position).treeNode;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.category_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Indent indentTreeNode = hierarchyArray.get(position);

        holder.categoryName.setText(indentTreeNode.treeNode.getName());
        holder.iconExpand.setImageResource(indentTreeNode.treeNode.getIconResource());
        holder.iconForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent productListOfCategoryIntent = new Intent(context, ProductListByCategoryActivity.class);
                productListOfCategoryIntent.putExtra(SearchActivity.TREENODE, indentTreeNode.treeNode);
                context.startActivity(productListOfCategoryIntent);
            }
        });

        holder.categoryName.setPadding((indentTreeNode.level + 1) * 50, 0, 0, 0);
        holder.iconExpand.setPadding((indentTreeNode.level) * 50, 0, 0, 0);
        return convertView;
    }

    private void generateHierarchy() {
        hierarchyArray.clear();
        generateList(originalTreeNode, 0);
    }

    private void generateList(List<TreeNode> originalTreeNode, int level) {
        for(TreeNode treeNode : originalTreeNode) {
            hierarchyArray.add(new Indent(treeNode, level));
            if(openTreeNode.contains(treeNode))
                generateList(treeNode.getChildren(), level + 1);
        }
    }

    public void clickOnCategory (int position) {

        TreeNode treeNode = hierarchyArray.get(position).treeNode;
        if (!openTreeNode.remove(treeNode))
            openTreeNode.add(treeNode);

        generateHierarchy();
        notifyDataSetChanged();
    }

    private class Indent {
        TreeNode treeNode;
        int level;

        Indent(TreeNode treeNode, int level) {
            this.treeNode = treeNode;
            this.level = level;
        }
    }

    private static class ViewHolder {
        TextView categoryName;
        ImageView iconExpand;
        ImageView iconForward;

        ViewHolder(View view) {
            categoryName = (TextView) view.findViewById(R.id.twCategoryName);
            iconExpand = (ImageView) view.findViewById(R.id.iconExpand);
            iconForward = (ImageView) view.findViewById(R.id.iconForward);
        }
    }
}
