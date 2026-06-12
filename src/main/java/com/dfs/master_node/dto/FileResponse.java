package com.dfs.master_node.dto;

import java.util.List;

public class FileResponse extends PathInfoResponse {
   public List<Long> chunkIds;
   public FileResponse(String name, List<Long> chunkIds){
      this.chunkIds=chunkIds;
      this.name=name;
      this.type="FILE";
   }
}
