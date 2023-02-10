package com.example.bootcamp.controller;

import com.example.bootcamp.dto.AdCreateDto;
import com.example.bootcamp.entity.Ad;
import com.example.bootcamp.entity.AdStatus;
import com.example.bootcamp.entity.User;
import com.example.bootcamp.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ads")
@Api("Ads management service")
public class AdController {
    @Autowired
    private AdService adService;

    @PostMapping
    @ApiOperation("Create Ad")
    public ResponseEntity<Ad> createAd(@ModelAttribute AdCreateDto adCreateDto,@RequestParam("file") MultipartFile file, @ApiIgnore HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(adService.createAd(adCreateDto, user, file), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get all ads")
    public List<Ad> getAds() {
        return adService.getAds();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get ad by id")
    public ResponseEntity<Ad> getAdById(@PathVariable Long id) {
        Ad ad = adService.getAdById(id);
        if (ad == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ad, HttpStatus.OK);
    }

    @GetMapping("/active")
    @ApiOperation("Get Active Ads")
    public List<Ad> getActiveAds() {
        return adService.getAds().stream().filter(x -> AdStatus.ACTIVE.equals(x.getStatus())).collect(Collectors.toList());
    }
}
