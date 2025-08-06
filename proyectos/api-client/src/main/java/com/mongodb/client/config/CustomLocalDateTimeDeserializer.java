package com.mongodb.client.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    
    private static final Pattern MICROSECONDS_PATTERN = Pattern.compile("(\\.\\d{1,6})");
    
    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String dateString = parser.getText().trim();
        
        try {
            // Normalize microseconds to nanoseconds by padding/truncating
            String normalizedDate = normalizeMicroseconds(dateString);
            return LocalDateTime.parse(normalizedDate);
        } catch (DateTimeParseException e) {
            throw new IOException("Unable to parse date: " + dateString, e);
        }
    }
    
    private String normalizeMicroseconds(String dateString) {
        // Find the fractional seconds part
        java.util.regex.Matcher matcher = MICROSECONDS_PATTERN.matcher(dateString);
        
        if (matcher.find()) {
            String fractionalPart = matcher.group(1); // includes the dot
            String digits = fractionalPart.substring(1); // remove the dot
            
            // Normalize to 9 digits (nanoseconds) for LocalDateTime parsing
            if (digits.length() <= 9) {
                String paddedDigits = String.format("%-9s", digits).replace(' ', '0');
                return matcher.replaceFirst("." + paddedDigits);
            } else {
                // Truncate if more than 9 digits
                String truncatedDigits = digits.substring(0, 9);
                return matcher.replaceFirst("." + truncatedDigits);
            }
        }
        
        return dateString;
    }
}