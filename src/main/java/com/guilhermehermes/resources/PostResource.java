package com.guilhermehermes.resources;

import com.guilhermehermes.domain.Post;
import com.guilhermehermes.resources.util.URL;
import com.guilhermehermes.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/posts")
public class PostResource {

    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> findAll(){
        List<Post> list = postService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post post = postService.findById(id);
        return ResponseEntity.ok().body(post);
    }


    @GetMapping(value="/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue="") String text){
        text = URL.decodeParam(text);
        List<Post> list = postService.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value="/fullsearch")
    public ResponseEntity<List<Post>> fullSearch(
            @RequestParam(value = "text", defaultValue="")String text,
            @RequestParam(value = "minDate", defaultValue="") String minDate,
            @RequestParam(value = "maxDate", defaultValue="") String maxDate
    ){
        text = URL.decodeParam(text);
        Date minD = URL.convertDate(minDate, new Date(0L));
        Date maxD = URL.convertDate(maxDate, new Date(0L));
        List<Post> list = postService.fullSearch(text, minD, maxD);
        return ResponseEntity.ok().body(list);
    }

}
