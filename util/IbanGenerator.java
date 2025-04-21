package util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IbanGenerator {
    private static final String countryCode = "RO";
    private static final Random rand = new Random();
    private static final Set<String> generatedIbans = new HashSet<String>();

    public static String generateIban(){
        String iban;

        do{
            StringBuilder sb = new StringBuilder();

            sb.append(countryCode);
            sb.append(String.format("%02d", rand.nextInt(100)));
            for (int i = 0; i < 4; i++){
                char letter = (char) ('A' + rand.nextInt(26));
                sb.append(letter);
            }
            for (int i = 0; i < 16; i++){
                sb.append(rand.nextInt(10));
            }
            iban = sb.toString();

        } while (generatedIbans.contains(iban));

        generatedIbans.add(iban);
        return iban;
    }
}
