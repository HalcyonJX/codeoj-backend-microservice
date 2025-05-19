package com.halcyon.codeojbackendjudgeservice.judge;


import com.halcyon.codeojbackendmodel.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
