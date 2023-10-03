import java.util.Comparator;
import java.util.Iterator;

public class NLNode<T> {
    // The parent node of this node
    private NLNode<T> parent;
    // A list of children nodes
    private ListNodes<NLNode<T>> children;
    // The data stored in this node
    private T data;

    // Default constructor
    public NLNode() {
        parent = null;
        data = null;
        children = new ListNodes<NLNode<T>>();
    }

    // Constructor with data and parent
    public NLNode(T d, NLNode<T> p) {
        parent = p;
        data = d;
        children = new ListNodes<NLNode<T>>();
    }

    // Set the parent of this node
    public void setParent(NLNode<T> p) {
        parent = p;
    }

    // Get the parent of this node
    public NLNode<T> getParent() {
        return parent;
    }

    // Add a child node to this node
    public void addChild(NLNode<T> newChild) {
        newChild.setParent(this);
        children.add(newChild);
    }

    // Get an iterator for the children of this node
    public Iterator<NLNode<T>> getChildren() {
        return children.getList();
    }

    // Get an iterator for the children of this node, sorted by a given comparator
    public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
        return children.sortedList(sorter);
    }

    // Get the data stored in this node
    public T getData() {
        return data;
    }

    // Set the data stored in this node
    public void setData(T d) {
        data = d;
    }
}
