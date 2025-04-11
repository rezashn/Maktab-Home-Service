package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.OfferDTO;
import com.example.maktabproject1.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@Valid @RequestBody OfferDTO offerDTO) {
        OfferDTO createdOffer = offerService.createOffer(offerDTO);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        OfferDTO offerDTO = offerService.getOfferById(id);
        return new ResponseEntity<>(offerDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        List<OfferDTO> offerDTOS = offerService.getAllOffers();
        return new ResponseEntity<>(offerDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferDTO> updateOffer(@PathVariable Long id, @Valid @RequestBody OfferDTO offerDTO) {
        OfferDTO updatedOffer = offerService.updateOffer(id, offerDTO);
        return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OfferDTO>> getOffersByOrderId(@PathVariable Long orderId) {
        List<OfferDTO> offerDTOS = offerService.getOffersByOrderId(orderId);
        return new ResponseEntity<>(offerDTOS, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/accept/{offerId}")
    public ResponseEntity<Void> acceptOffer(@PathVariable Long orderId, @PathVariable Long offerId) {
        offerService.acceptOffer(orderId, offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}