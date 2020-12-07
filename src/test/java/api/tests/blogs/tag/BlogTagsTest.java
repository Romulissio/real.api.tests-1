package api.tests.blogs.tag;

import api.tests.BaseTest;
import config.Endpoints;
import entity.blogs.BlogTag;
import entity.blogs.DataCollectionsEntity;
import helpers.AssertionsHelper;
import helpers.blogs.BlogTagDatabaseHelper;
import helpers.utils.DataGeneratorEntity;
import helpers.utils.DataGeneratorUtils;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.responses.RequestSpec;

import java.util.List;

import static io.restassured.RestAssured.given;

@Feature("Блог")
@DisplayName("Проверка создания, получения и изменения тэгов для блогов")
public class BlogTagsTest extends BaseTest {

    private static final BlogTagDatabaseHelper tagHelper = new BlogTagDatabaseHelper();
    private static final DataGeneratorEntity generator = new DataGeneratorEntity();
    private static final DataGeneratorUtils generatorUtils = new DataGeneratorUtils();

    @Test
    @DisplayName("Создание нового тэга")
    void createTag(){
        BlogTag newTag = generator.generateNewTag();
        BlogTag tagResponse = given().spec(requestSpecAuth).body(newTag)
                .when().post(Endpoints.API_TAGS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(BlogTag.class);
        BlogTag tagFromDb = tagHelper.getTagFindId(tagResponse.getId());
        Assertions.assertEquals(newTag.getName(), tagFromDb.getName());
    }

    @Test
    @DisplayName("Создание тэга не аутентифицированным юзером")
    void createTagUnauthenticatedUser(){
        given().spec(requestSpec).body(generator.generateNewTag())
                .when().post(Endpoints.API_TAGS)
                .then().spec(RequestSpec.checkingResponseFromServer401());
    }

    @Test
    @DisplayName("Создание тэга c невалидными данными")
    void createTagNullData(){
        given().spec(requestSpecAuth).body(new BlogTag())
                .when().post(Endpoints.API_TAGS)
                .then().spec(RequestSpec.checkingResponseFromServer422());
    }

    @Test
    @DisplayName("Получение коллекции тэгов")
    void getTagCollections(){
        DataCollectionsEntity data = given().spec(requestSpecAuth)
                .when().get(Endpoints.API_TAGS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(DataCollectionsEntity.class);
        List<BlogTag> tagsDb = tagHelper.getAllTagList();
        AssertionsHelper.comparisonCollections(data.getData(), tagsDb);
    }

    @Test
    @DisplayName("Изменение имени тэга")
    void updateBlogTag(){
        BlogTag tag = tagHelper.getRandomTag();
        tag.setName(generatorUtils.generateTag());
        given().spec(requestSpecAuth).body(tag)
                .when().put(Endpoints.API_TAGS_ID, tag.getId())
                .then().spec(RequestSpec.checkingResponseFromServer200());
        AssertionsHelper.objectComparison(tag, tagHelper.getTagFindId(tag.getId()));
    }

    @Test
    @DisplayName("Изменение имени тэга не аутентифицированным юзером")
    void updateBlogTagUnauthenticatedUser(){
        BlogTag tag = tagHelper.getRandomTag();
        tag.setName(generatorUtils.generateTag());
        given().spec(requestSpec).body(tag)
                .when().put(Endpoints.API_TAGS_ID, tag.getId())
                .then().spec(RequestSpec.checkingResponseFromServer401());
        Assertions.assertNotEquals(tagHelper.getTagFindId(tag.getId()), tag);
    }

    @Test
    @DisplayName("Изменение имени тэга не валидными даными")
    void updateBlogTagInvalidData(){
        BlogTag tag = tagHelper.getRandomTag();
        given().spec(requestSpecAuth).body(new BlogTag())
                .when().put(Endpoints.API_TAGS_ID, tag.getId())
                .then().spec(RequestSpec.checkingResponseFromServer422());
        AssertionsHelper.objectComparison(tag, tagHelper.getTagFindId(tag.getId()));
    }



}
