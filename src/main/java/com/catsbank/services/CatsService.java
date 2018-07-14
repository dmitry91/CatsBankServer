package com.catsbank.services;

import com.catsbank.db.entities.Cat;
import com.catsbank.db.repository.CatsRepository;
import com.catsbank.services.interfaces.ICatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class CatsService implements ICatsService {

    private final CatsRepository catsRepository;
    private final ImageService imageService;

    @Autowired
    public CatsService(CatsRepository catsRepository, ImageService imageService) {
        this.catsRepository = catsRepository;
        this.imageService = imageService;
    }

    @Override
    public Cat addCat(String text, MultipartFile photo) {
        Cat cat = new Cat();
        try {
            if (photo != null) {
                cat.setText(text);
                cat.setPhotoName(photo.getOriginalFilename());
                imageService.saveFileFromByte(photo.getBytes(),photo.getOriginalFilename());
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

    @Override
    public Cat updCat(int id, String text, MultipartFile photo) {
        Cat cat = catsRepository.findById(id).get();
        try {
            if (text != null) {
                cat.setText(text);
            }
            if (photo != null) {
                if(cat.getPhotoName()!=null || !Objects.equals(cat.getPhotoName(), "")){
                    imageService.deleteFile(cat.getPhotoName());// delete old image in update
                }
                cat.setPhotoName(photo.getOriginalFilename());
                imageService.saveFileFromByte(photo.getBytes(),photo.getOriginalFilename());
            }
            return catsRepository.save(cat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cat> getAllCats() {
        return (List<Cat>) catsRepository.findAll();
    }

    @Override
    public Cat getCatById(int id) {
        return catsRepository.findById(id).get();
    }

    @Override
    public void deleteCatById(int id) {
        imageService.deleteFile(catsRepository.findById(id).get().getPhotoName());
        catsRepository.deleteById(id);
    }

    @Override
    public byte[] getPhoto(String name) {
        return imageService.getFileByte(name);
    }
}
