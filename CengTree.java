import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root = null;

    }


    public void addVideo(CengVideo video)
    {
        if (root == null){
            root = new CengTreeNodeLeaf(null);
            ((CengTreeNodeLeaf) root).insertFirst(video);
        }
        else{
            CengTreeNodeInternal ofNode = addHelper(root, video.getKey(), video);
            if (ofNode != null) //there is overflow to root node
                root = new CengTreeNodeInternal(ofNode.getKeys(), ofNode.getAllChildren(), null);
        }
    }

    public ArrayList<CengTreeNode> searchVideo(Integer key)
    {
        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.
        ArrayList<CengTreeNode> retArray = new ArrayList<CengTreeNode>();
        CengTreeNode current = root;
        ArrayList<Integer> keys;
        ArrayList<CengTreeNode> children;
        int keysize;
        int i;
        while (current.getType() != CengNodeType.Leaf){
            retArray.add(current);
            keys = ((CengTreeNodeInternal) current).getKeys();
            children = ((CengTreeNodeInternal) current).getAllChildren();
            keysize = keys.size();
            for (i = 0; i < keysize; i++){
                if (key < keys.get(i)) break;
            }
            current = children.get(i);
        }
        ArrayList<CengVideo> videos = ((CengTreeNodeLeaf) current).getVideos();
        keysize = videos.size();
        for (i = 0; i < keysize; i++){
            if (key.equals(videos.get(i).getKey())){
                retArray.add(current);
                String tabs = "";
                for (CengTreeNode j: retArray){
                    if (j.getType() == CengNodeType.Leaf){
                        CengVideo cV = videos.get(i);
                        System.out.println(tabs + "<record>" + cV.getKey() + "|" + cV.getVideoTitle() + "|" + cV.getChannelName() + "|" + cV.getCategory() + "</record>");
                    }
                    else {
                        ArrayList<Integer> keyList = ((CengTreeNodeInternal)j).getKeys();

                        System.out.println(tabs + "<index>");
                        for (Integer iK : keyList){
                            System.out.println(tabs + iK);
                        }
                        System.out.println(tabs + "</index>");
                    }
                    tabs = tabs + "\t";
                }
                return retArray;
            }
        }
        System.out.println("Could not find " + key + ".");
        return null;
    }

    public void printTree()
    {
        // TODO: Print the whole tree to console
        printHelper(root, "");
    }

    // Any extra functions...
    // returns new parent to the leaves
    private void printHelper( CengTreeNode current, String tabs){
        if (current.getType() == CengNodeType.Leaf){
            ArrayList<CengVideo> videos = ((CengTreeNodeLeaf)current).getVideos();

            System.out.println(tabs + "<data>");
            for (CengVideo i : videos){
                System.out.println(tabs + "<record>" + i.getKey() + "|" + i.getVideoTitle() + "|" + i.getChannelName() + "|" + i.getCategory() + "</record>");
            }
            System.out.println(tabs + "</data>");
        }
        else {
            ArrayList<Integer> keys = ((CengTreeNodeInternal)current).getKeys();
            ArrayList<CengTreeNode> children = ((CengTreeNodeInternal)current).getAllChildren();

            System.out.println(tabs + "<index>");
            for (Integer i : keys){
                System.out.println(tabs + i);
            }
            System.out.println(tabs + "</index>");
            tabs = tabs + "\t";
            for (CengTreeNode i : children){
                printHelper(i , tabs);
            }

        }
    }

    private CengTreeNodeInternal splitLeaf(CengTreeNodeLeaf leaf){
        ArrayList<CengVideo> leafvideos = leaf.getVideos();
        int order = leaf.getOrder();
        Integer splitAtKey = leaf.videoKeyAtIndex(order);
        CengTreeNodeInternal newParent = new CengTreeNodeInternal(null);

        ArrayList<CengVideo> rightVideos = new ArrayList<CengVideo>(leafvideos.subList(order, leafvideos.size()));
        CengTreeNodeLeaf rightLeaf = new CengTreeNodeLeaf(rightVideos, leaf.getParent());

        leafvideos.subList(order, leafvideos.size()).clear();

        newParent.insertFirst(splitAtKey, leaf, rightLeaf);

        return newParent;
    }

    private CengTreeNodeInternal splitInternal(CengTreeNodeInternal node){
        ArrayList<CengTreeNode> children = node.getAllChildren();
        ArrayList<Integer> keys = node.getKeys();
        int order = node.getOrder();
        int splitAtKey = keys.get(order);
        CengTreeNodeInternal newParent = new CengTreeNodeInternal(null);

        ArrayList<Integer> rightKeys = new ArrayList<Integer>(keys.subList(order + 1, keys.size()));
        ArrayList<CengTreeNode> rightChildren = new ArrayList<CengTreeNode>(children.subList(order + 1, children.size()));
        CengTreeNodeInternal rightInternal = new CengTreeNodeInternal(rightKeys, rightChildren, node.getParent());

        keys.subList(order, keys.size()).clear();
        children.subList(order + 1, children.size()).clear();

        newParent.insertFirst(splitAtKey, node, rightInternal);
        return newParent;
    }

    private CengTreeNodeInternal addHelper( CengTreeNode root, Integer key, CengVideo video){
        CengTreeNodeInternal ofNode = null;

        if (root.getType() == CengNodeType.Leaf){
            ((CengTreeNodeLeaf) root).insertSorted(video);
            if (((CengTreeNodeLeaf) root).isFull()) return splitLeaf((CengTreeNodeLeaf) root);
        }
        else {
            CengTreeNodeInternal current = (CengTreeNodeInternal) root;
            ArrayList<Integer> keys = current.getKeys();
            ArrayList<CengTreeNode> children = current.getAllChildren();
            //finding node to insert
            if (key >= keys.get(keys.size() - 1)) ofNode = addHelper(children.get(children.size() - 1), key, video);
            else {
                for (int i = 0; i < keys.size(); i++){
                    if (keys.get(i) > key){
                        ofNode = addHelper(children.get(i), key, video);
                        break;
                    }
                }
            }

        }

        if (ofNode != null && root.getType() == CengNodeType.Internal){
            CengTreeNodeInternal current = (CengTreeNodeInternal) root;
            Integer keyOf = ofNode.getKeys().get(0);
            CengTreeNode childLeft = ofNode.getAllChildren().get(0);
            CengTreeNode childRight = ofNode.getAllChildren().get(1);
            current.insertSorted(keyOf, childLeft, childRight);
            if (current.isFull()) return splitInternal(current);
            else return null;
        }

        return ofNode;
    }
}
