package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.GenreFilm;
import com.challenge.mycinema.repository.GenreFilmRepository;
import com.challenge.mycinema.service.SortAscDesc;
import com.challenge.mycinema.utils.MessageModel;
import com.challenge.mycinema.utils.MessageModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/genreFilm")
public class GenreFilmController {
    @Autowired
    private GenreFilmRepository genreFilmRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data genre_film
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<GenreFilm> param) {
        MessageModel msg = new MessageModel();
        try {
            List<GenreFilm> genreFilmList = new ArrayList<>();
            for (GenreFilm data : param) {
                GenreFilm genreFilm = new GenreFilm();
                String uuid = UUID.randomUUID().toString();

                genreFilm.setGenreId(uuid);
                genreFilm.setKode(data.getKode());
                genreFilm.setNama(data.getNama());
                genreFilm.setCreated(new Date());
                genreFilm.setCreatedBy(data.getCreatedBy());
                genreFilm.setUpdated(new Date());
                genreFilm.setUpdatedBy("SYSTEM");
                genreFilm.setIsactive("Y");

                genreFilmList.add(genreFilm);
            }
            genreFilmRepository.saveAll(genreFilmList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(genreFilmList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data genre_film
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<GenreFilm> param) {
        MessageModel msg = new MessageModel();
        try {
            List<GenreFilm> genreFilmList = new ArrayList<>();
            for (GenreFilm data : param) {
                GenreFilm genreFilm = genreFilmRepository.getById(data.getGenreId());

                genreFilm.setKode(data.getKode());
                genreFilm.setNama(data.getNama());
                genreFilm.setUpdated(new Date());
                genreFilm.setUpdatedBy(data.getUpdatedBy());
                genreFilm.setIsactive(data.getIsactive());

                genreFilmList.add(genreFilm);
            }
            genreFilmRepository.saveAll(genreFilmList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(genreFilmList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete Data genre_film By genre_id
    @DeleteMapping("/delete/{genreId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("genreId") String genreId) {
        MessageModel msg = new MessageModel();
        try {
            genreFilmRepository.deleteById(genreId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data genre_film By genre_id
    @GetMapping("/getData/{genreId}")
    public ResponseEntity<MessageModel> getById(@PathVariable("genreId") String genreId) {
        MessageModel msg = new MessageModel();
        try {
            GenreFilm data = genreFilmRepository.getById(genreId);

            msg.setStatus(true);
            msg.setMessage("Success to get data..");
            msg.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get List Data genre_film
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<GenreFilm> data = (List<GenreFilm>) genreFilmRepository.findAll();

            msg.setStatus(true);
            msg.setMessage("Success to get all data..");
            msg.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data Pagination From genre_film
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<GenreFilm> data = genreFilmRepository.findAll(pageRequest);

            msg.setStatus(true);
            msg.setMessage("Success to get all data..");
            msg.setData(data.getContent());
            msg.setCurrentPage(data.getNumber());
            msg.setTotalPages(data.getTotalPages());
            msg.setTotalItems((int) data.getTotalElements());
            msg.setNumberOfElement(data.getNumberOfElements());

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

}