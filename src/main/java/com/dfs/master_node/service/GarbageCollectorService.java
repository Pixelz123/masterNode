package com.dfs.master_node.service;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class GarbageCollectorService {
   private final ConcurrentLinkedQueue<Long> pendingDeletions= new ConcurrentLinkedQueue<>();
   
   public void markForDeletion(List<Long> chunkId){
       pendingDeletions.addAll(chunkId);  
   }
   public List<Long> fetchPendingList(int batchSize){
       List<Long> deleteList = Stream.generate(pendingDeletions::poll)
                               .takeWhile((Long e)-> e!=null)
                               .limit(batchSize)
                               .toList();
       return deleteList;
   }
}
