package com.dfs.master_node.dto;

import java.util.List;

public class DirectoryContentResponse extends PathInfoResponse {
    public List<String> childrenList;
    public DirectoryContentResponse(String name, List<String> childrenList){
        this.childrenList=childrenList;
        this.name=name;
        this.type="DIRECTORY";
    }
}
