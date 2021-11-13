package com.lhd.mylblog.modules.admin.service.impl;

import com.lhd.mylblog.modules.admin.mapper.ArticleMapper;
import com.lhd.mylblog.modules.admin.mapper.ArticleTagAssMapper;
import com.lhd.mylblog.modules.admin.model.Tag;
import com.lhd.mylblog.modules.admin.mapper.TagMapper;
import com.lhd.mylblog.modules.admin.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

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
    public List<Tag> listTag() {
        List<Tag> tagList = null;
        List<Tag> tagEntityList = null;
        try {
            tagList = tagMapper.listTag();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得全部标签失败, cause:{}", e);
        }
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(tagList != null) {
            for (int i = 0; i < tagList.size(); ++i) {
                Tag tag = tagList.get(i);
                if (tag.getTagDeletedTime().equals(date)) {
                    tagEntityList.add(tag);
                }
            }
        }
        return tagEntityList;
    }

    @Override
    public List<Tag> listTagWithCount() {
        List<Tag> tagList = null;
        List<Tag> tagEntityList = null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            tagList = tagMapper.listTag();
            for (int i = 0; i < tagList.size(); i++) {
                Tag tag = tagList.get(i);
                if(tag.getTagDeletedTime().equals(date)) {
                    Long count = articleTagAssMapper.countArticleByTagId(tag.getTagId());
                    tag.setArticleCount(count.intValue());
                    tagEntityList.add(tag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得全部标签失败, cause:{}", e);
        }
        return tagEntityList;
    }

    @Override
    public Tag getTagById(Long id) {
        Tag tag = null;
        try {
            tag = tagMapper.getTagById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据ID获得标签失败, id:{}, cause:{}", id, e);
        }
        return tag;
    }

    @Override
    public Tag insertTag(Tag tag) {
        try {
            tagMapper.insert(tag);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("标签添加失败, tag:{}, cause:{}", tag, e);
        }
        return tag;
    }

    @Override
    public void updateTag(Tag tag) {
        try {
            tagMapper.update(tag);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新标签失败, tag:{}, cause:{}", tag, e);
        }
    }

    @Override
    public void deleteTag(Long id) {
        try { // 气死了给爷删干净！！！！
            // tagMapper.deleteById(id);
            tagMapper.getTagById(id).setTagDeletedTime(new Date());
            articleTagAssMapper.deleteByTagId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("标签删除失败, id:{}, cause:{}", id, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public Tag getTagByName(String name) {
        Tag tag = null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            tag = tagMapper.getTagByName(name);
            if(!tag.getTagDeletedTime().equals(date)) {
                tag = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据名称获得标签, name:{}, cause:{}", name, e);
        }
        return tag;
    }

    @Override
    public List<Tag> listTagByArticleId(Long articleId) {
        List<Tag> tagList = null;
        List<Tag> tagList1=null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            tagList = articleTagAssMapper.listTagByArticleId(articleId);
            for( int i = 0; i < tagList.size(); ++i) {
                if(tagList.get(i).getTagDeletedTime().equals(date)) {
                    tagList1.add(tagList.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章ID获得标签失败，articleId:{}, cause:{}", articleId, e);
        }
        return tagList1;
    }

}
