package com.dfs.master_node.dto;

import java.util.List;

public class DirectoryResponse extends PathInfoResponse {
    public List<String> childrenList;
    public DirectoryResponse(String name, List<String> childrenList){
        this.childrenList=childrenList;
        this.name=name;
        this.type="DIRECTORY";
    }
}
