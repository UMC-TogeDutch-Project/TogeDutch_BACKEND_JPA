package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Advertisement;
import com.proj.togedutch.dto.AdvertisementReqDto;
import com.proj.togedutch.dto.AdvertisementResDto;
import com.proj.togedutch.dto.DeclarationResDto;
import com.proj.togedutch.repository.AdRepository;
import com.proj.togedutch.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;
@RequiredArgsConstructor
@Slf4j
@Service
public class AdService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdRepository adRepository;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    //광고생성
    public AdvertisementResDto createAd(AdvertisementReqDto adReqDto, int userIdx, String fileUrl, String tid) throws BaseException {
        try {
            adReqDto.setUserIdx(userIdx);
            adReqDto.setImage(fileUrl);
            adReqDto.setTid(tid);
            return new AdvertisementResDto(adRepository.save(adReqDto.toEntity()));
        }catch (Exception e) {
            log.info(e.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //개인의 광고 전체 조회
    public List<AdvertisementResDto> getAdsByUserId(int userIdx) throws BaseException {
        List<Advertisement> adsByUserId = adRepository.findAllByUserIdx(userIdx);
        return adsByUserId.stream()
                .map(m-> new AdvertisementResDto(
                        m.getAdIdx(),
                        m.getStore(),
                        m.getInformation(),
                        m.getMainMenu(),
                        m.getDeliveryTips(),
                        m.getRequest(),
                        m.getCreatedAt(),
                        m.getUpdatedAt(),
                        m.getStatus(),
                        m.getUserIdx(),
                        m.getImage(),
                        m.getTid(),
                        m.getLatitude(),
                        m.getLongitude()))
                        .collect(Collectors.toList());
    }
    //랜덤으로 반경 1km 광고 10개 조회
    public List<AdvertisementResDto> getRandomAds(double latitude, double longitude) throws BaseException {
        List<Advertisement> randomAds = adRepository.findRandomAds(latitude,longitude);
        return randomAds.stream()
                .map(m-> new AdvertisementResDto(m))
                .collect(Collectors.toList());
    }
    //id로 광고 상세 조회
    public AdvertisementResDto getAdById(int adIdx) throws BaseException {
        try {
            Advertisement getAd = adRepository.findById(adIdx)
                    .orElseThrow(()-> new IllegalArgumentException("해당 광고가 없습니다. id="+ adIdx));
            return new AdvertisementResDto(getAd);
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //tid로 광고 삭제
    public void deleteAd(String tid) throws BaseException {
        try {
            adRepository.deleteByTid(tid);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
