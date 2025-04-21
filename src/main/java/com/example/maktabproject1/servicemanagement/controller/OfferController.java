package com.example.maktabproject1.servicemanagement.controller;

import com.example.maktabproject1.servicemanagement.dto.UpdateOfferDto;
import com.example.maktabproject1.servicemanagement.exception.InvalidDataInputException;
import com.example.maktabproject1.servicemanagement.exception.ResponseNotFoundException;
import com.example.maktabproject1.servicemanagement.service.OfferService;
import com.example.maktabproject1.servicemanagement.dto.OfferDTO;
import com.example.maktabproject1.ResponseDto;
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
    public ResponseEntity<ResponseDto<OfferDTO>> createOffer(@Valid @RequestBody OfferDTO offerDTO) {
        try {
            OfferDTO createdOffer = offerService.createOffer(offerDTO);
            return new ResponseEntity<>(new ResponseDto<>(true, createdOffer, "Offer created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<OfferDTO>> getOfferById(@PathVariable Long id) {
        try {
            OfferDTO offerDTO = offerService.getOfferById(id);
            return new ResponseEntity<>(new ResponseDto<>(true, offerDTO, "Offer retrieved successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<OfferDTO>>> getAllOffers() {
        try {
            List<OfferDTO> offerDTOS = offerService.getAllOffers();
            return new ResponseEntity<>(new ResponseDto<>(true, offerDTOS, "Offers retrieved successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<OfferDTO>> updateOffer(@PathVariable Long id, @Valid @RequestBody UpdateOfferDto offerDTO) {
        try {
            OfferDTO updatedOffer = offerService.updateOffer(id, offerDTO);
            return new ResponseEntity<>(new ResponseDto<>(true, updatedOffer, "Offer updated successfully"), HttpStatus.OK);
        } catch (ResponseNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, "Offer not found"), HttpStatus.NOT_FOUND);
        } catch (InvalidDataInputException e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteOffer(@PathVariable Long id) {
        try {
            offerService.deleteOffer(id);
            return new ResponseEntity<>(new ResponseDto<>(true, null, "Offer deleted successfully"), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseDto<List<OfferDTO>>> getOffersByOrderId(@PathVariable Long orderId) {
        try {
            List<OfferDTO> offerDTOS = offerService.getOffersByOrderId(orderId);
            return new ResponseEntity<>(new ResponseDto<>(true, offerDTOS, "Offers for order retrieved successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/order/{orderId}/accept/{offerId}")
    public ResponseEntity<ResponseDto<Void>> acceptOffer(@PathVariable Long orderId, @PathVariable Long offerId) {
        try {
            offerService.acceptOffer(orderId, offerId);
            return new ResponseEntity<>(new ResponseDto<>(true, null, "Offer accepted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(false, null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
