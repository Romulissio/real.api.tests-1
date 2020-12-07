package helpers.utils;

import com.github.javafaker.Faker;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DataGeneratorUtils {

    private static final Faker faker = new Faker();

    public int getRandIndexCollection(List<?> list){
        Random rnd = new Random();
        return rnd.nextInt(list.size());
    }

    public Timestamp generateTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public String generateUUID(){
        return String.valueOf(UUID.randomUUID());
    }

    public String generateFirstName(){
        return faker.name().firstName();
    }

    public String generateLastName(){
        return faker.name().lastName();
    }

    public String generatePassword(){
        return faker.code().imei();
    }

    public String generatePhoneNumber(){
        return "+79" + faker.phoneNumber().subscriberNumber(9);
    }

    public String generateSafeEmailAddresses(){
        return faker.internet().safeEmailAddress();
    }

    public Long generateLengthNumber(int lengthNumber){
        return Long.valueOf(faker.phoneNumber().subscriberNumber(lengthNumber));
    }

    public String generateTag(){
        return faker.book().title();
    }


    public String generateCompanyName(){
        return faker.company().name();
    }
}
