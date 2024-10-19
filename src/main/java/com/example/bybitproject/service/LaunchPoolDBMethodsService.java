package com.example.bybitproject.service;

import com.example.bybitproject.model.LaunchPool;
import com.example.bybitproject.model.LaunchPoolDTO;
import com.example.bybitproject.model.Pool;
import com.example.bybitproject.repository.LaunchPoolRepository;
import com.example.bybitproject.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LaunchPoolDBMethodsService {
    private final PoolRepository poolRepository;
    private final LaunchPoolRepository launchPoolRepository;

    public LaunchPoolDBMethodsService(LaunchPoolRepository launchPoolRepository, PoolRepository poolRepository) {
        this.launchPoolRepository = launchPoolRepository;
        this.poolRepository = poolRepository;
    }

//    Сохранение одного LaunchPool
    public boolean saveLaunchPool(LaunchPoolDTO launchPoolDTO){

        if (launchPoolRepository.findByExchangeAndLaunchPoolAndPeriod(launchPoolDTO.getExchange(), launchPoolDTO.getLaunchPool(), launchPoolDTO.getPeriod())==null) {
            LaunchPool launchPool = new LaunchPool(launchPoolDTO.getExchange(), launchPoolDTO.getLaunchPool(),null, launchPoolDTO.getPeriod(), launchPoolDTO.getStatus());
            launchPoolRepository.save(launchPool);
            LaunchPool launchPoolFromDb = launchPoolRepository.findByExchangeAndLaunchPoolAndPeriod(launchPoolDTO.getExchange(), launchPoolDTO.getLaunchPool(), launchPoolDTO.getPeriod());
            for (Map.Entry<String, String> poolFor:launchPoolDTO.getPools().entrySet()){
                Pool pool = new Pool(poolFor.getKey(), poolFor.getValue());
                pool.setLaunchPool(launchPoolFromDb);
                poolRepository.save(pool);
            }
            return true;
        }
        System.out.println("E;t ceotcndetn");
        return false;
    }

//    сохранение листа LaunchPool
    public boolean saveLaunchPool(List<LaunchPoolDTO> launchPoolDTOList){
        for (LaunchPoolDTO launchPoolDTO: launchPoolDTOList) {
            if (launchPoolRepository.findByExchangeAndLaunchPoolAndPeriod(launchPoolDTO.getExchange(), launchPoolDTO.getLaunchPool(), launchPoolDTO.getPeriod())==null) {
                saveLaunchPool(launchPoolDTO);
            }
            System.out.println("E;t ceotcndetn");
        }
        return false;
    }

    public boolean deleteLaunchPool(LaunchPool launchPool){
        for (Pool pool : poolRepository.findByLaunchPool_Id(launchPool.getId())) {
            poolRepository.delete(pool);
        }
        launchPoolRepository.delete(launchPool);
        System.out.println("delete "+launchPool);
        return true;
    }

    public boolean updateLaunchPool(LaunchPool launchPool){
        launchPoolRepository.save(launchPool);
        return true;
    }
}
