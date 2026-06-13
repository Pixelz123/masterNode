package com.dfs.master_node.namespace;

import com.dfs.master_node.service.NamespaceService.FileNodeContext;

public enum FilesystemComponentType {
       DIRECTORY_COMPONENT{
        @Override
         public DirectoryNode createComponent(FileNodeContext context){
            return new DirectoryNode(context.ComponentName());  
         }
       },
       FILE_COMPONENT{
        @Override 
        public FilesystemNode createComponent(FileNodeContext context){
             if (context.chunkIds().isEmpty()){
                throw new IllegalArgumentException("Chunk list for file : "+context.ComponentName()+" missing");
             }
             return new FileNode(context.ComponentName(), context.chunkIds());
        }
       };
       public abstract FilesystemNode createComponent(FileNodeContext context);
}
