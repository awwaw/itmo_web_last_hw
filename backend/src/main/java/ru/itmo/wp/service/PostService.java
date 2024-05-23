package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final JwtService jwtService;

    public PostService(PostRepository postRepository, JwtService jwtService) {
        this.postRepository = postRepository;
        this.jwtService = jwtService;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post createPost(PostCredentials postCredentials) throws ValidationException {
        Post post = new Post();
        User author = jwtService.find(postCredentials.getAuthorJwt());
        post.setUser(author);
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());
        postRepository.save(post);
        return post;
    }
}
