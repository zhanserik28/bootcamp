package com.example.bootcamp.service;


import com.example.bootcamp.dto.BidResponse;
import com.example.bootcamp.entity.Ad;
import com.example.bootcamp.entity.AdStatus;
import com.example.bootcamp.entity.Bid;
import com.example.bootcamp.entity.User;
import com.example.bootcamp.repository.AdRepository;
import com.example.bootcamp.repository.BidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
;

@Service
@Transactional
public class BidService {

    private static final int WAIT_TIME_IN_SECONDS = 30;
    private static final Logger LOGGER = LoggerFactory.getLogger(BidService.class);

    @Autowired
    public BidRepository bidRepository;

    @Autowired
    public AdRepository adRepository;

    public List<Bid> getBids(Long adId) {
        return bidRepository.findBidByAdIdOrderById(adId);
    }

    public synchronized BidResponse placeBid(Long adId, User user, double bidAmount) {
        Optional<Ad> ad = adRepository.findById(adId);
        if (ad.isEmpty() || ad.get().getMinimumPrice() > bidAmount) {
            return new BidResponse(false, "The bid value is smaller than ad value");
        }
        List<Bid> bids = bidRepository.findByAdIdOrderByPriceDesc(adId);
        if (bids.isEmpty() || bids.get(0).getPrice() < bidAmount) {
            Bid newBid = new Bid();
            newBid.setUserId(user.getId());
            newBid.setPrice(bidAmount);
            newBid.setLocalDateTime(LocalDateTime.now());
            newBid.setAdId(adId);
            bidRepository.save(newBid);
            startBiddingTimeout(adId, user, bidAmount);
            return new BidResponse(true, "The bid is accepted");
        } else {
            return new BidResponse(false, "The bid is lower than current max bid: " + bids.get(0).getPrice());
        }
    }

    private void startBiddingTimeout(Long adId, User user, double bidAmount) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Optional<Ad> adOptional = adRepository.findById(adId);
                if (adOptional.isEmpty()) {
                    LOGGER.info("Message: {}", user.getEmail() + " bid has been declined as the ad does not exist");
                    return;
                }

                Ad ad = adOptional.get();
                if (AdStatus.UNPUBLISHED.equals(ad.getStatus())) {
                    LOGGER.info("Message: {}", user.getEmail() + " bid has been declined as the ad is already unpublished");
                    return;
                }

                List<Bid> bids = bidRepository.findByAdIdOrderByPriceDesc(adId);
                Bid highestBid = bids.get(0);
                if (highestBid.getPrice() > bidAmount) {
                    LOGGER.info("Message: {}", user.getEmail() + " bid has been declined by other user");
                } else {
                    ad.setStatus(AdStatus.UNPUBLISHED);
                    ad.setCurrentPrice(highestBid.getPrice());
                    ad.setUserBought(user);
                    LOGGER.info("Message: {}", user.getEmail() + " bid has been accepted");
                }
            }
        }, WAIT_TIME_IN_SECONDS * 1000);
    }

    public Optional<Bid> getMaxBidByAdId(Long adId) {
        return bidRepository.findTopBidByAdIdOrderByPriceDesc(adId).stream().max(Comparator.comparingDouble(Bid::getPrice));

    }

}
