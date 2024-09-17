package com.guilhermehermes.services;

import com.guilhermehermes.domain.Post;
import com.guilhermehermes.repository.PostRepository;
import com.guilhermehermes.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post findById(String id){
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ObjectNotFoundException(id));
    }

    public List<Post> findByTitle(String title){
//        return postRepository.searchTitle(title);
        return postRepository.findAllByTitleContainingIgnoreCase(title);
    }

    public List<Post> findByText(String text){
        return postRepository.findAllByBodyContainingIgnoreCaseOrTitleContainingIgnoreCase(text, text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate){
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return postRepository.fullsearch(text, minDate, maxDate);
    }

}
