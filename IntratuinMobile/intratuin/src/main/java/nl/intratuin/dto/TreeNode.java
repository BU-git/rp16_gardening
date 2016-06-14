package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import nl.intratuin.R;

/**
 * Class {@code TreeNode} is for creating hierarchy.
 * This DTO able to be written to and restored from a {@code Parcel}.
 *
 * @see Parcel
 * @see Parcelable
 */
public class TreeNode implements Parcelable {
    private int id;
    private String name;
    private List<TreeNode> children;
    //variable not used
    private int iconResource;

    /**
     * Instantiates a new Tree node.
     *
     * @param id   the id
     * @param name the name
     */
    public TreeNode(int id, String name) {
        this.id = id;
        this.name = name;
        children = new ArrayList<>();
    }

    private TreeNode(Parcel in) {
        id = in.readInt();
        name = in.readString();
        children = new ArrayList<>();
        in.readList(children, getClass().getClassLoader());
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation
     *
     * @return some bitmask
     * @see Parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten {@code Category} object in to a {@code Parcel}
     *
     * @param out
     * @param flags
     * @see Parcel
     * @see Parcelable
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeList(children);
    }

    /**
     * The constant CREATOR generates instances of {@code Category} class from a Parcel
     *
     * @see Parcel
     * @see Parcelable
     */
    public static final Parcelable.Creator<TreeNode> CREATOR = new Parcelable.Creator<TreeNode>() {
        public TreeNode createFromParcel(Parcel in) {
            return new TreeNode(in);
        }

        public TreeNode[] newArray(int size) {
            return new TreeNode[size];
        }
    };

    /**
     * Gets icon resource.
     *
     * @return the icon resource
     */
    public int getIconResource() {
        if (children.size() > 0)
            return R.drawable.expand;
        return 0;
    }

    /**
     * Sets icon resource.
     *
     * @param iconResource the icon resource
     */
    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets children.
     *
     * @return the children
     */
    public List<TreeNode> getChildren() {
        return children;
    }


    /**
     * Sets children.
     *
     * @param children the children
     */
    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
