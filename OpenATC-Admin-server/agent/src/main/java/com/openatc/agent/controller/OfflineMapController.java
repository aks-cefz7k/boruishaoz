package com.openatc.agent.controller;


import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping(value = "/xyz")
public class OfflineMapController {

<<<<<<< HEAD
    //String dataPath = "C:\\MapDownload\\OSM";
    String dataPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
=======
    String dataPath = "C:\\MapDownload\\OSM";
    //String dataPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
>>>>>>> dev-backend

    @RequestMapping(value = "/{name}/{level}/{col}/{rowExtention:.+}", method = RequestMethod.GET)
    public void xyz(@PathVariable String name, @PathVariable String level, @PathVariable String col, @PathVariable String rowExtention, HttpServletResponse response) {
        String[] rowParts = rowExtention.split("\\.");
        try {
            //int row = Integer.parseInt(rowParts[0]);
            String extension = rowParts[1];
            String path = dataPath + "/" + name + "/" + level + "/" + col + "/" + rowExtention;
            InputStream inputStream = new FileInputStream(path);
            if (inputStream == null) {
                return;
            }
            BufferedImage br = ImageIO.read(inputStream);
            ImageIO.write(br, extension, response.getOutputStream());
            br.flush();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(String.format("%s/%d/%d/%s请求出错", name, level, col, rowExtention));
        }
    }
}
