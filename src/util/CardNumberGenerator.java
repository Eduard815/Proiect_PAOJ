package util;

import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class CardNumberGenerator {
    private static final Set<String> generatedCardNumbers = new HashSet<String>();
    private static final Random random = new Random();

    public static String generateCardNumber(){
        String cardNumber;

        do{
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 16; i++){
                char digit = (char) ('0' + random.nextInt(10));
                sb.append(digit);
            }
            cardNumber = sb.toString();

        } while (generatedCardNumbers.contains(cardNumber));

        generatedCardNumbers.add(cardNumber);
        return cardNumber;
    }
}
