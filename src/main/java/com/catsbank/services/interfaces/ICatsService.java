package com.catsbank.services.interfaces;
import com.catsbank.db.entities.Cat;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ICatsService {
    public Cat addCat(String text, MultipartFile photo);
    public Cat updCat(int id, String text, MultipartFile photo);
    public List<Cat> getAllCats();
    public Cat getCatById(int id);
    public void deleteCatById(int id);
}
