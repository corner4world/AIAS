package top.aias.controller;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import io.milvus.param.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.aias.domain.*;
import top.aias.service.FeatureService;
import top.aias.service.LocalStorageService;
import top.aias.service.SearchService;
import top.aias.service.TextService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文本管理
 * Data management
 *
 * @author Calvin
 * @email 179209347@qq.com
 * @website www.aias.top
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "文本管理")
@RequestMapping("/api/text")
public class TextController {
    @Autowired
    private TextService textService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private LocalStorageService localStorageService;

    @ApiOperation(value = "提取文本特征值")
    @GetMapping("/extractFeatures")
    public ResponseEntity<Object> extractFeatures(@RequestParam(value = "id") String id) {
        LocalStorage localStorage = localStorageService.findById(Integer.parseInt(id));

        String input = localStorage.getPath();
        File file = new File(input);
        CsvReader csvReader = new CsvReader();
        List<TextInfoDto> list = new ArrayList<>();
        TextInfoDto textInfoDto;
        // 解析文本信息
        ConcurrentHashMap<Long, TextInfoDto> map = textService.getMap();
        long size = map.size();
        try (CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
            CsvRow row;
            while ((row = csvParser.nextRow()) != null) {
                textInfoDto = new TextInfoDto();
                String title = row.getField(0);
                String text = row.getField(1);
                log.info("title: " + title);
                log.info("text: " + text);
                textInfoDto.setId(size++);
                textInfoDto.setTitle(title);
                textInfoDto.setText(text);
                List<Float> feature = featureService.textFeature(title);
                textInfoDto.setFeature(feature);
                list.add(textInfoDto);
            }

            try {
                // 将向量插入Milvus向量引擎
                // Insert the vectors into the Milvus vector engine
                R<Boolean> response = searchService.hasCollection();
                if (!response.getData()) {
                    searchService.initSearchEngine();
                }

                List<Long> vectorIds = new ArrayList<>();
                List<List<Float>> vectors = new ArrayList<>();
                for (TextInfoDto item : list) {
                    vectorIds.add(item.getId());
                    vectors.add(item.getFeature());

                }
                searchService.insert(vectorIds, vectors);
                textService.addTexts(list);

                // 检查是否加载 collection， 如果没有，插入数据后加载
                boolean loaded = searchService.getCollectionState();
                if (!loaded) {
                    searchService.loadCollection();
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                return new ResponseEntity<>(ResultRes.error(ResEnum.MILVUS_CONNECTION_ERROR.KEY, ResEnum.MILVUS_CONNECTION_ERROR.VALUE), HttpStatus.OK);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ModelException e) {
            e.printStackTrace();
        } catch (TranslateException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(ResultBean.success(), HttpStatus.OK);
    }
}