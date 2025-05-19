package com.halcyon.codeojbackendquestionservice.service.impl;


import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halcyon.codeojbackendcommon.exception.BusinessException;
import com.halcyon.codeojbackendcommon.exception.ErrorCode;
import com.halcyon.codeojbackendcommon.exception.ThrowUtils;
import com.halcyon.codeojbackendmodel.dto.question.QuestionQueryRequest;
import com.halcyon.codeojbackendmodel.entity.Question;
import com.halcyon.codeojbackendmodel.entity.User;
import com.halcyon.codeojbackendmodel.vo.QuestionVO;
import com.halcyon.codeojbackendmodel.vo.UserVO;
import com.halcyon.codeojbackendquestionservice.mapper.QuestionMapper;
import com.halcyon.codeojbackendquestionservice.service.QuestionService;
import com.halcyon.codeojbackendserviceclient.service.UserFeignClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 张嘉鑫
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2025-03-22 17:00:56
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

    @Resource
    private UserFeignClient userFeignClient;


    @Override
    public void validQuestion(Question question, boolean add) {
        if(question == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String content = question.getContent();
        String tags = question.getTags();
        String answer = question.getAnswer();
        String judgeCase = question.getJudgeCase();
        String judgeConfig = question.getJudgeConfig();
        //创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StrUtil.hasBlank(title, content, tags), ErrorCode.PARAMS_ERROR);
        }
        //有参数则校验
        if(StrUtil.isNotBlank(title) && title.length() > 80){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题过长");
        }
        if(StrUtil.isNotBlank(content) && content.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"内容过长");
        }
        if(StrUtil.isNotBlank(answer) && answer.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"答案过长");
        }
        if(StrUtil.isNotBlank(judgeCase)&& judgeCase.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"判题用例过长");
        }
        if(StrUtil.isNotBlank(judgeConfig)&& judgeConfig.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"判题配置过长");
        }
    }

    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if(questionQueryRequest == null){
            return queryWrapper;
        }
        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        List<String> tags = questionQueryRequest.getTags();
        String answer = questionQueryRequest.getAnswer();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();

        //拼接查询条件
        queryWrapper.like(StrUtil.isNotBlank(title),"title",title);
        queryWrapper.like(StrUtil.isNotBlank(content),"content",content);
        queryWrapper.like(StrUtil.isNotBlank(answer),"answer",answer);
        if(CollectionUtils.isNotEmpty(tags)){
            for (String tag : tags){
                queryWrapper.like("tags","\""+tag+"\"");
            }
        }
        queryWrapper.eq(ObjUtil.isNotEmpty(id),"id",id);
        queryWrapper.eq(ObjUtil.isNotEmpty(userId),"userId",userId);
        queryWrapper.eq("isDelete",false);
        queryWrapper.orderBy(StrUtil.isNotBlank(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;
    }

    @Override
    public QuestionVO getQuestionVO(Question question, HttpServletRequest request) {
        QuestionVO questionVO = QuestionVO.objToVo(question);
        //关联查询用户信息
        Long userId = question.getUserId();
        User user = null;
        if(userId != null && userId > 0){
            user = userFeignClient.getById(userId);
        }
        UserVO userVO = userFeignClient.getUserVO(user);
        questionVO.setUserVO(userVO);
        return questionVO;
    }

    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if(CollectionUtils.isEmpty(questionList)){
            return questionVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userFeignClient.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<QuestionVO> questionVOList = questionList.stream().map(question -> {
            QuestionVO questionVO = QuestionVO.objToVo(question);
            Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionVO.setUserVO(userFeignClient.getUserVO(user));
            return questionVO;
        }).collect(Collectors.toList());
        questionVOPage.setRecords(questionVOList);
        return questionVOPage;
    }
}




