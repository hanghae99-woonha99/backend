package com.sparta.woonha99.service;

import com.sparta.woonha99.dto.response.LikeReponseDto;
import com.sparta.woonha99.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

}
