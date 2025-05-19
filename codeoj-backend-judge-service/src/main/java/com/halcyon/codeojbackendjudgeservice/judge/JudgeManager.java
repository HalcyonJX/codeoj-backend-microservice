package com.halcyon.codeojbackendjudgeservice.judge;


import com.halcyon.codeojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.halcyon.codeojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.halcyon.codeojbackendjudgeservice.judge.strategy.JudgeContext;
import com.halcyon.codeojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.halcyon.codeojbackendmodel.dto.questionsubmit.JudgeInfo;
import com.halcyon.codeojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}