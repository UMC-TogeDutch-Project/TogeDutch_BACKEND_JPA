package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.secret.Secret;
import com.proj.togedutch.domain.EmailMessage;
import com.proj.togedutch.domain.Keyword;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.KeywordReqDto;
import com.proj.togedutch.dto.KeywordResDto;
import com.proj.togedutch.dto.UserReqDto;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.repository.KeywordRepository;
import com.proj.togedutch.repository.UserRepository;
import com.proj.togedutch.utils.AES128;
import com.proj.togedutch.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    public UserResDto createUser(UserReqDto user) throws BaseException{
        if (checkEmail(user.getEmail()) != null) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try {
            AES128 aes128 = new AES128(Secret.USER_INFO_PASSWORD_KEY);
            pwd = aes128.encrypt(user.getPassword());
            user.setPassword(pwd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            User newUser = new User(user.getKeywordIdx(), user.getName(), user.getRole(), user.getEmail(), user.getPassword(), user.getPhone(), user.getImage(), user.getStatus(), user.getLatitude(), user.getLongitude());
            User saveUser = userRepository.save(newUser);
            logger.debug(String.valueOf(saveUser.getUserIdx()));
            System.out.println(saveUser.getUserIdx());
            String jwt = jwtService.createJwt(saveUser.getUserIdx());
            UserResDto createUser = getUser(saveUser.getUserIdx());
            System.out.println(jwt);
            //UserResDto userRes = new UserResDto(createUser.getUserIdx(), createUser.getKeywordIdx(), createUser.getName(), createUser.getRole(), createUser.getEmail(), createUser.getPassword(), createUser.getPhone(), createUser.getImage(), createUser.getStatus(), createUser.getCreated_at(), createUser.getUpdated_at(), createUser.getLatitude(), createUser.getLongitude(), jwt);
            createUser.setJwt(jwt);

            return createUser;
        } catch (Exception e) {
            logger.debug("에러로그S : " + e.getCause());
            System.out.println("에러로그S : " + e.getCause());
            System.out.println("에러로그S : " + e.getMessage());
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public User checkEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public UserResDto getUser(int userIdx) throws BaseException{
        try {
            User user = userRepository.findUserByUserIdx(userIdx);
            UserResDto userRes = new UserResDto(user.getUserIdx(), user.getKeywordIdx(), user.getName(), user.getRole(), user.getEmail(), user.getPassword(), user.getPhone(), user.getImage(), user.getStatus(), user.getCreated_at(), user.getUpdated_at(), user.getLatitude(), user.getLongitude());
            return userRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public KeywordResDto createKeyword(KeywordReqDto keyword) throws BaseException {
        try {
            //logger.debug(keyword.getWord1());
            Keyword reqKeyword = new Keyword(keyword.getWord1(), keyword.getWord2(), keyword.getWord3(), keyword.getWord4(), keyword.getWord5(), keyword.getWord6());
            Keyword saveKeyword = keywordRepository.save(reqKeyword);
            //int keywordIdx = userDao.createKeyword(keyword);
            KeywordResDto createKeyword = new KeywordResDto(saveKeyword.getKeywordIdx(), saveKeyword.getWord1(), saveKeyword.getWord2(), saveKeyword.getWord3(), saveKeyword.getWord4(), saveKeyword.getWord5(), saveKeyword.getWord6());
            return createKeyword;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
    //키워드 조회(키워드 id)
    public KeywordResDto getKeyword(int keywordIdx) throws BaseException{
        try {
            Keyword keyword = keywordRepository.findKeywordByKeywordIdx(keywordIdx);
            KeywordResDto keywordRes = new KeywordResDto(keyword.getKeywordIdx(), keyword.getWord1(), keyword.getWord2(), keyword.getWord3(), keyword.getWord4(), keyword.getWord5(), keyword.getWord6());
            return keywordRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public KeywordResDto getKeywordByUserIdx(int userIdx) throws BaseException {
        try {
            UserResDto user = getUser(userIdx);
            return getKeyword(user.getKeywordIdx());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


//    public List<User> getUsers() throws BaseException {
//        try {
//            List<User> users = userRepository.findAllUser();
//
//            return users;
//        } catch (Exception e) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public UserResDto login(UserReqDto user) throws BaseException {
        User getUser = userRepository.findUserByEmail(user.getEmail());
        UserResDto userRes = new UserResDto(getUser.getUserIdx(), getUser.getKeywordIdx(), getUser.getName(), getUser.getRole(), getUser.getEmail(), getUser.getPassword(), getUser.getPhone(), getUser.getImage(), getUser.getStatus(), getUser.getCreated_at(), getUser.getUpdated_at(), getUser.getLatitude(), getUser.getLongitude());
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(getUser.getPassword());
            System.out.println(user.getPassword());
            System.out.println(password);
        } catch (Exception e) {
            System.out.println(e.getCause());
            e.printStackTrace();
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if (user.getPassword().equals(password)) {
            System.out.println("login : " + getUser.getUserIdx());
            int userIdx = getUser.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            userRes.setJwt(jwt);
            System.out.println(jwt);
            return userRes;
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

//    public User modifyUser(User user) throws BaseException {
//        try {
//            User patchUser = userDao.modifyUser(user);
//            return patchUser;
//        } catch (Exception e) {
//            throw new BaseException(MODIFY_FAIL_USER);
//        }
//    }

    public UserResDto modifyPassword(int userIdx, String password) throws BaseException {
        String pwd;
        try {
            AES128 aes128 = new AES128(Secret.USER_INFO_PASSWORD_KEY);
            pwd = aes128.encrypt(password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            if (pwd != null) {
                User modifyUser = userRepository.findUserByUserIdx(userIdx);
                modifyUser.setPassword(pwd);
                User user = userRepository.save(modifyUser);
                UserResDto userRes = new UserResDto(user.getUserIdx(), user.getKeywordIdx(), user.getName(), user.getRole(), user.getEmail(), user.getPassword(), user.getPhone(), user.getImage(), user.getStatus(), user.getCreated_at(), user.getUpdated_at(), user.getLatitude(), user.getLongitude());
                return userRes;
            }
            else {
                throw new BaseException(USERS_EMPTY_PASSWORD);
            }
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public KeywordResDto modifyKeyword(KeywordReqDto keyword) throws BaseException {
        try {
            Keyword getKeyword = keywordRepository.findKeywordByKeywordIdx(keyword.getKeywordIdx());
            getKeyword.setWord1(keyword.getWord1());
            getKeyword.setWord2(keyword.getWord2());
            getKeyword.setWord3(keyword.getWord3());
            getKeyword.setWord4(keyword.getWord4());
            getKeyword.setWord5(keyword.getWord5());
            getKeyword.setWord6(keyword.getWord6());
            Keyword modifyKeyword = keywordRepository.save(getKeyword);
            KeywordResDto keywordRes = new KeywordResDto(modifyKeyword.getKeywordIdx(), modifyKeyword.getWord1(), modifyKeyword.getWord2(), modifyKeyword.getWord3(), modifyKeyword.getWord4(), modifyKeyword.getWord5(), modifyKeyword.getWord6());
            return keywordRes;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public int deleteUser(int userIdx) throws BaseException {
        try {
            User getUser = userRepository.findUserByUserIdx(userIdx);
            userRepository.delete(getUser);
            return 1;
        } catch (Exception e) {
            throw new BaseException(DELETE_FAIL_USER);
        }
    }

    public UserResDto modifyStatus(int userIdx, String status) throws BaseException {
        try {
            User getUser = userRepository.findUserByUserIdx(userIdx);
            getUser.setStatus(status);
            User saveUser = userRepository.save(getUser);
            UserResDto userRes = new UserResDto(saveUser.getUserIdx(), saveUser.getKeywordIdx(), saveUser.getName(), saveUser.getRole(), saveUser.getEmail(), saveUser.getPassword(), saveUser.getPhone(), saveUser.getImage(), saveUser.getStatus(), saveUser.getCreated_at(), saveUser.getUpdated_at(), saveUser.getLatitude(), saveUser.getLongitude());
            return userRes;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public UserResDto modifyUserImage(int userIdx, String fileUrl) throws BaseException {
        try {
            User getUser = userRepository.findUserByUserIdx(userIdx);
            getUser.setImage(fileUrl);
            User saveUser = userRepository.save(getUser);
            UserResDto userRes = new UserResDto(saveUser.getUserIdx(), saveUser.getKeywordIdx(), saveUser.getName(), saveUser.getRole(), saveUser.getEmail(), saveUser.getPassword(), saveUser.getPhone(), saveUser.getImage(), saveUser.getStatus(), saveUser.getCreated_at(), saveUser.getUpdated_at(), saveUser.getLatitude(), saveUser.getLongitude());
            return userRes;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public UserResDto modifyPhone(int userIdx, String phone) throws BaseException {
        try {
            User getUser = userRepository.findUserByUserIdx(userIdx);
            getUser.setPhone(phone);
            User saveUser = userRepository.save(getUser);
            UserResDto userRes = new UserResDto(saveUser.getUserIdx(), saveUser.getKeywordIdx(), saveUser.getName(), saveUser.getRole(), saveUser.getEmail(), saveUser.getPassword(), saveUser.getPhone(), saveUser.getImage(), saveUser.getStatus(), saveUser.getCreated_at(), saveUser.getUpdated_at(), saveUser.getLatitude(), saveUser.getLongitude());
            return userRes;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public User getUserByEmail(String email) throws BaseException {
        try {
            User getUser = userRepository.findUserByEmail(email);
            //UserResDto userRes = new UserResDto(getUser.getUserIdx(), getUser.getKeywordIdx(), getUser.getName(), getUser.getRole(), getUser.getEmail(), getUser.getPassword(), getUser.getPhone(), getUser.getImage(), getUser.getStatus(), getUser.getCreated_at(), getUser.getUpdated_at(), getUser.getLatitude(), getUser.getLongitude());
            return getUser;
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_FIND_USER);
        }
    }

    //TODO 여기서부터 작성 230122
    public int sendEmail(User user) throws BaseException{
        try {
            String password;
            try {
                password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
                System.out.println(user.getPassword());
                System.out.println(password);
            } catch (Exception e) {
                System.out.println(e.getCause());
                e.printStackTrace();
                throw new BaseException(PASSWORD_DECRYPTION_ERROR);
            }
            EmailMessage emailMessage = EmailMessage.builder()
                    .to(user.getEmail())
                    .subject("[가치더치] 회원님의 비밀번호 안내드립니다.")
                    .message("회원님의 비밀번호는 " + password + "입니다.")
                    .build();
            if (emailService.sendMail(emailMessage) == 1)
                return 1;
            return 0;
        } catch (Exception e) {
            throw new BaseException(null);
        }
    }

}