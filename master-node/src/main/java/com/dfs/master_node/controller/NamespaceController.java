package com.dfs.master_node.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dfs.master_node.namespace.FilesystemComponentType;
import com.dfs.master_node.service.NamespaceService;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins ="*")
public class NamespaceController {
    private final NamespaceService namespaceService;
    
    @Autowired 
    public NamespaceController(NamespaceService namespaceService){
        this.namespaceService=namespaceService;
    }
     
    public record ComponentRequest(String ComponentName ,FilesystemComponentType ComponentType, String ComponentPath){}
    public record PathRequest(String path){}

    // @PostMapping("/getNodeList")
    // public Mono<List<String>> getNodeListForFile(@RequestBody String filePath){
       
    // }
    @PostMapping("/create")
    public Mono<String> createComponent(@RequestBody ComponentRequest request){
       return Mono.fromCallable(()->{
           namespaceService.createFileComponent(request);
            return "ok";
        }
       ); 
    }
    @PostMapping("/delete")
    public Mono<String> deleteComponent(@RequestBody ComponentRequest request){
       return Mono.fromCallable(()->{
           namespaceService.deleteFileComponent(request);
           return "deleted!!!";
       });
    }
    @PostMapping("/show")
    public Mono<String> showChildren(@RequestBody PathRequest request){
       return Mono.fromCallable(()->{
            return namespaceService.showFileComponent(request.path());
       });
    }
    @GetMapping("/test")
    public Mono<String> test(){
        return Mono.fromCallable(()->{return "ok";});
    }
}
