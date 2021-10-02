package com.lblog.blogbackend.entity;

import lombok.Builder;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@TableName("category")
public class CategoryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 6687286913317513141L;

    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    @TableField(value = "category_pid")
    private Integer categoryPid;

    @TableField(value = "category_name")
    private String categoryName;

    @TableField(value = "category_description")
    private String categoryDescription;

    @TableField(value = "category_order")
    private Integer categoryOrder;

    @TableField(value = "category_icon")
    private String categoryIcon;

    /**
     * 文章数量(非数据库字段)
     */
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
