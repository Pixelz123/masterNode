package com.dfs.master_node.namespace;

import java.util.List;

public record CreateFileCommand(String fileName ,List<Long> chunkIds) implements FilesystemCommand{

    @Override
    public void execute(DirectoryNode node) {
        node.getLock().writeLock().lock();
        try{
            node.addChildren(new FileNode(fileName,chunkIds));
        }finally{
            node.getLock().writeLock().unlock();
        }
    }

}
