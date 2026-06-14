package com.dfs.master_node.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dfs.master_node.controller.NamespaceController.ComponentRequest;
import com.dfs.master_node.namespace.DirectoryNode;
import com.dfs.master_node.namespace.FileNode;
import com.dfs.master_node.namespace.FilesystemComponentType;
import com.dfs.master_node.namespace.FilesystemNode;

@Service
public class NamespaceService {
    private final DirectoryNode rootNamespace= new DirectoryNode("");

    public record FileNodeContext(String ComponentName,List<Long> chunkIds){
        public FileNodeContext(String name){
            this(name,Collections.emptyList());
        }
    }

    public DirectoryNode getRoot() {return rootNamespace;}

    public FilesystemNode resolvePath(String fullPath){
        if (fullPath == null || fullPath.trim().isEmpty() || fullPath.trim().equals("/")) {
            return rootNamespace;
        }
        
        Queue<String> segment = Arrays.stream(fullPath.split("/"))
                .map((String seg)-> seg.trim())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(LinkedList::new));
        
        System.out.println("Path steps: " + segment.size());
        return rootNamespace.resolve(segment);
    }
    public void createFileComponent(ComponentRequest request){
        FilesystemNode resolvedComponent = resolvePath(request.ComponentPath());
        if (!(resolvedComponent instanceof DirectoryNode targetComponent)){
            throw new IllegalArgumentException("Cannot create in a file !!!");
        }
        FilesystemNode newNode = switch(request.ComponentType()){
            case DIRECTORY_COMPONENT ->
             FilesystemComponentType.DIRECTORY_COMPONENT.createComponent(new FileNodeContext(request.ComponentName()));
            case FILE_COMPONENT ->
             FilesystemComponentType.FILE_COMPONENT.createComponent(new FileNodeContext(request.ComponentName(),List.<Long>of(100L)));
        };
        targetComponent.addChildren(newNode);

    }
    public void deleteFileComponent(ComponentRequest request){
        int lastSlashIndex = request.ComponentPath().lastIndexOf('/');
        String parentPath = lastSlashIndex <=0 ?"/": request.ComponentPath().substring(0,lastSlashIndex);
        String targetName = request.ComponentPath().substring(lastSlashIndex+1);
        
        FilesystemNode resolvedComponent = resolvePath(parentPath);
        if (!(resolvedComponent instanceof DirectoryNode targetParent)){
            throw new IllegalArgumentException("Corrupt File Path");
        }
        FilesystemNode targetComponent;
        targetParent.getLock().readLock().lock();
        try{
            targetComponent= targetParent.removeChild(targetName);
            if (targetComponent==null){
                throw new IllegalArgumentException("Component does not exist !!!");
            }

        }finally{
            targetParent.getLock().readLock().unlock();
        }
        List<Long> orphanedChunk = new ArrayList<>();
        getOrphanedChunk(targetComponent, orphanedChunk);
        if (!orphanedChunk.isEmpty()){
            // TODO: gc implementation later 
        } 
    }

    private void getOrphanedChunk(FilesystemNode root, List<Long> chunkIds){
        if (root instanceof FileNode file){
            chunkIds.addAll(file.getChunkIds());
        }else if (root instanceof DirectoryNode dir){
            for (FilesystemNode child : dir.getChildren().values()){
                getOrphanedChunk(child, chunkIds);
            }
        }
    }
    public String showFileComponent(String request){
        System.out.println("show command for path "+ request);
        FilesystemNode resolvedComponent = resolvePath(request);
        if (!(resolvedComponent instanceof DirectoryNode targetComponent)){
            throw new IllegalArgumentException("Cannot show contents of a file !!!");
        }
        return targetComponent.getContent();
    }
}
