package com.example.bootcamp.repository;

import com.example.bootcamp.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    public List<Bid> findBidByAdIdOrderById(Long id);

    @Query("SELECT b FROM Bid b WHERE b.adId = :adId ORDER BY b.price DESC")
    List<Bid> findTopBidByAdIdOrderByPriceDesc(@Param("adId") Long adId);
    List<Bid> findByAdIdOrderByPriceDesc(@Param("adId") Long adId);

}