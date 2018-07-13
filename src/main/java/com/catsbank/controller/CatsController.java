package com.catsbank.controller;

import com.catsbank.db.repository.CatsRepository;
import com.catsbank.db.entities.Cat;
import com.catsbank.services.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("catsbank")
public class CatsController {

    private final CatsService catsService;

    @Autowired
    public CatsController(CatsService catsService) {
        this.catsService = catsService;
    }

    /**
     * add new Cat to base
     * @param text text about Cat
     * @param photo image Cat
     * @return Object if success else return null
     */
    @RequestMapping(value = "cat", method = RequestMethod.POST)
    @ResponseBody
    public Cat addCat(@RequestParam String text, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        return catsService.addCat(text,photo);
    }

    /**
     * update cat in database
     * @param id id for update
     * @param text new text
     * @param photo new image
     * @return Object if success else return null
     */
    @RequestMapping(value = "updcat", method = RequestMethod.POST)//I do not use "put", because an incomprehensible mistake in retrofit "code=400"
    @ResponseBody
    public Cat updCat(@RequestParam int id, @RequestParam String text, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        return catsService.updCat(id,text,photo);
    }

    @RequestMapping(value = "cats", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Cat>> listAllCats() {
        return new ResponseEntity<>(catsService.getAllCats(), HttpStatus.OK);
    }

    @RequestMapping(value = "cat/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Cat> getCatById(@RequestParam int id) {
        return new ResponseEntity<Cat>(catsService.getCatById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "cat/{id}", method = RequestMethod.DELETE)
    public void deleteCat(@PathVariable("id") int id){
        catsService.deleteCatById(id);
    }
}
