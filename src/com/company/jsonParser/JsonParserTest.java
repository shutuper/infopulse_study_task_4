package com.company.jsonParser;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void parseJSON() {
        JsonParser jsonParser = new JsonParser();
        Map<String, Object> result = jsonParser.parseJSON(new File("resources/example1.json"));
        System.out.println(result + "\n");
        result = jsonParser.parseJSON(new File("resources/example2.json"));
        System.out.println(result + "\n");
        result = jsonParser.parseJSON(new File("resources/example3.json"));
        System.out.println(result + "\n");
    }
}