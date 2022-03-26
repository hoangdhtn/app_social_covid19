package com.huyhoang.covid19.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImageController {

	@RequestMapping(value = "/getimage/{photo}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getImg(@PathVariable(name = "photo") String photo){
		if(!photo.equals("") || photo != null) {
			try {
				Path filenamePath = Paths.get("uploads",photo);
				byte[] buffer = Files.readAllBytes(filenamePath);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok()
						.contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(byteArrayResource);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ResponseEntity.badRequest().build();
	}
}
