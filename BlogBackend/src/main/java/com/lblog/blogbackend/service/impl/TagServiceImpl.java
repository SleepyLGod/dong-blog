package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.mapper.ArticleMapper;
import com.lblog.blogbackend.mapper.ArticleTagAssMapper;
import com.lblog.blogbackend.mapper.TagMapper;
import com.lblog.blogbackend.model.entity.TagEntity;
import com.lblog.blogbackend.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagAssMapper articleTagAssMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Integer countTag() {
        Integer cnt = null;
        try {
            cnt = tagMapper.countTag();
        }catch (Exception e) {
            e.printStackTrace();
            log.error("标签数统计失败, cause:{}", e);
        }
        return cnt;
    }

    @Override
    public List<TagEntity> listTag() {
        List<TagEntity> tagList = null;
        try {
            tagList = tagMapper.listTag();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得全部标签失败, cause:{}", e);
        }
        return tagList;
    }

    @Override
    public List<TagEntity> listTagWithCount() {
        List<TagEntity> tagList = null;
        try {
            tagList = tagMapper.listTag();
            for (int i = 0; i < tagList.size(); i++) {
                Integer count = articleTagAssMapper.countArticleByTagId(tagList.get(i).getTagId());
                tagList.get(i).setArticleCount(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得全部标签失败, cause:{}", e);
        }
        return tagList;
    }

    @Override
    public TagEntity getTagById(Integer id) {
        TagEntity tagEntity = null;
        try {
            tagEntity = tagMapper.getTagById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据ID获得标签失败, id:{}, cause:{}", id, e);
        }
        return tagEntity;
    }

    @Override
    public TagEntity insertTag(TagEntity tag) {
        try {
            tagMapper.insert(tag);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("标签添加失败, tag:{}, cause:{}", tag, e);
        }
        return tag;
    }

    @Override
    public void updateTag(TagEntity tag) {
        try {
            tagMapper.update(tag);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新标签失败, tag:{}, cause:{}", tag, e);
        }
    }

    @Override
    public void deleteTag(Integer id) {
        try { // 气死了给爷删干净！！！！
            tagMapper.deleteById(id);
            articleTagAssMapper.deleteByTagId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("标签删除失败, id:{}, cause:{}", id, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public TagEntity getTagByName(String name) {
        TagEntity tagEntity = null;
        try {
            tagEntity = tagMapper.getTagByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据名称获得标签, name:{}, cause:{}", name, e);
        }
        return tagEntity;
    }

    @Override
    public List<TagEntity> listTagByArticleId(Integer articleId) {
        List<TagEntity> tagEntityList = null;
        try {
            tagEntityList = articleTagAssMapper.listTagByArticleId(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章ID获得标签失败，articleId:{}, cause:{}", articleId, e);
        }
        return tagEntityList;
    }
}
