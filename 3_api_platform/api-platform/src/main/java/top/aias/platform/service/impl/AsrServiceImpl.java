package top.aias.platform.service.impl;

import ai.djl.modality.audio.Audio;
import ai.djl.translate.TranslateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aias.platform.model.asr.WhisperModel;
import top.aias.platform.service.AsrService;

/**
 * 语音识别服务
 *
 * @author Calvin
 * @mail 179209347@qq.com
 * @website www.aias.top
 */
@Service
public class AsrServiceImpl implements AsrService {
    private Logger logger = LoggerFactory.getLogger(AsrServiceImpl.class);

    @Autowired
    private WhisperModel whisperModel;

    public String enSpeechToText(Audio audio) throws TranslateException {
        String output = whisperModel.asr(audio, false);
        return output;
    }

    public String zhSpeechToText(Audio audio) throws TranslateException {
        String output = whisperModel.asr(audio, true);
        return output;
    }
}
