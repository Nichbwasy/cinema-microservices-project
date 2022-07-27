package com.cinema.common.utils.generators;

import java.nio.charset.Charset;
import java.util.Random;

public class StringGenerator {
    public static String generateRandomString(Integer stringSize) {
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (stringSize > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (stringSize > 0)) {

                r.append(AlphaNumericString.charAt(k));
                stringSize--;
            }
        }

        // return the resultant string
        return r.toString();
    }
}
