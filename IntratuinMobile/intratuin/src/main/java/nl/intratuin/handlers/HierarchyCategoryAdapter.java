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


/**
 * Class {@code HierarchyCategoryAdapter} is used for creating hierarchy of categories.
 *
 * @see BaseAdapter
 */
//maybe we can optimize this class
public class HierarchyCategoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Indent> hierarchyArray;
    private List<TreeNode> originalTreeNode;
    private List<TreeNode> openTreeNode;
    private Context context;

    /**
     * Instantiates a new Hierarchy category adapter and generating his hierarchy
     *
     * @param context          the context
     * @param originalTreeNode the original tree node
     */
    public HierarchyCategoryAdapter(Context context, List<TreeNode> originalTreeNode) {
        this.context = context;
        this.originalTreeNode = originalTreeNode;
        inflater = LayoutInflater.from(context);
        hierarchyArray = new ArrayList<>();
        openTreeNode = new ArrayList<>();

        generateHierarchy();
    }

    /**
     * Get count of {@code Indent}
     *
     * @return count of {@code Indent}
     * @see BaseAdapter
     */
    @Override
    public int getCount() {
        return hierarchyArray.size();
    }

    /**
     * Get a specific {@code Indent} as {@code TreeNode}
     *
     * @param position position in array
     * @return a specific Indent as TreeNode
     * @see BaseAdapter
     */
    @Override
    public Object getItem(int position) {
        return hierarchyArray.get(position).treeNode;
    }

    /**
     * Return position
     *
     * @param position
     * @return position
     * @see BaseAdapter
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the {@code hierarchyArray}
     *
     * @param position    specific position
     * @param convertView the old view to reuse
     * @param parent      the parent to attach
     * @return View with specified position
     * @see BaseAdapter
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
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

    /**
     * Clear and generate a hierarchy
     */
    private void generateHierarchy() {
        hierarchyArray.clear();
        generateList(originalTreeNode, 0);
    }

    /**
     * Generate the category tree with subcategories
     *
     * @param originalTreeNode category tree as list
     * @param level            level of subcategories
     */
    //only recursive?(
    private void generateList(List<TreeNode> originalTreeNode, int level) {
        for (TreeNode treeNode : originalTreeNode) {
            hierarchyArray.add(new Indent(treeNode, level));
            if (openTreeNode.contains(treeNode))
                generateList(treeNode.getChildren(), level + 1);
        }
    }

    /**
     * Generate new category tree when click to open a new category
     *
     * @param position the position of subcategories
     */
    public void clickOnCategory(int position) {

        TreeNode treeNode = hierarchyArray.get(position).treeNode;
        if (!openTreeNode.remove(treeNode))
            openTreeNode.add(treeNode);

        generateHierarchy();
        notifyDataSetChanged();
    }

    /**
     * Class for simplify generating category tree
     */
    private class Indent {
        private TreeNode treeNode;
        private int level;

        /**
         * Instantiates a new Indent.
         *
         * @param treeNode the tree node
         * @param level    the level
         */
        Indent(TreeNode treeNode, int level) {
            this.treeNode = treeNode;
            this.level = level;
        }
    }

    /**
     * Class for simplify holding a category view
     */
    private static class ViewHolder {
        private TextView categoryName;
        private ImageView iconExpand;
        private ImageView iconForward;

        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        ViewHolder(View view) {
            categoryName = (TextView) view.findViewById(R.id.twCategoryName);
            iconExpand = (ImageView) view.findViewById(R.id.iconExpand);
            iconForward = (ImageView) view.findViewById(R.id.iconForward);
        }
    }
}
