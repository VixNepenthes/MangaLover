package com.example.lib.model;

public class DeleteModel {
    private int [] chapterNumber;
    private Long storyId;
    private long [] ids;
    private int[] categoryIds;

    public int[] getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int[] chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public long[] getIds() {
        return ids;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }

    public int[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(int[] categoryIds) {
        this.categoryIds = categoryIds;
    }
}
