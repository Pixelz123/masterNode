package com.dfs.master_node.namespace;

import java.util.List;
import java.util.Queue;
// HWH WP7 CO PUR WP7
public class FileNode extends FilesystemNode {
    private final List<Long> chunkIds;
    public FileNode(String name,List<Long> chunkIds) {
           super(name);
           this.chunkIds = null;
    }
    @Override
    public FilesystemNode resolve(Queue<String> pathSegment) {
        if (pathSegment.isEmpty()) return this;
        throw new RuntimeException("File Not Found !!!");
    }
    public List<Long> getChunkIds(){ return chunkIds;}
}
