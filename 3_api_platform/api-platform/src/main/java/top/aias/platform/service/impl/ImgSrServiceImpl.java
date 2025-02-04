package top.aias.platform.service.impl;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.translate.TranslateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aias.platform.model.det.FaceDetModel;
import top.aias.platform.model.gan.FaceGanModel;
import top.aias.platform.model.seg.FaceSegModel;
import top.aias.platform.model.sr.SrModel;
import top.aias.platform.service.ImgSrService;

/**
 * 图像分割服务
 *
 * @author Calvin
 * @mail 179209347@qq.com
 * @website www.aias.top
 */
@Service
public class ImgSrServiceImpl implements ImgSrService {
    private Logger logger = LoggerFactory.getLogger(ImgSrServiceImpl.class);

    @Autowired
    private FaceDetModel faceDetModel;
    @Autowired
    private FaceSegModel faceSegModel;
    @Autowired
    private FaceGanModel faceGanModel;
    @Autowired
    private SrModel srModel;

    public DetectedObjects faceDet(Image image) throws TranslateException {
        DetectedObjects detectedObjects = faceDetModel.predict(image);
        return detectedObjects;
    }

    public Image faceGan(Image image) throws TranslateException {
        Image img = faceGanModel.predict(image);
        return img;
    }

    public Image faceSeg(Image image) throws TranslateException {
        Image img = faceSegModel.predict(image);
        return img;
    }

    public Image imageSr(Image image) throws TranslateException {
        Image img = srModel.predict(image);
        return img;
    }
}
