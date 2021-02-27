import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengVideo> videos;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);
        videos = new ArrayList<CengVideo>();
        type = CengNodeType.Leaf;

        // TODO: Extra initializations
    }


    // GUI Methods - Do not modify
    public int videoCount()
    {
        return videos.size();
    }
    public Integer videoKeyAtIndex(Integer index)
    {
        if(index >= this.videoCount()) {
            return -1;
        } else {
            CengVideo video = this.videos.get(index);

            return video.getKey();
        }
    }

    // Extra Functions

    public CengTreeNodeLeaf(ArrayList<CengVideo> newVideos, CengTreeNode parent){
        super(parent);
        type = CengNodeType.Leaf;
        videos = newVideos;
    }
    public void insertFirst(CengVideo firstVal){
        videos.add(firstVal);
    }

    public void insertSorted(CengVideo newVideo){
        Integer newKey = newVideo.getKey();
        int nodesize = videos.size();
        if (newKey < videos.get(0).getKey()){
            videos.add(0, newVideo);
        }
        else if (newKey > videos.get(nodesize - 1).getKey()) {
            videos.add(newVideo);
        }
        else {
            for (int i = 0; i < nodesize; i++){
                if (newKey < videos.get(i).getKey()) {
                    videos.add(i, newVideo);
                    break;
                }
            }
        }
    }

    public ArrayList<CengVideo> getVideos(){
        return videos;
    }

    public CengVideo getVideoKey(Integer key){
        for (CengVideo i : videos){
            if (i.getKey().equals(key)) return i;
        }
        return new CengVideo(-1,"","","");
    }

    public boolean isFull(){
        return videos.size() > 2 * order;
    }

    public int getOrder() {return order; }



}
