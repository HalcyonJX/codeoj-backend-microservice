package com.halcyon.codeojbackendjudgeservice.judge.strategy;


import com.halcyon.codeojbackendmodel.dto.question.JudgeCase;
import com.halcyon.codeojbackendmodel.dto.questionsubmit.JudgeInfo;
import com.halcyon.codeojbackendmodel.entity.Question;
import com.halcyon.codeojbackendmodel.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}