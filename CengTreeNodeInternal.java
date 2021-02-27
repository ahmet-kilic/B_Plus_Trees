import java.lang.reflect.Array;
import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);
        keys = new ArrayList<Integer>();
        children = new ArrayList<CengTreeNode>();
        type = CengNodeType.Internal;

        // TODO: Extra initializations, if necessary.
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions
    public CengTreeNodeInternal(ArrayList<Integer> newKeys, ArrayList<CengTreeNode> newChildren, CengTreeNode parent){
        super(parent);
        type = CengNodeType.Internal;
        keys = newKeys;
        children = newChildren;
        for (CengTreeNode child: newChildren) child.setParent(this);

    }
    public void insertFirst(Integer key, CengTreeNode l, CengTreeNode r){
        keys.add(key);
        children.add(l);
        children.add(r);
    }
    public void insertSorted(Integer key, CengTreeNode l, CengTreeNode r){
        if (key < keys.get(0)) {
            keys.add(0, key);
            children.add(1,r);
        } else if (key > keys.get(keys.size() - 1)) {
            keys.add(key);
            children.add(r);
        } else {
            for (int i = 1; i < keys.size(); i++) {
                if ( keys.get(i) > key) {
                    keys.add(i, key);
                    children.remove(i);
                    children.add(i, l);
                    children.add(i + 1, r);
                    break;
                }
            }
        }
    }
    public boolean isFull(){
        return keys.size() > 2 * order;
    }
    public ArrayList<Integer> getKeys(){
        return keys;
    }

    public int getOrder() {return order; }

}
