package com.lblog.blogbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
//@Builder
@TableName("category")
public class CategoryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 6687286913317513141L;

    @TableId(value = "category_id", type = IdType.AUTO)
    @TableLogic
    private Integer categoryId;

    @TableField(value = "category_pid")
    @TableLogic
    private Integer categoryPid;

    @TableField(value = "category_name")
    @TableLogic
    private String categoryName;

    @TableField(value = "category_description")
    @TableLogic
    private String categoryDescription;

    @TableField(value = "category_order")
    @TableLogic
    private Integer categoryOrder;

    @TableField(value = "category_icon")
    @TableLogic
    private String categoryIcon;

    @TableField(value = "category_deleted_time")
    @TableLogic
    private Date categoryDeletedTime;

    /**
     * 文章数量(非数据库字段)
     */
    @TableLogic
    private Integer articleCount;

    public CategoryEntity(Integer categoryId, Integer categoryPid, String categoryName, String categoryDescription, Integer categoryOrder, String categoryIcon,Integer articleCount) {
        this.categoryId = categoryId;
        this.categoryPid = categoryPid;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryOrder = categoryOrder;
        this.categoryIcon = categoryIcon;
        this.articleCount = articleCount;
    }

    public CategoryEntity(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public CategoryEntity(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryEntity() {}

    /**
     * 未分类
     *
     * @return 分类
     */
    public static CategoryEntity Default() {
        return new CategoryEntity(100000000, "未分类");
    }

}
