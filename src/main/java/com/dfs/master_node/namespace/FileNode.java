package com.dfs.master_node.namespace;

import java.util.List;

public class FileNode extends FilesystemNode {
    private final List<Long> chunkIds;
    public FileNode(String name,List<Long> chunkIds) {
           super(name);
           this.chunkIds = null;
    }
    @Override
    void traverse(FilesystemNode node) {
        System.out.println("/"+super.name);
    }
    public List<Long> getChunkIds(){ return chunkIds;}
}
