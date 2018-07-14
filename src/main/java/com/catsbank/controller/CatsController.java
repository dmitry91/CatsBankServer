package com.catsbank.controller;

import com.catsbank.db.entities.Cat;
import com.catsbank.services.CatsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("catsbank")
public class CatsController {

    private final CatsService catsService;
    private static final Logger Log = LogManager.getLogger(CatsController.class.getName());

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
        Log.info("add new cat");
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
        Log.info("update cat");
        return catsService.updCat(id,text,photo);
    }

    @RequestMapping(value = "cats", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Cat>> listAllCats() {
        Log.info("get all cats");
        return new ResponseEntity<>(catsService.getAllCats(), HttpStatus.OK);
    }

    @RequestMapping(value = "cat/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Cat> getCatById(@RequestParam int id) {
        Log.info("get cat by id");
        return new ResponseEntity<Cat>(catsService.getCatById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "cat/{id}", method = RequestMethod.DELETE)
    public void deleteCat(@PathVariable("id") int id){
        Log.info("delete cat by id");
        catsService.deleteCatById(id);
    }

    @RequestMapping(value = "photo/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto(@RequestParam String name){
        Log.info("get photo");
        HttpHeaders headers = new HttpHeaders();
        byte[] media = catsService.getPhoto(name);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }
}
