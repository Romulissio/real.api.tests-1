package helpers.utils;

import entity.agencies.Agency;
import entity.blogs.BlogTag;
import entity.users.Users;
import helpers.agencies.AgenciesHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class DataGeneratorEntity {

    private static final DataGeneratorUtils generate = new DataGeneratorUtils();

    @Step("Создание нового юзера")
    public static Users generateNewUser() {
        String genPassword = generate.generatePassword();
        Users users = Users.builder()
                .first_name(generate.generateFirstName())
                .last_name(generate.generateFirstName())
                .middle_name(generate.generateLastName())
                .password(genPassword)
                .password_confirmation(genPassword)
                .type("seller")
                .created_at(generate.generateTimestamp())
                .updated_at(generate.generateTimestamp())
                .email_verified_at(generate.generateTimestamp())
                .phone(generate.generatePhoneNumber())
                .email(generate.generateSafeEmailAddresses())
                .active(true)
                .confirmed(true)
                .agency_id(AgenciesHelper.getRandOrNullAgency())
                .build();
        Allure.addAttachment("Сгенерирован новый юзер: ", users.toString());
        return users;
    }

    @Step("Создание нового агенства недвижимости")
    public Agency generateNewAgency() {
        Agency agency = Agency.builder()
                .name(generate.generateCompanyName())
                .inn(generate.generateLengthNumber(13))
                .created_at(generate.generateTimestamp())
                .updated_at(generate.generateTimestamp())
                .build();
        Allure.addAttachment("Сгенерировано новое Агенство Недвижимости: ", agency.toString());
        return agency;
    }

    @Step("Создание нового тэга для блога")
    public BlogTag generateNewTag(){
        BlogTag tag = BlogTag.builder().name(generate.generateTag()).build();
        Allure.addAttachment("Сгенерирован новый тег для блога: ", tag.toString());
        return tag;
    }
}
