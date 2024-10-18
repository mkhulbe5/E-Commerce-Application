package com.myStartup.services.imageService;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.myStartup.dto.ImageDto;
import com.myStartup.model.Image;

public interface ImageService {
	 Image getImageById(Long id);
	    void deleteImageById(Long id);
	    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
	    void updateImage(MultipartFile file,  Long imageId);
}
