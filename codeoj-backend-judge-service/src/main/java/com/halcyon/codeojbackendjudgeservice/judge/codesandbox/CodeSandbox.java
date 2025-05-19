package com.halcyon.codeojbackendjudgeservice.judge.codesandbox;


import com.halcyon.codeojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.halcyon.codeojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
