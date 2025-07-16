package org.example.BookStore.services;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

@Service
public class URLService {
    public static boolean checkURL(String url){
        try {
            Image image = ImageIO.read(new URL(url));
            if(image == null){
                return false;
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}
