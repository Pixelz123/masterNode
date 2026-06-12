package com.dfs.master_node.namespace;

import java.util.concurrent.ConcurrentHashMap;

public class DirectoryNode extends FilesystemNode {
    private final ConcurrentHashMap<String , FilesystemNode> children= new ConcurrentHashMap<>(); 

    public DirectoryNode(String name) {super(name);}

    @Override
    void traverse(FilesystemNode node) {
        for (FilesystemNode child : children.values()){
               System.out.println("/"+super.name);
               child.traverse(child);
        }
        return;
    }

    public void addChildren(FilesystemNode child){ children.put(child.getName(),child);}
    public FilesystemNode removeChild(String name){ return children.remove(name);}
    public FilesystemNode getChild(String name){ return children.get(name);}

}
