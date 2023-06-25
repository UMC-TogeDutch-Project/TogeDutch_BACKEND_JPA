package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.KeywordReqDto;
import com.proj.togedutch.dto.KeywordResDto;
import com.proj.togedutch.dto.UserReqDto;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.UserService;
import com.proj.togedutch.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.proj.togedutch.utils.ValidationRegex.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Value("${cloud.aws.url}")
    private String url;
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final JwtService jwtService;
    private final AWSS3Service awsS3Service;

    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<UserResDto> createUser(@RequestPart UserReqDto user, @RequestPart(value="file",required = false) MultipartFile file) throws IOException, NullPointerException {
        if (user.getEmail() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
        }
        if (!isRegexEmail(user.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
        }
        if (!isRegexPassword(user.getPassword())) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PASSWORD);
        }
        if (!isRegexPhone(user.getPhone())) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE);
        }
        String fileUrl = null;
        if(file != null && !file.isEmpty()) {
            fileUrl = url + awsS3Service.uploadUserFile(file);
        }
        try {
            user.setImage(fileUrl);
            UserResDto newUser = userService.createUser(user);
            return new BaseResponse<>(newUser);
        } catch (BaseException e) {
            logger.debug("에러로그 : " + e.getCause());
            System.out.println("에러로그 : " + e.getCause());
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }

//    @ResponseBody
//    @GetMapping("")
//    public BaseResponse<List<UserResDto>> getUsers() {
//        try {
//            List<UserResDto> users = userService.getUsers();
//            return new BaseResponse<>(users);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//
//    }

    //user-3
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<UserResDto> login(@RequestBody UserReqDto user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);
            }
            if (!isRegexEmail(user.getEmail())){
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
            }
            if (!isRegexPassword(user.getPassword())) {
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PASSWORD);
            }
            UserResDto loginUser = userService.login(user);
            return new BaseResponse<>(loginUser);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}/password")
    public BaseResponse<UserResDto> modifyPassword(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            System.out.println(userIdx);
            System.out.println(userIdxByJwt);
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            UserResDto newUser = userService.modifyPassword(userIdx, user.getPassword());
            return new BaseResponse<>(newUser);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<UserResDto> getUser(@PathVariable("userIdx") int userIdx) {
        try {
            UserResDto getUser = userService.getUser(userIdx);
            return new BaseResponse<>(getUser);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //user-6
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    public BaseResponse<String> deleteUser(@PathVariable("userIdx") int userIdx) {
        try {
            int delete = userService.deleteUser(userIdx);
            if (delete == 1) {
                return new BaseResponse<>("delete success");
            }
            return new BaseResponse<>("delete fail");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //user-7
    @ResponseBody
    @PatchMapping("/{userIdx}/status")
    public BaseResponse<UserResDto> modifyStatus(@PathVariable("userIdx") int userIdx, @RequestParam String status) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            UserResDto user = userService.modifyStatus(userIdx, status);
            return new BaseResponse<>(user);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //user-8
    @ResponseBody
    @PatchMapping("/{userIdx}/image")
    public BaseResponse<UserResDto> modifyUserImage(@PathVariable("userIdx") int userIdx, @RequestPart MultipartFile file) throws IOException {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            String fileUrl = null;
            if(file != null)
                fileUrl = url + awsS3Service.uploadUserFile(file);
            UserResDto user = userService.modifyUserImage(userIdx, fileUrl);
            return new BaseResponse<>(user);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //TODO user-11
    @ResponseBody
    @PatchMapping("/{userIdx}/phone")
    public BaseResponse<UserResDto> modifyPhone(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            System.out.println(user.getPhone());
            UserResDto newUser = userService.modifyPhone(userIdx, user.getPhone());
            return new BaseResponse<>(newUser);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //TODO user-12
    @ResponseBody
    @GetMapping("/findPwd")
    public BaseResponse<String> findPassword(@RequestParam String email) {
        try {
            if (email == null || email.equals("")) {
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
            }
            if (!isRegexEmail(email)) {
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
            }
            User returnUser = userService.getUserByEmail(email);
            if (userService.sendEmail(returnUser) == 1)
                return new BaseResponse<>("success");
            else
                return new BaseResponse<>("fail");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //keyword-1
    @ResponseBody
    @PostMapping("/keyword")
    public BaseResponse<KeywordResDto> createKeyword(@RequestBody KeywordReqDto keyword) {
        try {
            KeywordResDto newKeyword = userService.createKeyword(keyword);
            return new BaseResponse<>(newKeyword);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //keyword-2
    @ResponseBody
    @GetMapping("/{userIdx}/keyword")
    public BaseResponse<KeywordResDto> getKeyword(@PathVariable("userIdx") int userIdx) {
        try {
            KeywordResDto getKeyword = userService.getKeywordByUserIdx(userIdx);
            return new BaseResponse<>(getKeyword);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //keyword-3
    @ResponseBody
    @PatchMapping("/{userIdx}/keyword")
    public BaseResponse<KeywordResDto> modifyKeyword(@PathVariable("userIdx") int userIdx, @RequestBody KeywordReqDto keyword) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            UserResDto user = userService.getUser(userIdx);
            keyword.setKeywordIdx(user.getKeywordIdx());
            KeywordResDto modifyKeyword = userService.modifyKeyword(keyword);
            return new BaseResponse<>(modifyKeyword);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
