
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class DestinationHashGenerator {

    public static void main(String[] args) {
        // Step 1: Parse Command-Line Arguments
        if (args.length != 2) {
            System.err.println("Usage: java -jar DestinationHashGenerator.jar <Roll Number> <JSON File Path>");
            return;
        }

        // Step 2: Extract Roll Number and File Path
        String rollNumber = args[0].toLowerCase().replaceAll("\s+", "");
        String jsonFilePath = args[1];

        try {
            // Step 3: Read and Traverse JSON to Find "destination" Key
            String destinationValue = getDestinationValue(jsonFilePath);
            if (destinationValue == null) {
                System.err.println("Key 'destination' not found in the JSON file.");
                return;
            }

            // Step 4: Generate a Random 8-Character Alphanumeric String
            String randomString = generateRandomString(8);

            // Step 5: Concatenate and Generate the MD5 Hash
            String concatenatedString = rollNumber + destinationValue + randomString;
            String hash = generateMD5Hash(concatenatedString);

            // Step 6: Output in the Specified Format
            System.out.println(hash + ";" + randomString);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Helper Method: Traverse JSON and Find the First "destination" Key
    private static String getDestinationValue(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(filePath));
        return findKey(root, "destination");
    }

    private static String findKey(JsonNode node, String key) {
        if (node.has(key)) {
            return node.get(key).asText();
        }

        for (JsonNode child : node) {
            if (child.isObject() || child.isArray()) {
                String result = findKey(child, key);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    // Helper Method: Generate a Random 8-Character Alphanumeric String
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    // Helper Method: Generate MD5 Hash of a String
    private static String generateMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
