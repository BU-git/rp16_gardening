package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.intratuin.R;

public class TreeNode implements Parcelable {
    private int id;
    private String name;
    private List<TreeNode> children;
    private int iconResource;

    public  TreeNode(int id, String name){
        this.id = id;
        this.name = name;
        children = new ArrayList<>();
    }

    public int getIconResource() {
        if(children.size() > 0)
            return R.drawable.expand;
        return 0;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeList(children);
    }

    public static final Parcelable.Creator<TreeNode> CREATOR = new Parcelable.Creator<TreeNode>(){
        public TreeNode createFromParcel(Parcel in){
            return new TreeNode(in);
        }
        public TreeNode[] newArray(int size){
            return new TreeNode[size];
        }
    };

    private TreeNode(Parcel in){
        id = in.readInt();
        name = in.readString();
        children = new ArrayList<>();
        in.readList(children, getClass().getClassLoader());
    }
}
