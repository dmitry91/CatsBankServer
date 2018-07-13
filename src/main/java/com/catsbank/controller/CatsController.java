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

@RestController
@RequestMapping("catsbank")
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
     * @return Object if success else return null
     */
    @RequestMapping(value = "cat", method = RequestMethod.POST)
    @ResponseBody
    public Cat addCat(@RequestParam String text, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        Cat cat = new Cat();
        try {
            if (photo != null) {
                cat.setText(text);
                cat.setPhoto(photo.getBytes());
                return catsRepository.save( cat);
            } else {
                cat.setText(text);
                return catsRepository.save(cat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        Cat cat = catsRepository.findById(id).get();
        try {
            if (text != null) {
                cat.setText(text);
            }
            if (photo != null) {
                cat.setPhoto(photo.getBytes());
            }
            return catsRepository.save(cat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "cats", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Cat>> listAllCats() {
        List<Cat> list = (List<Cat>) catsRepository.findAll();
        return new ResponseEntity<List<Cat>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "cat/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Cat> getCatById(@RequestParam int id) {
        Cat cat = catsRepository.findById(id).get();
        return new ResponseEntity<Cat>(cat, HttpStatus.OK);
    }

    @RequestMapping(value = "cat/{id}", method = RequestMethod.DELETE)
    public void deleteCat(@PathVariable("id") int id){
        catsRepository.deleteById(id);
    }
}
