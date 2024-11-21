package com.mycart.mycart.Controller;

import com.mycart.mycart.Entities.Images;
import com.mycart.mycart.Exceptions.ImageNotFound;
import com.mycart.mycart.Request.ImageDto;
import com.mycart.mycart.Response.ImageApiResponse;
import com.mycart.mycart.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    public ImageService imageservice;

    @PostMapping("/upload")
    public ResponseEntity<ImageApiResponse> saveImage(@RequestParam List<MultipartFile> file, @RequestParam int productId) {
        try {
            List<ImageDto> imgdtos = imageservice.saveImage(file, productId);
            return ResponseEntity.ok(new ImageApiResponse("UploadSucessfull", imgdtos));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ImageApiResponse("UploadFailed",e.getMessage()));
        }
    }
    @GetMapping("/image/download/{imageId}")
   public ResponseEntity<Resource> downloadImage(@PathVariable int imageId) throws SQLException {
       Images img=imageservice.getImagesById(imageId);
       ByteArrayResource resource=new ByteArrayResource(img.getImage().getBytes(1, (int) img.getImage().length()));
       return ResponseEntity.ok().contentType(MediaType.parseMediaType(img.getFiletype())).header(HttpHeaders.CONTENT_DISPOSITION,"attachment filename=\""+img.getFilename()+"\"").body(resource);
   }
    @PutMapping("/image/{imageid}/update")
   public ResponseEntity<ImageApiResponse> updateimage(@RequestBody MultipartFile file, @PathVariable int productId) {
        Images img=imageservice.getImagesById(productId);
        try {
            if (img != null) {
                imageservice.updateImage(file, productId);
                return ResponseEntity.ok(new ImageApiResponse("UpdatedSucessfully", null));
            }
        }
        catch(ImageNotFound e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ImageApiResponse("UnableTo Upload",e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ImageApiResponse("UnableTo Upload",null));

   }

   @DeleteMapping("/image/{imageid}/delete")
   public ResponseEntity<ImageApiResponse> deleteimage(@PathVariable int imgId) {
        Images img=imageservice.getImagesById(imgId);
        try {
            if (img != null) {
                imageservice.deleteImageById(imgId);
                return ResponseEntity.ok(new ImageApiResponse("DeletedSucessfully", null));
            }
        }
        catch(ImageNotFound e){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ImageApiResponse("UnableTo Upload",e.getMessage()));
       }
       return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ImageApiResponse("UnableTo Upload",null));

   }



}
