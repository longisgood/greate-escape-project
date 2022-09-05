package net.koreate.greatescape.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;

public class MediaUtils {
  private static Map<String, MediaType> mediaMap = new HashMap<>();
  
  static {
    mediaMap.put("JPG", MediaType.IMAGE_JPEG);
    mediaMap.put("JPEG", MediaType.IMAGE_JPEG);
    mediaMap.put("GIF", MediaType.IMAGE_GIF);
    mediaMap.put("PNG", MediaType.IMAGE_PNG);
  }
  
  public static MediaType getMediaType(String ext) {
    return mediaMap.get(ext.toUpperCase());
  }
}
