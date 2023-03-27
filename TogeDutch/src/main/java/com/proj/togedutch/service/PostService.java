package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    // 모든 공고
    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
