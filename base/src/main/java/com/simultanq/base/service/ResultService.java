package com.simultanq.base.service;

import com.simultanq.base.entity.Result;
import com.simultanq.base.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {


    @Autowired
    private ResultRepository resultRepository;

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(Long id) {
        try {
            return resultRepository.findById(id)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Result createResult(Result result) {
        return resultRepository.save(result);
    }

    public Result updateResult(Long id, Result updatedResult) {
        Result result = getResultById(id);
        result.setName(updatedResult.getName());
        result.setScore(updatedResult.getScore());
        return resultRepository.save(result);
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}
