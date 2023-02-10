package com.example.bootcamp.service;

import com.example.bootcamp.dto.AdCreateDto;
import com.example.bootcamp.entity.Ad;
import com.example.bootcamp.entity.AdStatus;
import com.example.bootcamp.entity.User;
import com.example.bootcamp.repository.AdRepository;
import com.example.bootcamp.repository.BidRepository;
import com.example.bootcamp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AdService {
    @Autowired
    public AdRepository adRepository;

    @Autowired
    public BidRepository bidRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BidService.class);


    public Ad createAd(AdCreateDto adCreateDto, User user, MultipartFile file) {
        Ad ad = Ad.builder()
                .title(adCreateDto.getTitle())
                .description(adCreateDto.getDescription())
                .status(AdStatus.ACTIVE)
                .minimumPrice(adCreateDto.getMinimumPrice())
                .image(Utils.getFileName(file))
                .user(user)
                .build();
        LOGGER.info("Message: {}", "user successfully registered");
        return adRepository.save(ad);
    }

    public boolean disableAd(Ad ad) {
        Ad tempAd = adRepository.findById(ad.getId()).orElse(null);
        if (tempAd == null) {
            return false;
        }
        tempAd.setStatus(AdStatus.UNPUBLISHED);
        adRepository.save(tempAd);
        return true;
    }

    public List<Ad> getAds() {
        return adRepository.findAll();
    }

    public Ad getAdById(Long id) {
        return adRepository.findById(id).orElse(null);
    }

}
