package com.simultanq.base.controller;

import com.simultanq.base.entity.Answer;
import com.simultanq.base.entity.Question;
import com.simultanq.base.entity.Result;
import com.simultanq.base.service.AnswerService;
import com.simultanq.base.service.QuestionService;
import com.simultanq.base.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private
    ResultService resultService;
    @GetMapping
    public List<Result> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/id/{id}")
    public Result getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @PostMapping
    public Result createResult(@RequestBody Result result) {
        return resultService.createResult(result);
    }

    @PutMapping("/{id}")
    public Result updateResult(@PathVariable Long id, @RequestBody Result updatedResult) {
        return resultService.updateResult(id, updatedResult);
    }

    @DeleteMapping("/{id}")
    public void deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
    }
}
