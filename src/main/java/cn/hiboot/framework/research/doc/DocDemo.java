package cn.hiboot.framework.research.doc;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.nio.file.Paths;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/4/3 17:37
 */
public class DocDemo {


    /**
     * Swagger2Markup 生成adhoc格式文档，可输出为pdf或者html
     * @throws MalformedURLException
     */
    @Test
    public void adhoc() throws MalformedURLException {

        /*Swagger2MarkupConverter.from("json" + "/swagger.json")
                .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
                .build()
                .intoFolder("doc");*/
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withPathsGroupedBy(GroupBy.TAGS)
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withBasePathPrefix()
                .withGeneratedExamples()
                .build();

        Swagger2MarkupConverter.from(Paths.get("G:\\kg-service-public.json"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("src/docs/generated"));
    }
}
