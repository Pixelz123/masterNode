package com.dfs.master_node.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        if (fullPath==null || fullPath.equals("/")||fullPath.trim().isEmpty()){
            return rootNamespace;
        }
        Queue<String> segment= new LinkedList<>(Arrays.asList(fullPath.replaceAll("^/+", "").split("/")));
         System.out.println("Path steps"+segment.size());
        return rootNamespace.resolve(segment);
    }
    public void createFileComponent(ComponentRequest request){
        FilesystemNode resolvedComponent = resolvePath(request.ComponentPath());
        if (!(resolvedComponent instanceof DirectoryNode targetComponent)){
            throw new IllegalArgumentException("Cannot create in a file !!!");
        }
        FilesystemNode newNode = switch(request.ComponentType()){
            case DIRECTORY_COMPONENT -> FilesystemComponentType.DIRECTORY_COMPONENT.createComponent(new FileNodeContext(request.ComponentName()));
            case FILE_COMPONENT -> FilesystemComponentType.FILE_COMPONENT.createComponent(new FileNodeContext(request.ComponentName(),List.<Long>of(100L)));
        };
        targetComponent.addChildren(newNode);
        System.out.println(targetComponent.getChildren());
        // for (String child: targetComponent.)

    }
    public void deleteFileComponent(ComponentRequest request){
        FilesystemNode resolvedComponent = resolvePath(request.ComponentPath());
         
    }
    public String showFileComponent(String request){
        System.out.println("show command for path "+ request);
        FilesystemNode resolvedComponent = resolvePath(request);
        if (!(resolvedComponent instanceof DirectoryNode targetComponent)){
            throw new IllegalArgumentException("Cannot create in a file !!!");
        }
        return targetComponent.getChildren();
    }
}
