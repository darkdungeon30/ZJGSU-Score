package org.example.controller;

import jakarta.annotation.Resource;
import org.example.domain.Evaluate;
import org.example.domain.SystemUser;
import org.example.service.EvaluateService;
import org.example.service.SystemUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommend/{id}")
public class RecommendController {
    @Resource
    private EvaluateService evaluateService;
    @Resource
    private SystemUserService systemUserService;

    @RequestMapping
    public Map<Integer, Double> recommend(@PathVariable int id) {
        SystemUser u = systemUserService.getById(id);
        List<Evaluate> elist = evaluateService.list();
        Map<Integer, List<Evaluate>> groupedByLid = elist.stream()
                .collect(Collectors.groupingBy(Evaluate::getLid));
        Map<Integer, Double> recommendations = new HashMap<>();
        Map<Integer, Double[]> llist = new HashMap<>();
        for (Map.Entry<Integer, List<Evaluate>> entry:groupedByLid.entrySet()) {
            List<Evaluate> evaluations = entry.getValue();
            Double[] averages = new Double[5];

            // 计算每个属性的平均值
            averages[0] = evaluations.stream().mapToDouble(Evaluate::getEfun).average().orElse(0f);
            averages[1] = evaluations.stream().mapToDouble(Evaluate::getEexam).average().orElse(0f);
            averages[2] = evaluations.stream().mapToDouble(Evaluate::getEdifficult).average().orElse(0f);
            averages[3] = evaluations.stream().mapToDouble(Evaluate::getEscore).average().orElse(0f);
            averages[4] = evaluations.stream().mapToDouble(Evaluate::getEspecial).average().orElse(0f);

            llist.put(entry.getKey(), averages);
        }
        for (Map.Entry<Integer, Double[]> entry : llist.entrySet()) {
            Double[] averages = entry.getValue();
            double variance = 0;
            double max = 0;
            // 计算方差
            variance += Math.pow(averages[0] - u.getUfun(), 2);
            variance += Math.pow(averages[1] - u.getUexam(), 2);
            variance += Math.pow(averages[2] - u.getUdifficult(), 2);
            variance += Math.pow(averages[3] - u.getUscore(), 2);
            variance += Math.pow(averages[4] - u.getUspecial(), 2);
            for (int i=0;i<5;i++){
                max += Math.max(Math.pow(averages[i]-0,2),Math.pow(averages[i]-10,2));
            }
            if (max==0) max=999;
            recommendations.put(entry.getKey(), 1-variance/max);
        }

        return recommendations;
    }
}
