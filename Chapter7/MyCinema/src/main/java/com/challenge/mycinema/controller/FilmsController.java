package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.Films;
import com.challenge.mycinema.repository.FilmsRepository;
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
@RequestMapping("/films")
public class FilmsController {
    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data films
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Films> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Films> filmsList = new ArrayList<>();
            for (Films data : param) {
                Films films = new Films();
                String uuid = UUID.randomUUID().toString();

                films.setFilmId(uuid);
                films.setGenreId(data.getGenreId());
                films.setKode(data.getKode());
                films.setJudul(data.getJudul());
                films.setKategori(data.getKategori());
                films.setDesc(data.getDesc());
                films.setCreated(new Date());
                films.setCreatedBy(data.getCreatedBy());
                films.setUpdated(new Date());
                films.setUpdatedBy("SYSTEM");
                films.setIsactive("Y");

                filmsList.add(films);
            }
            filmsRepository.saveAll(filmsList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(filmsList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data films
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Films> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Films> filmsList = new ArrayList<>();
            for (Films data : param) {
                Films films = filmsRepository.getById(data.getFilmId());

                films.setGenreId(data.getGenreId());
                films.setKode(data.getKode());
                films.setJudul(data.getJudul());
                films.setKategori(data.getKategori());
                films.setDesc(data.getDesc());
                films.setUpdated(new Date());
                films.setUpdatedBy(data.getUpdatedBy());
                films.setIsactive(data.getIsactive());

                filmsList.add(films);
            }
            filmsRepository.saveAll(filmsList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(filmsList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete data films By film_id
    @DeleteMapping("/delete/{filmId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("filmId") String filmId) {
        MessageModel msg = new MessageModel();
        try {
            filmsRepository.deleteById(filmId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data films By film_id
    @GetMapping("/getData/{filmId}")
    public ResponseEntity<MessageModel> detById(@PathVariable("filmId") String filmId) {
        MessageModel msg = new MessageModel();
        try {
            Films data = filmsRepository.getById(filmId);

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

    // Get List All Data films
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Films> data = (List<Films>) filmsRepository.findAll();

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

    // Get Data Pagination From films
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Films> data = filmsRepository.findAll(pageRequest);

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

    // Get Data films By genre_id
    @GetMapping("/getData/genre/{genreId}")
    public ResponseEntity<MessageModel> getByGenre(@PathVariable("genreId") String genreId) {
        MessageModel msg = new MessageModel();
        try {
            List<Films> data = filmsRepository.getByGenre(genreId);

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

}