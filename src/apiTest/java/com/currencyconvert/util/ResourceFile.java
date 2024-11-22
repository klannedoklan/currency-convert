package com.currencyconvert.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemNotFoundException;

import org.apache.commons.io.FileUtils;

import org.springframework.core.io.ClassPathResource;

public final class ResourceFile {

  private ResourceFile() {
  }

  public static String readJsonResourceToString(String filePath) {
    try {
      File resourceFile = new ClassPathResource(filePath).getFile();

      return FileUtils.readFileToString(resourceFile, StandardCharsets.UTF_8);
    } catch (IOException ex) {
      throw new FileSystemNotFoundException(filePath);
    }
  }

  public static InputStream readResourceToInputStream(String filePath) {
    try {
      File resourceFile = new ClassPathResource(filePath).getFile();

      return FileUtils.openInputStream(resourceFile);
    } catch (IOException ex) {
      throw new FileSystemNotFoundException(filePath);
    }
  }

  public static byte[] readResourceToByteArray(String filePath) {
    try {
      File resourceFile = new ClassPathResource(filePath).getFile();

      return FileUtils.readFileToByteArray(resourceFile);
    } catch (IOException ex) {
      throw new FileSystemNotFoundException(filePath);
    }
  }

}
