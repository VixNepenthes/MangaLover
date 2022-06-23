package com.example.lib.interfaceRepository;

import com.example.lib.model.StoryModel;

import java.util.List;

public interface ResultMethod {
    void onSuccess(List<StoryModel> modelList);
}
