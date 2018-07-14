package com.catsbank.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ImageService {

    private static final String path = "src/main/resources/photo/";
    private static final Logger Log = LogManager.getLogger(ImageService.class.getName());

    /**
     * read byte array from file
     * @param name path to file
     * @return byte array
     */
    public byte[] getFileByte(String name) {
        File file = new File(path+name);
        byte[] bFile = new byte[(int) file.length()];
        //read file into bytes[]
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            Log.info("get bytes for file-"+name);
            return bFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * for save file from byte array
     * @param arr byte array for save
     * @param name file name
     */
    public void saveFileFromByte(byte[] arr, String name){
        try (FileOutputStream fileOutputStream = new FileOutputStream(path+name)) {
            fileOutputStream.write(arr);
            Log.info("save file name-"+name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * for delete file
     * @param name name file for delete
     * @return true if file delete, else return false
     */
    public boolean deleteFile(String name){
        try{
            File file = new File(path + name);
            Log.info("delete file name-"+name);
            return file.delete();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
