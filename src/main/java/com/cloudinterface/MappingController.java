package com.cloudinterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.cloudinterface.mapping.MappingProcessor;

@RestController
public class MappingController {

	@PostMapping("/testxml")
	public void doPost(@RequestParam("file") MultipartFile file, String interfaceId) {
		try {
			MappingProcessor processor = new MappingProcessor(interfaceId, convert(file));
			processor.process();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}
