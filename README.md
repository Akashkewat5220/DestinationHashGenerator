
# DestinationHashGenerator

A Java application to generate an MD5 hash using Roll Number, a destination value from a JSON file, and a random string.

## How to Use

1. Compile the Java source file:
   ```bash
   javac -cp .:jackson-databind-2.15.0.jar DestinationHashGenerator.java
   ```

2. Create the JAR file:
   ```bash
   jar -cvfe DestinationHashGenerator.jar DestinationHashGenerator DestinationHashGenerator.class -C . jackson-databind-2.15.0.jar
   ```

3. Run the application:
   ```bash
   java -jar DestinationHashGenerator.jar <roll_number> <json_file_path>
   ```

## Example

**Input JSON:**
```json
{
    "key1": 10,
    "key2": {
        "key3": "value3",
        "destination": "finalValue"
    },
    "key4": [1, 2, {"destination": "anotherValue"}]
}
```

**Command:**
```bash
java -jar DestinationHashGenerator.jar 12345 example.json
```

**Output:**
```
c719733517bfbdad3b8504213a7f3c96;5f4dcc3b
```

## Dependencies
- Jackson library for JSON parsing:
  - Add via Maven or download `jackson-databind-2.15.0.jar`.

## Files
- `DestinationHashGenerator.java`: Source code.
- `DestinationHashGenerator.jar`: Compiled executable JAR file.

## License
This project is open source under the MIT license.
