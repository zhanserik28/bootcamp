package com.example.bootcamp;

import com.example.bootcamp.dto.BidResponse;
import com.example.bootcamp.entity.Ad;
import com.example.bootcamp.entity.AdStatus;
import com.example.bootcamp.entity.Bid;
import com.example.bootcamp.entity.User;
import com.example.bootcamp.repository.AdRepository;
import com.example.bootcamp.repository.BidRepository;
import com.example.bootcamp.service.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BidServiceTest.class);

    @Mock
    private AdRepository adRepository;

    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private BidService bidService;

    private User user;
    private Ad ad;
    private Bid bid;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@test");

        ad = new Ad();
        ad.setId(1L);
        ad.setMinimumPrice(100.0);
        ad.setStatus(AdStatus.ACTIVE);

        bid = new Bid();
        bid.setUserId(1L);
        bid.setPrice(200.0);
        bid.setLocalDateTime(LocalDateTime.now());
        bid.setAdId(1L);
    }

    @Test
    public void testPlaceBidWithLowBidAmount() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.of(ad));
        BidResponse bidResponse = bidService.placeBid(1L, user, 50.0);
        assertThat(bidResponse.isSuccess()).isFalse();
    }

    @Test
    public void testPlaceBidWithHigherBidAmount() {
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        when(adRepository.findById(anyLong())).thenReturn(Optional.of(ad));
        when(bidRepository.findByAdIdOrderByPriceDesc(anyLong())).thenReturn(bids);
        when(bidRepository.save(any(Bid.class))).thenReturn(bid);
        BidResponse bidResponse = bidService.placeBid(1L, user, 250);
        assertThat(bidResponse.isSuccess()).isTrue();
    }

    @Test
    public void testPlaceBidWithSameBidAmount() {
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        when(adRepository.findById(anyLong())).thenReturn(Optional.of(ad));
        when(bidRepository.findByAdIdOrderByPriceDesc(anyLong())).thenReturn(bids);
        BidResponse bidResponse = bidService.placeBid(1L, user, 200.0);
        assertThat(bidResponse.isSuccess()).isFalse();
    }

    @Test
    public void testPlaceBidWithAdNotFound() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.empty());
        BidResponse bidResponse = bidService.placeBid(ad.getId(), user, 100);
        assertFalse(bidResponse.isSuccess());
    }
}
