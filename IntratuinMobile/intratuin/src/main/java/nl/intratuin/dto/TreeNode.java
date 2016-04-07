package nl.intratuin.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.intratuin.R;

public class TreeNode implements Serializable {
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

}
