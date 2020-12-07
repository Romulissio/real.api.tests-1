package api.tests.blogs.blog;

import api.tests.BaseTest;
import config.Endpoints;
import entity.blogs.Blog;
import entity.blogs.BlogTag;
import helpers.blogs.BlogTagDatabaseHelper;
import helpers.utils.DataGeneratorUtils;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static io.restassured.RestAssured.given;

@Feature("Блог")
@DisplayName("Проверка создания нового блога")
public class CreateBlogTest extends BaseTest {

    @Test
    @DisplayName("Корректное создание блога")
    void createCorrectBlog(){
        DataGeneratorUtils generatorUtils = new DataGeneratorUtils();
        BlogTagDatabaseHelper tagDatabaseHelper = new BlogTagDatabaseHelper();
        List<Integer> listTags = new ArrayList<>();
        Integer randIdTag = Math.toIntExact(tagDatabaseHelper.getRandomTag().getId());
        listTags.add(randIdTag);
        Blog blog = Blog.builder()
                .title("Title test2")
                .slug("slug_ssll668r453678")
                .type("article")
                .active(true)
                .important(false)
                .preview_text("preview text test")
                .detail_text("detail text test")
                .meta_title("meta title test")
                .meta_description("meta description test")
                .published_at("2020-10-10")
                .tags(listTags)
                .build();
        Response response = given().spec(requestSpecAuth).body(blog).when().post(Endpoints.API_BLOG);
        response.getBody().prettyPrint();
        System.out.println(response.statusCode());
    }

    @Test
    void createImage() throws IOException {

        Response response = given().spec(requestSpecAuth).body(getImage()).post(Endpoints.API_IMAGES);
        response.getBody().prettyPrint();
    }

    public static void main(String[] args) throws IOException {
        String directory = "C:\\uploads";
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
        BufferedImage image = ImageIO.read(new File(directory, "b0e13476-19b1-43cf-8691-4a3a4c1dec54123.jpg"));
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        String base654String = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();
        byte[] resByteArray = Base64.getDecoder().decode(base654String);
        BufferedImage resultImage = ImageIO.read(new ByteArrayInputStream(resByteArray));
        ImageIO.write(resultImage, "jpg", new File(directory,"resultImage.jpg"));
    }

    private static byte[] getImage() throws IOException {
        String directory = "C:\\uploads";
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
        BufferedImage image = ImageIO.read(new File(directory, "b0e13476-19b1-43cf-8691-4a3a4c1dec54123.jpg"));
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        String base654String = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();
        byte[] resByteArray = Base64.getDecoder().decode(base654String);
        return resByteArray;
    }
}
