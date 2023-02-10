package com.example.bootcamp.controller;

import com.example.bootcamp.dto.BidRequest;
import com.example.bootcamp.dto.BidResponse;
import com.example.bootcamp.entity.Bid;
import com.example.bootcamp.entity.User;
import com.example.bootcamp.service.BidService;
import com.example.bootcamp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/bids")
@Api("Bids management service")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping
    @ApiOperation("Make a Bid")
    public ResponseEntity<BidResponse> makeBid(@RequestBody BidRequest bidRequest, @ApiIgnore HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        BidResponse bidResponse = bidService.placeBid(bidRequest.getAdId(), user, bidRequest.getPrice());
        return new ResponseEntity<>(bidResponse, HttpStatus.OK);
    }
}
