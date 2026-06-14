package com.dfs.master_node.namespace;


public record DeleteComponentCommand (String filename) implements FilesystemCommand {

    @Override
    public void execute(DirectoryNode node) {
        node.getLock().writeLock().lock();
        try{
            FilesystemNode removed =  node.removeChild(filename);
            // if (removed instanceof FileNode fileNode){
            //     //  GarbageCollector.markForDeletion
            //     // GarbageCollector.markForDeletion(fileNode.getChunkIds());
            // }
        }finally{
            node.getLock().writeLock().unlock();
        }
    }

}
