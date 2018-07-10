package com.catsbank.controller;

import com.catsbank.db.dao.CatsRepository;
import com.catsbank.db.entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CatsController {

    private final CatsRepository catsRepository;

    @Autowired
    public CatsController(CatsRepository catsRepository) {
        this.catsRepository = catsRepository;
    }

    /**
     * add new Cat to base
     * @param text text about Cat
     * @param photo image Cat
     * @return '1' if success else return '0'
     */
    @RequestMapping(value = "addCat", method = RequestMethod.POST)
    @ResponseBody
    public int addCat(@RequestParam String text, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            if (photo != null) {
                return catsRepository.addCat(text, photo.getBytes());
            } else {
                return catsRepository.addCat(text, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * update cat in database
     * @param id id for update
     * @param text new text
     * @param photo new image
     * @return '1' if success else return '0'
     */
    @RequestMapping(value = "updCat", method = RequestMethod.POST)
    @ResponseBody
    public int updCat(@RequestParam int id, @RequestParam String text, @RequestParam(value = "photo", required = false) MultipartFile photo){
        try {
            if (photo != null) {
                return catsRepository.updCat(id, text, photo.getBytes());
            } else {
                return catsRepository.updCat(id, text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping(value = "getAllCats", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> listAllCats() {
        List<Cat> list = catsRepository.findAll();
        return new ResponseEntity<String>(String.valueOf(list.stream().map(Cat::toJson).collect(Collectors.toList())), HttpStatus.OK);
    }

    @RequestMapping(value = "getCatById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getCatById(@RequestParam int id) {
        Cat cat = catsRepository.findById(id);
        return new ResponseEntity<String>(String.valueOf(cat.toJson()), HttpStatus.OK);
    }

    @RequestMapping(value = "delCatById", method = RequestMethod.GET)
    public String deleteCat(@RequestParam int id){
        catsRepository.deleteById(id);
        return "del";
    }
}
