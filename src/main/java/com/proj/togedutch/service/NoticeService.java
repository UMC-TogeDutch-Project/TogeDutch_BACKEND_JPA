package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Notice;
import com.proj.togedutch.dto.NoticeReqDto;
import com.proj.togedutch.dto.NoticeResDto;
import com.proj.togedutch.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    //공지사항 생성
    public NoticeResDto createNotice(NoticeReqDto noticeReq) throws BaseException {
        try {
            Notice notice = noticeReq.dtoToEntity();
            Notice save = noticeRepository.save(notice);
            NoticeResDto noticeResDto = new NoticeResDto(save);

            return noticeResDto;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 공지사항 전체 조회 (최신순)
    public List<NoticeResDto> getAllNotices() throws BaseException {
        try {
            List<Notice> getAllNotices = noticeRepository.findAllByOrderByCreatedAtDesc();

            List<NoticeResDto> noticeResDtos = new ArrayList<>();
            for (int i = 0; i < getAllNotices.size(); i++) {
                noticeResDtos.add(new NoticeResDto(getAllNotices.get(i)));
            }
            return noticeResDtos;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 공지사항 삭제
    public int deleteNotice(int noticeIdx) throws BaseException {
        try {
            noticeRepository.deleteById(noticeIdx);
            return 1;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 공지사항 수정
    public NoticeResDto modifyNotice(int noticeIdx, NoticeReqDto noticeReqDto) throws BaseException {
        try {
            Notice notice =
                    noticeRepository.findById(noticeIdx)
                            .orElseThrow();

            String title = noticeReqDto.getTitle();
            String content = noticeReqDto.getContent();

            notice.modifyNotice(content, title);

            noticeRepository.save(notice);

            return new NoticeResDto(notice);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public NoticeResDto getNoticeById(int noticeIdx) throws BaseException {
        try {
            Notice notice = noticeRepository.findById(noticeIdx).get();

            return new NoticeResDto(notice);

        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

