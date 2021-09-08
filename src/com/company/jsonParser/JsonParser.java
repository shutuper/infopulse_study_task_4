package com.company.jsonParser;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonParser {

    Pattern keyPattern = Pattern.compile("\".+\":");
    Pattern valuePattern = Pattern.compile("(\".+\")|([-]?\\d+)|([-]?\\d+\\.\\d+)");
    Pattern longPattern = Pattern.compile("[-]?\\d+");
    Pattern doublePatter = Pattern.compile("[-]?\\d+\\.\\d+");

    public Map<String, Object> parseJSON(File json) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(json))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = sb.toString().replaceAll("\\s*\\{\\s*", " { ").replaceAll("\\s*}\\s*", " } ")
                .replaceAll("\\s*\\[\\s*", " [ ").replaceAll("\\s*]\\s*", " ] ")
                .replaceAll("\\s*,\\s*", " ").replaceAll("\\s*:\\s*", ": ").trim();
        ArrayList<String> jsonElems = (ArrayList<String>) Arrays.stream(jsonString.split("\\s+")).collect(Collectors.toList());
        //System.out.println(jsonElems);
        return jsonParser(jsonElems);
    }

    private Map<String, Object> jsonParser(ArrayList<String> jsonElems) {
        Stack<Object> stack = new Stack<>();
        for (String curElement : jsonElems) {
            if (curElement.equals("{")) {
                HashMap<String, Object> map = new HashMap<>();
                stack.push(map);
            } else if (curElement.equals("}")) {
                if (stack.size() == 1) return (Map<String, Object>) stack.pop();
                Map<String, Object> mapValue = (Map<String, Object>) stack.pop();
                Object keyOrList = stack.peek();
                if (keyOrList instanceof String) {
                    stack.pop();
                    ((Map<String, Object>) stack.peek()).put((String) keyOrList, mapValue);
                } else ((List<Object>) keyOrList).add(mapValue);

            } else if (curElement.equals("[")) {
                stack.push(new ArrayList<>());
            } else if (curElement.equals("]")) {
                List<Object> listValue = (List<Object>) stack.pop();
                Object keyOrList = stack.peek();
                if (keyOrList instanceof String) {
                    stack.pop();
                    ((Map<String, Object>) stack.peek()).put((String) keyOrList, listValue);
                } else ((List<Object>) keyOrList).add(listValue);

            } else if (keyPattern.matcher(curElement).matches()) {
                stack.push(curElement.substring(1, curElement.length() - 2));
            } else if (valuePattern.matcher(curElement).matches()) {
                String element;
                if (curElement.contains("\"")) element = curElement.substring(1, curElement.length() - 1);
                else element = curElement;
                Object value;
                if (longPattern.matcher(element).matches()) value = Long.parseLong(element);
                else if (doublePatter.matcher(element).matches()) value = Double.parseDouble(element);
                else value = element;
                Object keyOrList = stack.peek();
                if (keyOrList instanceof String) {
                    stack.pop();
                    ((Map<String, Object>) stack.peek()).put((String) keyOrList, value);
                } else ((List<Object>) keyOrList).add(value);
            }

        }
        return null;
    }

}
//    String jsonString = sb.toString().replaceAll("\\s*\\{\\s*", " { ").replaceAll("\\s*}\\s*", " } ")
//            .replaceAll("\\s*\\[\\s*", " [ ").replaceAll("\\s*]\\s*", " ] ")
//            .replaceAll("\\s*,\\s*", " ").replaceAll("\\s*:\\s*", ": ").trim();
//    ArrayList<String> jsonElems = (ArrayList<String>) Arrays.stream(jsonString.split("\\s+")).collect(Collectors.toList());
//        System.out.println(jsonElems);