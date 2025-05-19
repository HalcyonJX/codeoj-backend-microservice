package com.halcyon.codeojbackendjudgeservice.judge.strategy;


import com.halcyon.codeojbackendmodel.dto.questionsubmit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}