package com.udacity.webcrawler.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {
  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() {
    // TODO: Fill in this method.

    CrawlerConfiguration crawlerConfiguration = null;
   // Reader reader = null;
   /* try {
      reader =
              Files.newBufferedReader(path, StandardCharsets.UTF_8);
      crawlerConfiguration = read(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    }*/
    try(Reader reader=Files.newBufferedReader(path,StandardCharsets.UTF_8)) {
      crawlerConfiguration = read(reader);
    }catch (IOException e){
      throw new RuntimeException(e);
    }
    return  crawlerConfiguration;
            //.Builder().build();
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(reader);
    // TODO: Fill in this method
    CrawlerConfiguration crawlerConfiguration =null;
    ObjectMapper objectMapper=new ObjectMapper();
    objectMapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);

    try {
      crawlerConfiguration = objectMapper.readValue(reader,CrawlerConfiguration.class );
      System.out.println("======= Inside CrawlerConfiguration.read(Reader), crawlerConfiguration.getTimeout()=" + crawlerConfiguration.getTimeout());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
return crawlerConfiguration;
    //return new CrawlerConfiguration.Builder().build();
  }
}
