import java.util.ArrayList;
import java.util.Iterator;

public class FileStructure {
    // Root node of the file structure
    private NLNode<FileObject> root;

    // Constructor that initializes the root and builds the file structure
    public FileStructure(String fileObjectName) throws FileObjectException {
        FileObject fileObject = new FileObject(fileObjectName);
        root = new NLNode<>();
        root.setData(fileObject); // Set the root data
        if (fileObject.isDirectory()) {
            buildFileStructure(root);
        }
    }

    // Recursive method to build the file structure from the provided node
    private void buildFileStructure(NLNode<FileObject> node) {
        FileObject fileObject = node.getData();
        Iterator<FileObject> fileObjects = fileObject.directoryFiles();
        if (fileObjects != null) {
            while (fileObjects.hasNext()) {
                FileObject childFileObject = fileObjects.next();
                NLNode<FileObject> childNode = new NLNode<>();
                childNode.setData(childFileObject); // Set the child node data
                node.addChild(childNode);
                childNode.setParent(node);
                if (childFileObject.isDirectory()) {
                    buildFileStructure(childNode);
                }
            }
        }
    }

    // Get the root node of the file structure
    public NLNode<FileObject> getRoot() {
        return root;
    }

    // Get an iterator for files of a specific type
    public Iterator<String> filesOfType(String type) {
        ArrayList<String> files = new ArrayList<>();
        findFilesOfType(root, type, files);
        return files.iterator();
    }

    // Recursive method to find files of a specific type in the file structure
    private void findFilesOfType(NLNode<FileObject> node, String type, ArrayList<String> files) {
        FileObject fileObject = node.getData();
        if (fileObject.isFile() && fileObject.getLongName().endsWith(type)) {
            files.add(fileObject.getLongName());
        } else if (fileObject.isDirectory()) {
            Iterator<NLNode<FileObject>> children = node.getChildren();
            while (children.hasNext()) {
                findFilesOfType(children.next(), type, files);
            }
        }
    }

    // Find a file by name in the file structure
    public String findFile(String name) {
        return findFileInNode(root, name);
    }

    // Recursive method to find a file by name in the file structure
    private String findFileInNode(NLNode<FileObject> node, String name) {
        FileObject fileObject = node.getData();
        if (fileObject.isFile() && fileObject.getName().equals(name)) {
            return fileObject.getLongName();
        } else if (fileObject.isDirectory()) {
            Iterator<NLNode<FileObject>> children = node.getChildren();
            while (children.hasNext()) {
                String foundFile = findFileInNode(children.next(), name);
                if (!foundFile.isEmpty()) {
                    return foundFile;
                }
            }
        }
        return "";
    }
}
